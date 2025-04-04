document.addEventListener("DOMContentLoaded", function () {

    loadActiveCombatants();

    setUpEventListener();

});

function loadActiveCombatants() {
    fetch("/battle_tracker/activeCombatants")
        .then(response => response.json())
        .then(data => {
            localStorage.setItem("turnQueueItems", data.turnQueueItems);
            updateCombatantsList(data);
        })
        .catch(error => console.error("Error loading active combatants:", error));
}

function setUpEventListener() {

    document.querySelector("#nextTurnButton").addEventListener("click", handleNextTurn);
}


// Function to add a combatant to the active list
function addToCombat(button) {
    const templateId = button.getAttribute("data-id");
    const amount = button.closest("li").querySelector(".creaturesAmountInput").value;

    fetch(`/battle_tracker/add?templateId=${templateId}&amount=${amount}`, {
        method: "POST",
        headers: {"Content-type": "application/json"},
    })
        .then(response => response.json())
        .then(data => {
            localStorage.setItem("turnQueueItems", data.turnQueueItems);
            updateCombatantsList(data);
        })
        .catch(error => console.error("Error:", error));
}

function updateCombatantsList(data) {
    const combatantsList = document.getElementById("activeCombatants");
    combatantsList.innerHTML = "";

    if (!data.turnQueueItems || !Array.isArray(data.turnQueueItems)) {
        return;
    }

    data.turnQueueItems.forEach((member, index) => {
        const li = document.createElement("li");
        li.className = `combatant ${index === 0 ? 'active-turn' : ''}`;

        if (member.type === "INDIVIDUAL") {
            li.innerHTML = `
                <div class="combatant-header">
                    <span class="name">${member.name}</span>
                    <span class="initiative">Initiative: ${member.initiative}</span>
                </div>
                <div class="combatant-stats">
                    HP: <span class="hp-value">${member.currentHp}</span>/${member.maxHp}
                    <button class="remove-btn" data-id="${member.id}">Remove</button>
                </div>
                <div class="combatant-controls">
                    <select class="hp-action">
                        <option value="damage">Damage</option>
                        <option value="heal">Heal</option>
                    </select>
                    <input type="number" class="hp-amount" min="0" value="0">
                    <button class="apply-hp-btn" data-id="${member.id}">Apply</button>
                </div>
            `;
        }
        else if (member.type === "GROUP") {
            li.innerHTML = `
                <div class="group-header" onclick="toggleGroup(this)">
                    <span class="name">${member.groupName} (${member.members.length})</span>
                    <span class="initiative">Initiative: ${member.initiative}</span>
                    <button class="remove-btn" data-id="${member.groupId}">Remove All</button>
                </div>
                <div class="group-controls">
                    <select class="hp-action">
                        <option value="damage">Damage All</option>
                        <option value="heal">Heal All</option>
                    </select>
                    <input type="number" class="hp-amount" min="0" value="0">
                    <button class="apply-hp-btn" data-id="${member.groupId}">Apply</button>
                </div>
                <div class="group-members" style="display:none">
                    ${member.members.map(m => `
                        <div class="group-member">
                            <span>${m.name}</span>
                            <span>HP: ${m.currentHp}/${m.maxHp}</span>
                            <button class="remove-btn" data-id="${m.id}">Remove</button>
                        </div>
                    `).join('')}
                </div>
            `;
        }

        combatantsList.appendChild(li);
    });

    // Add event listeners after elements are created
    setupCombatantEventListeners();
}

function toggleGroup(headerElement) {
    const membersSection = headerElement.nextElementSibling.nextElementSibling;
    membersSection.style.display = membersSection.style.display === 'none' ? 'block' : 'none';
}

function setupCombatantEventListeners() {
    // Individual HP controls
    document.querySelectorAll('.apply-hp-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const combatantId = this.getAttribute('data-id');
            const action = this.parentElement.querySelector('.hp-action').value;
            const amount = parseInt(this.parentElement.querySelector('.hp-amount').value);

            if (!isNaN(amount)) {
                applyHpChange(combatantId, action, amount);
            }
        });
    });

    // Remove buttons
    document.querySelectorAll('.remove-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            removeItem(this.getAttribute('data-id'));
        });
    });
}

function updateRoundStatus() {
    const roundCounterHTML = document.getElementById("roundCounter");
    roundCounterHTML.innerHTML = "";

    try {
        const roundCounterJSON = localStorage.getItem("roundCounter");
        const roundCounter = JSON.parse(roundCounterJSON);

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

function handleNextTurn() {
    fetch("battle_tracker/nextTurn", {
        method: "POST",
        headers: {"Content-type": "application/json"},
    })
        .then(response => response.json())
        .then(data => {
            localStorage.setItem("roundCounter", JSON.stringify(data.roundCounter));
            updateCombatantsList(data);
            updateRoundStatus();
        })
        .catch(error => console.error("Error:", error));
}

function applyHpChange(itemId, action, amount) {
    fetch(`battle_tracker/${action}?itemId=${itemId}&amount=${amount}`, {
        method: "POST",
        headers: {"Content-type": "application/json"},
    })
        .then(response => response.json())
        .then(data => {
            localStorage.setItem("turnQueueItems", data.turnQueueItems);
            updateCombatantsList(data);
            updateRoundStatus();
        })
}

function removeItem(itemId) {
    fetch(`battle_tracker/remove?itemId=${itemId}`, {
        method: "POST",
        headers: { "Content-type": "application/json" },
    })
        .then(response => response.json())
        .then(data => {
            localStorage.setItem("turnQueueItems", data.turnQueueItems);
            updateCombatantsList(data);
        })
}