class EffectManager {
    constructor() {
        this.pendingUpdates = new Map();
        this.debounceTimer = null;
        this.DEBOUNCE_DELAY = 1000;
    }

    queueUpdates(checkbox) {
        const row = checkbox.closest('.table-row');
        const combatantId = row.dataset.id;
        const effect = checkbox.value;
        const isActive = checkbox.checked;

        if (!this.pendingUpdates.has(combatantId)) {
            this.pendingUpdates.set(combatantId, new Set());
        }

        const effectsSet = this.pendingUpdates.get(combatantId);
        isActive ? effectsSet.add(effect) : effectsSet.delete(effect);

        console.log(effectsSet);
        this.debounceRequest(isActive);
    }

    debounceRequest(isActive) {
        clearTimeout(this.debounceTimer);
        this.debounceTimer = setTimeout(() => this.sendBatch(isActive), this.DEBOUNCE_DELAY);
    }

    async sendBatch(isActive) {
        if (this.pendingUpdates === 0) return;

        const updates = Array.from(this.pendingUpdates).map(([id, effects]) => ({
            itemId: id,
            effects: Array.from(effects),
            shouldAdd: isActive
        }));

        try {
            const response = await fetch('api/combatants/effects', {
                method: "PATCH",
                headers: {"Content-type": "application/json"},
                body: JSON.stringify({updates})
            });

            if (!response.ok) throw new Error("Effects update failed");

            this.pendingUpdates.clear();
        } catch (error) {
            console.error("Effects update error:", error);
        }
    }
}