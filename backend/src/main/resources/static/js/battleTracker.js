document.addEventListener("DOMContentLoaded", async function () {
    await getItems();
});

const sortButton = document.getElementById('sort-btn');
let isSortByName = true;

sortButton.addEventListener('click', function ()  {
    isSortByName = !isSortByName;

    if (isSortByName) {
        sortButton.textContent = "A-Z";
        sortButton.dataset.sort = "name";
        console.log("Now sorting by NAME");
        // Add your sorting logic here
    } else {
        sortButton.textContent = "CR";
        sortButton.dataset.sort = "challengeRating";
        console.log("Now sorting by RATING");
        // Add your sorting logic here
    }
})

let currentTurnIndex = 0;
let selectedCombatant = null;
const selectedCombatants = new Set();
const effectManager = new EffectManager();

function selectCombatant(element) {
    let itemId;
    let templateId;
    if (element.className === 'combatant-main' || element.className === 'group-main') {
        const row = element.closest('.table-row');
        itemId = row.dataset.id;
        templateId = row.dataset.templateId;
    }
    if (element.className === 'group-member-row') {
        itemId = element.dataset.id;
    }

    toggleCombatantSelection(itemId, templateId, !selectedCombatants.has(itemId));
}

async function toggleCombatantSelection(itemId, templateId, selected) {
    let html
    if (selected) {
        selectedCombatants.add(itemId);
    } else {
        selectedCombatants.delete(itemId);
    }
    updateSelectionStyles();
    const templateContainer = document.getElementById('creature-sheet-container');
    html = await renderSelectedTemplateCreature(templateId);

    templateContainer.innerHTML = html;

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

async function searchCreature() {
    const searchTerm = document.getElementById('creature-search-input').value;
    const searchBy = sortButton.dataset.sort;

    const request = {
        name: searchTerm,
        sortDirection: 'ASC',
        page: 0,
        size: 15,
        sortBy: searchBy
    }

    const response = await fetch('api/combatants/search', {
        method: "POST",
        headers: {"Content-type": "application/json"},
        body: JSON.stringify(request)
    });

    const templateCreature = await response.json();
    const html = await updateAvailableCreaturesList(templateCreature.content);

}

async function updateCombatantsUI(items) {

    const turnQueueItems = items[0]?.turnQueueItems || [];
    turnQueueItems.forEach(item => {
        if (item.type === "GROUP") {
            item.members.forEach(combatant => {
                const row = document.querySelector(`.group-member-row[data-id="${combatant.id}"]`);
                updateCombatantUI(combatant, row);
            });
        }

        if (item.type === "INDIVIDUAL") {
            if (item.groupId) {
                const row = document.querySelector(`.group-member-row[data-id="${item.id}"]`);
                updateCombatantUI(item, row);
            } else {
                const row = document.querySelector(`.table-row[data-id="${item.id}"]`);
                updateCombatantUI(item, row);
            }
        }
    });


}

function updateCombatantUI(item, row) {
    if (row) {
        const hpElement = row.querySelector('.hp-cell');
        if (hpElement) {
            hpElement.textContent = `${item.currentHp}/${item.maxHp}`;
            // Visual feedback
            hpElement.classList.add('hp-updated');
            setTimeout(() => hpElement.classList.remove('hp-updated'), 1000);
        }
    }
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
    return turnQueueItems;
}

// Function to add a combatant to the active list
async function addToCombat(button) {
    const templateId = button.getAttribute("data-id");
    const amount = 1;

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

async function updateAvailableCreaturesList(templateCreatures) {
    console.log("in update: " + templateCreatures);
    const availableCreaturesList = document.getElementById('available-creatures');
    availableCreaturesList.innerHTML = "";

    if (!templateCreatures) return;

    const html = await renderAvailableCreatures(templateCreatures);
    availableCreaturesList.insertAdjacentHTML('beforeend', html);

}

async function updateCombatantsList(turnQueueItems) {
    const combatantsList = document.getElementById("active-combatants");
    combatantsList.innerHTML = "";

    if (!turnQueueItems) return;

    const html = await renderActiveCombatants();
    combatantsList.insertAdjacentHTML('beforeend', html);
}

async function renderAvailableCreatures(templateCreatures) {
    console.log('in render: ' + JSON.stringify(templateCreatures));
    const response = await fetch('/battle_tracker/renderCreatures', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'text/html'
        },
        body: JSON.stringify(templateCreatures)
    });
    return await response.text();
}

async function renderActiveCombatants() {
    const response = await fetch('/battle_tracker/renderCombatants', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'text/html'
        },
    });
    return await response.text();
}

async function renderSelectedTemplateCreature(id) {
    const response = await fetch(`/creatures/renderTemplateCreature?id=${id}`, {
        method: "GET",
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

async function advanceTurn() {
    const response = await fetch('battle_tracker/nextTurn', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
    });

    const currentItem = await response.json();
    await renderTurnOrder(currentItem.currentQueueItem);
}

async function renderTurnOrder(currentItem) {

    document.querySelectorAll('.table-row').forEach(row => {
        row.classList.remove(
            'current-turn'
        );
    });

    document.querySelectorAll('.table-row').forEach(row => {
        row.classList.toggle(
            'current-turn',
            parseInt(row.dataset.id) === parseInt(currentItem.id)
        );
        console.log('item: ' + row.dataset.id + 'current turn: ' + (parseInt(row.dataset.id) === parseInt(currentItem.id)))
        console.log(row)
        console.log('item id: ' + row.dataset.id)
        console.log('current item id: ' + currentItem.id)
    });
}

