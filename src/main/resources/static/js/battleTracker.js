document.addEventListener("DOMContentLoaded", function () {

    loadActiveCombatants();

    setUpEventListener();

});

function loadActiveCombatants() {
    fetch("/battle_tracker/activeCombatants")
        .then(response => response.json())
        .then(data => {
            if (data.activeCombatants) {
                localStorage.setItem("activeCombatants", JSON.stringify(data.activeCombatants));
            }
            updateCombatantsList();
        })
        .catch(error => console.error("Error loading active combatants:", error));
}

function setUpEventListener() {

    document.querySelector("#nextTurnButton").addEventListener("click", handleNextTurn);
}


// Function to add a combatant to the active list
function addToCombat(button) {
    const combatantId = button.getAttribute("data-id");
    const amount = button.closest("li").querySelector(".creaturesAmountInput").value;

    fetch(`/battle_tracker/add?combatantId=${combatantId}&amount=${amount}`, {
        method: "POST",
        headers: { "Content-type": "application/json" },
    })
        .then(response => response.json())
        .then(data => {
            localStorage.setItem("activeCombatants", JSON.stringify(data.activeCombatants));
            updateCombatantsList();
        })
        .catch(error => console.error("Error:", error));
}

// Function to update turn highlighting
function updateCombatantsList() {
    const combatantsList = document.getElementById("activeCombatants");
    combatantsList.innerHTML = "";

    try {
        const activeCombatantsJSON = localStorage.getItem("activeCombatants");
        const activeCombatants = activeCombatantsJSON ? JSON.parse(activeCombatantsJSON) : [];

        activeCombatants.forEach((turn, index) => {
            if (!turn.combatant) return;

            const li = document.createElement("li");
            li.className = "combatant-item";
            if (index === 0) {
                li.classList.add("active-turn");
            }

            li.innerHTML = `
                <span class="combatant-name">${turn.combatant.name}</span> 
                - HP: <span class="hp-value">${turn.currentHp}</span> 
                - Initiative: ${turn.combatant.initiative}
                
                <select class="hp-action">
                    <option value="damage">Damage</option>
                    <option value="heal">Heal</option>
                </select>
                
                <input type="number" class="hp-amount" value="0" min="0">
                
                <button class="apply-hp-change" data-index="${index}">Apply</button>
                <button class="remove-combatant" data-index="${index}">Remove</button>
            `;

            combatantsList.appendChild(li);
        });

        // Add event listeners for HP changes
        document.querySelectorAll(".apply-hp-change").forEach(button => {
            button.addEventListener("click", handleHpChange);
        });

        // Add event listeners for removing combatants
        document.querySelectorAll(".remove-combatant").forEach(button => {
            button.addEventListener("click", handleRemoveCombatant);
        });

    } catch (error) {
        console.error("Error updating combatants list:", error);
        combatantsList.innerHTML = "<li>Error loading combatants</li>";
    }

}

function updateRoundStatus() {
    const roundCounterHTML = document.getElementById("roundCounter");
    roundCounterHTML.innerHTML = "";

    try {
        const roundCounterJSON = localStorage.getItem("currentRound");
        const roundCounter = JSON.parse(roundCounterJSON);
        console.log(roundCounter);

        const div = document.createElement("div");
        div.innerHTML = `
            <div>${roundCounter}</div>
        `
        roundCounterHTML.appendChild(div);
    } catch (error) {
        console.error("Error updating round counter:", error);
        roundCounter.innerHTML = "<div>Error updating round counter</div>";
    }
}

function handleHpChange() {
    const index = parseInt(this.getAttribute("data-index"));
    const listItem = this.closest("li");
    const action = listItem.querySelector(".hp-action").value;
    const amount = parseInt(listItem.querySelector(".hp-amount").value, 10);

    if (isNaN(amount)) {
        alert("Please enter a valid number");
        return;
    }

    fetch(`/battle_tracker/${action}?index=${index}&amount=${amount}`, {
        method: "POST",
        headers: { "Content-type": "application/json" },
    })
        .then(response => response.json())
        .then(data => {
            if (data.activeCombatants) {
                localStorage.setItem("activeCombatants", JSON.stringify(data.activeCombatants));
                updateCombatantsList();
            }
        })
        .catch(error => console.error("Error:", error));
}

function handleRemoveCombatant() {
    const index = parseInt(this.getAttribute("data-index"));

    fetch(`battle_tracker/remove?index=${index}`, {
        method: "POST",
        headers: { "Content-type": "application/json" },
    })
        .then(response => response.json())
        .then(data => {
            if (data.activeCombatants) {
                localStorage.setItem("activeCombatants", JSON.stringify(data.activeCombatants));
                updateCombatantsList();
            }
        })
        .catch(error => console.error("Error:", error));
}

function handleNextTurn() {
    fetch("battle_tracker/nextTurn", {
        method: "POST",
        headers: { "Content-type": "application/json" },
    })
        .then(response => response.json())
        .then(data => {
            if (data.activeCombatants && data.currentRound) {
                localStorage.setItem("activeCombatants", JSON.stringify(data.activeCombatants));
                localStorage.setItem("currentRound", JSON.stringify(data.currentRound));
                updateCombatantsList();
                updateRoundStatus();
            }
        })
        .catch(error => console.error("Error:", error));
}