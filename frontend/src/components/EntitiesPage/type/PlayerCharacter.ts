export type PlayerCharacter = {
    id: number,
    about: string,
    clazz: string,
    experience: number,
    hp: {
        max: number,
        current: number,
        temporary: number
    },
    level: number,
    name: string,
    speed: {
        walk: number
    },
    armorClass: number,
    abilities: {
        dexterity: number
    }

}