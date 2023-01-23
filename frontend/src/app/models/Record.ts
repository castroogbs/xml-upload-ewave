import { Agent } from "./Agent";

export interface Record {
    id: string;
    region: string;
    recordDate: string;
    value: number;
    agent: Agent;
    generation: boolean;
    purchase: boolean;
}
