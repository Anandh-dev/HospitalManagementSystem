import { request } from "./api";

export const getAppointments = () => request("/api/appointments");
export const createAppointment = (appointment) => request("/api/appointments", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(appointment),
});