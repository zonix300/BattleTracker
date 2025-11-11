import { User } from "../../type/User"
import { Player } from "./Player"

export type Lobby = {
    id: number,
    name: string,
    ownerUsername: string,
    players: Player[],
    playersNumber: number,
    createdAt: string,
    hasPassword: boolean
}