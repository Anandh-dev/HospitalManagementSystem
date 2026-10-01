import { request } from "./api";

export const getPatients = () => request("/api/patients");
export const getPatientById = (id) => request(`/api/patients/${id}`);
export const createPatient = (patient) => request("/api/patients", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(patient),
});
