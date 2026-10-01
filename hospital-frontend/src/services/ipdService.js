import { request } from "./api";

export const getIPDAdmissions = () => request("/api/ipd");
export const getIPDBeds = () => request("/api/ipd/beds");
export const getAvailableIPDBeds = () => request("/api/ipd/beds/available");
export const createIPDAdmission = (admission) => request("/api/ipd", { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(admission) });
export const dischargeIPDAdmission = (id, dischargeSummary) => request(`/api/ipd/${id}/discharge`, { method: "PUT", headers: { "Content-Type": "application/json" }, body: JSON.stringify({ dischargeSummary }) });