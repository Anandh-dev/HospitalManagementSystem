import { request } from "./api";

export const getDoctors = () => request("/api/doctors");
export const createDoctor = (doctor) => request("/api/doctors", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(doctor),
});