import { request } from "./api";

export const getNurses = () => request("/api/nurses");
export const createNurse = (nurse) => request("/api/nurses", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(nurse),
});