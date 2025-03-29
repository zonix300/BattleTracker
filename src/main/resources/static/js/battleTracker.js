document.addEventListener("DOMContentLoaded", function () {
    const nextTurnButton = document.getElementById("nextTurnButton");

    fetch("/battle_tracker/activeCombatants")
        .then(response => response.json())
        .then(data => updateCombatantsList(data))
        .catch(error => console.error("Error loading active combatants:", error));

    if (nextTurnButton) {
        nextTurnButton.addEventListener("click", function () {
            fetch("/battle_tracker/nextTurn", {
                method: "POST",
                headers: { "Content-type": "application/json" },
            })
                .then(response => response.json())
                .then(data => updateCombatantsList(data)) // Highlight next combatant
                .catch(error => console.error("Error:", error));
        });
    }
});

// Function to add a combatant to the active list
function addToCombat(button) {
    const combatantId = button.getAttribute("data-id");

    fetch(`/battle_tracker/add?combatantId=${combatantId}`, {
        method: "POST",
        headers: { "Content-type": "application/json" },
    })
        .then(response => response.json())
        .then(() => {
            return fetch("battle_tracker/activeCombatants");
        })
        .then(response => response.json())
        .then(data => {
            updateCombatantsList(data); // Append only new combatant
        })
        .catch(error => console.error("Error:", error));
}

// Function to update turn highlighting
function updateCombatantsList(data) {
    const combatantsList = document.getElementById("activeCombatants");
    combatantsList.innerHTML = "";

    if (data && data.activeCombatants) {
        data.activeCombatants.forEach((turn, index) => {
            const li = document.createElement("li");
            li.textContent = `${turn.combatant.name} - HP: ${turn.combatant.hp} - Initiative: ${turn.combatant.initiative}`;

            if (index === data.currentTurnIndex) {
                li.style.fontWeight = "bold";
                li.style.color = "red";
            }

            combatantsList.appendChild(li);
        });
    }

}
