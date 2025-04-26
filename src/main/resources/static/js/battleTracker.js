document.addEventListener("DOMContentLoaded", async function () {
    await getItems();
});

let currentTurnIndex = 0;
let selectedCombatant = null;
const selectedCombatants = new Set();
const effectManager = new EffectManager();

function selectCombatant(element) {
    const combatantId = element.dataset.id;

    toggleCombatantSelection(combatantId, !selectedCombatants.has(combatantId));
}

function toggleCombatantSelection(id, selected) {
    if (selected) {
        selectedCombatants.add(id);
    } else {
        selectedCombatants.delete(id);
    }
    updateSelectionStyles();
}

function updateSelectionStyles() {
    document.querySelectorAll('.table-row').forEach(row => {
        const id = row.getAttribute('data-id');
        row.classList.toggle('highlighted', selectedCombatants.has(id));
    });
}

async function applyHpToSelected() {
    const selectedIds = Array.from(selectedCombatants);
    const type = document.getElementById('hpAction').value;
    const amount = document.getElementById('hpAmount').valueAsNumber;

    const request = {
        combatantIds : selectedIds,
        amount : amount,
        type : type.toUpperCase()
    }

    if (selectedCombatants.size === 0) {
        alert('Please select at least one combatant');
    }

    const response = await fetch('api/combatants/hp', {
        method: "PATCH",
        headers: {"Content-type": "application/json"},
        body: JSON.stringify(request)
    });

    const result = await response.json();
    const updatedCombatants = Array.isArray(result) ? result : [result];
    await updateCombatantsUI(updatedCombatants);
}

async function removeSelected() {
    const selectedIds = Array.from(selectedCombatants);
    const request = {
        itemIds: selectedIds
    }

    const response = await fetch('api/combatants/remove', {
        method: "DELETE",
        headers: {"Content-type": "application/json"},
        body: JSON.stringify(request)
    });

    const result = await response.json();
    const updatedCombatants = Array.isArray(result) ? result : [result];
    await removeCombatantsUI(updatedCombatants);
}

async function updateCombatantsUI(items) {
    console.log(items);

    const turnQueueItems = items[0]?.turnQueueItems || [];
    console.log(items);
    turnQueueItems.forEach(item => {
        if (item.type === "GROUP") {
            item.members.forEach(combatant => {
                const row = document.querySelector(`.group-member-row[data-id="${combatant.id}"]`);
                if (row) {
                    const hpElement = row.querySelector('.hp-cell');
                    if (hpElement) {
                        hpElement.textContent = `${combatant.currentHp}/${combatant.maxHp}`;
                        // Visual feedback
                        hpElement.classList.add('hp-updated');
                        setTimeout(() => hpElement.classList.remove('hp-updated'), 1000);
                    }
                }
            });
        }

        if (item.type === "INDIVIDUAL") {
            const row = document.querySelector(`.table-row[data-id="${combatant.id}"]`);
            console.log(row);
            if (row) {
                const hpElement = row.querySelector('.hp-cell');
                console.log(hpElement);
                if (hpElement) {
                    hpElement.textContent = `${combatant.currentHp}/${combatant.maxHp}`;
                    // Visual feedback
                    hpElement.classList.add('hp-updated');
                    setTimeout(() => hpElement.classList.remove('hp-updated'), 1000);
                }
            }
        }
    });


}

async function removeCombatantsUI(items) {

    const combatants = items[0]?.turnQueueItems || [];
    combatants.forEach(combatant => {
       const row = document.querySelector(`.table-row[data-id="${combatant.id}"]`);
       if (row) {
           row.remove();
       }
    });
}

async function getItems() {
    const response = await fetch('/api/combatants', {
        method: "GET",
        headers: {"Content-type": "application/json"}
    })

    const turnQueueItems = await response.json();
    await updateCombatantsList(turnQueueItems);
}

// Function to add a combatant to the active list
async function addToCombat(button) {
    const templateId = button.getAttribute("data-id");
    const amount = button.closest("li").querySelector(".creaturesAmountInput").value;

    const request = {
        templateId: templateId,
        amount: amount
    }

    const response = await fetch(`/api/combatants/add`, {
        method: "POST",
        headers: {"Content-type": "application/json"},
        body: JSON.stringify(request)
    })

    const turnQueueItems = await response.json();
    await updateCombatantsList(turnQueueItems);
}

async function updateCombatantsList(turnQueueItems) {
    const combatantsList = document.getElementById("activeCombatants");
    combatantsList.innerHTML = "";

    if (!turnQueueItems) return;

    const html = await renderThymeleaf();
    combatantsList.insertAdjacentHTML('beforeend', html);
}


async function renderThymeleaf() {
    const response = await fetch('/battle_tracker/renderCombatants', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'text/html'
        },
    });
    return await response.text();
}

function toggleHiddenContent(headerElement) {
    const row = headerElement.closest('.table-row');
    const effectsPanel = row.querySelector('.expandable-content')

    if (effectsPanel) {
        const isHidden = effectsPanel.style.display === 'none';
        effectsPanel.style.display = isHidden ? 'block' : 'none';

        const toggleIcon = headerElement.querySelector('.toggle-icon');
        if (toggleIcon) {
            toggleIcon.textContent = isHidden ? '▲' : '▼';
        }
    }
}

function advanceTurn(items) {
    currentTurnIndex = (currentTurnIndex + 1) % items.length;
    renderTurnOrder();
}

function renderTurnOrder() {

}

