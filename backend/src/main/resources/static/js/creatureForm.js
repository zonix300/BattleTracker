document.addEventListener('DOMContentLoaded', function() {

    // Add Action functionality
    function setupActionButton(buttonId, containerId) {
        document.getElementById(buttonId).addEventListener('click', function() {
            const container = document.getElementById(containerId);
            const actionDiv = document.createElement('div');
            actionDiv.className = 'action-card';
            actionDiv.innerHTML = `
                <div class="action-header">
                    <input type="text" class="action-name" placeholder="Action name" required>
                    <div class="action-controls">
                        <button type="button" class="remove-action">×</button>
                    </div>
                </div>
                <textarea class="action-description" placeholder="Action description" required></textarea>
            `;
            container.appendChild(actionDiv);
        });
    }

    setupActionButton('addActionBtn', 'actionsContainer');
    setupActionButton('addBonusActionBtn', 'bonusActionsContainer');
    setupActionButton('addLegendaryActionBtn', 'legendaryActionsContainer');

    // Tag system functionality
    function setupTagSystem(inputId, containerId) {
        const input = document.getElementById(inputId);
        const container = document.getElementById(containerId);
        const addButton = input.nextElementSibling;

        addButton.addEventListener('click', function() {
            if (input.value.trim() !== '') {
                const tag = document.createElement('div');
                tag.className = 'tag';
                tag.innerHTML = `
                    <span>${input.value.trim()}</span>
                    <span class="tag-remove">×</span>
                    <input type="hidden" name="${containerId.replace('Container', '')}[]" value="${input.value.trim()}">
                `;
                container.appendChild(tag);
                input.value = '';

                tag.querySelector('.tag-remove').addEventListener('click', function() {
                    tag.remove();
                });
            }
        });

        input.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                addButton.click();
            }
        });
    }

    setupTagSystem('vulnerabilityInput', 'vulnerabilitiesContainer');
    setupTagSystem('resistanceInput', 'resistancesContainer');
    setupTagSystem('immunityInput', 'immunitiesContainer');

    // Form submission
    document.getElementById('creatureForm').addEventListener('submit', async function(e) {
        e.preventDefault();

        try {
            // Collect all form data
            const formData = {
                name: document.getElementById('name').value,
                size: document.getElementById('size').value,
                creatureType: document.getElementById('creatureType').value,
                challengeRating: document.getElementById('challengeRating').value,
                description: document.getElementById('description').value,
                // Basic stats
                armorClass: document.getElementById('armorClass').value,
                hitPoints: document.getElementById('hitPoints').value,
                // Ability scores
                strength: document.getElementById('strength').value,
                dexterity: document.getElementById('dexterity').value,
                constitution: document.getElementById('constitution').value,
                intelligence: document.getElementById('intelligence').value,
                wisdom: document.getElementById('wisdom').value,
                charisma: document.getElementById('charisma').value,
                // Dynamic collections
                speeds: Array.from(document.querySelectorAll('input[name="speeds[]"]')).map(i => i.value),
                senses: Array.from(document.querySelectorAll('input[name="senses[]"]')).map(i => i.value),
                languages: Array.from(document.querySelectorAll('input[name="languages[]"]')).map(i => i.value),
                savingThrowProficiencies: Array.from(document.querySelectorAll('input[name="savingThrowProficiencies[]"]:checked')).map(i => i.value),
                skills: Array.from(document.querySelectorAll('input[name="skills[]"]:checked')).map(i => i.value),
                // Actions and special abilities
                actions: collectActionData('actionsContainer'),
                bonusActions: collectActionData('bonusActionsContainer'),
                legendaryActions: collectActionData('legendaryActionsContainer'),
                specialAbilities: collectActionData('specialAbilitiesContainer'),
                // Damage properties
                damageVulnerabilities: Array.from(document.querySelectorAll('input[name="damageVulnerabilities[]"]')).map(i => i.value),
                damageResistances: Array.from(document.querySelectorAll('input[name="damageResistances[]"]')).map(i => i.value),
                damageImmunities: Array.from(document.querySelectorAll('input[name="damageImmunities[]"]')).map(i => i.value),
                conditionImmunities: Array.from(document.querySelectorAll('input[name="conditionImmunities[]"]')).map(i => i.value)
            };

            // Helper function to collect action data
            function collectActionData(containerId) {
                const container = document.getElementById(containerId);
                return Array.from(container.querySelectorAll('.action-card')).map(card => {
                    return {
                        name: card.querySelector('.action-name').value,
                        description: card.querySelector('.action-description').value
                    };
                });
            }

            // Send to API
            const response = await fetch('/creatures/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) throw new Error('Failed to create creature');

            const creature = await response.json();
            window.location.href = '/battleTracker';

        } catch (error) {
            console.error('Error:', error);
            alert('Failed to create creature. Please check console for details.');
        }
    });

    // Cancel button
    document.querySelector('.cancel-button').addEventListener('click', function() {
        if (confirm('Are you sure you want to cancel? All unsaved changes will be lost.')) {
            window.location.href = '/battleTracker';
        }
    });
});