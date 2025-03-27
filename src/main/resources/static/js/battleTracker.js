document.addEventListener("DOMContentLoaded", function () {
    const nextTurnButton = document.getElementById("nextTurnButton");

    if (nextTurnButton) {
        nextTurnButton.addEventListener("click", function () {
            fetch("/battle_tracker/nextTurn", {
                method: "POST",
                headers: {
                    "Content-type": "application/json",
                },
            })
                .then(response => response.json())
                .then(data => {
                    updateCombatantsList(data); // Update list with the received data
                })
                .catch(error => console.error("Error:", error));
        });
    }

    function updateCombatantsList(data) {
        const combatantsList = document.getElementById("combatantsList");
        combatantsList.innerHTML = ""; // Clear existing list

        if (data && data.combatants) {
            // Loop through the combatants array
            data.combatants.forEach((combatant, index) => {

                console.log(`Processing combatant at index: ${index}`, combatant);

                // Create a new list item for each combatant
                const li = document.createElement("li");
                li.textContent = combatant.name + " - HP: " + combatant.hp + " - Initiative: " + combatant.initiative;

                // Highlight the current combatant's turn
                if (index === data.currentTurnIndex) {
                    li.style.fontWeight = "bold";
                    li.style.color = "red";
                }

                combatantsList.appendChild(li); // Add it to the list
            });
        }
    }
});
