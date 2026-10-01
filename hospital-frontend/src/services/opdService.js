import { request } from "./api";

export const getOPDVisits = () => request("/api/opd");
export const createOPDVisit = (visit) => request("/api/opd", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(visit),
});
export const checkInOPDVisit = (id) => request(`/api/opd/${id}/check-in`, { method: "PUT" });
export const startOPDConsultation = (id) => request(`/api/opd/${id}/start-consultation`, { method: "PUT" });
export const completeOPDVisit = (id) => request(`/api/opd/${id}/complete`, { method: "PUT" });