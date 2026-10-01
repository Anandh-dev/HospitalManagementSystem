import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PageHeader from "../components/PageHeader";
import StatePanel from "../components/StatePanel";
import { getAppointments } from "../services/appointmentService";
import { getDoctors } from "../services/doctorService";
import { getPatients } from "../services/patientService";

function Dashboard() {
    const navigate = useNavigate();
    const [patients, setPatients] = useState([]); const [appointments, setAppointments] = useState([]); const [doctors, setDoctors] = useState([]); const [loading, setLoading] = useState(true); const [error, setError] = useState("");
    const loadDashboard = useCallback(async () => { setLoading(true); setError(""); try { const [patientData, appointmentData, doctorData] = await Promise.all([getPatients(), getAppointments(), getDoctors()]); setPatients(Array.isArray(patientData) ? patientData : []); setAppointments(Array.isArray(appointmentData) ? appointmentData : []); setDoctors(Array.isArray(doctorData) ? doctorData : []); } catch (requestError) { setError(requestError.message); } finally { setLoading(false); } }, []);
    useEffect(() => { const timer = window.setTimeout(loadDashboard, 0); return () => window.clearTimeout(timer); }, [loadDashboard]);
    const today = new Date().toISOString().slice(0, 10); const todaysAppointments = appointments.filter((appointment) => appointment.appointmentDate === today); const availableDoctors = doctors.filter((doctor) => doctor.availability?.toLowerCase() === "available" && doctor.status?.toLowerCase() === "active").length;
    const cards = [["👥", "Total patients", loading ? "…" : error ? "N/A" : patients.length, error ? "Hospital APIs unavailable" : "Live patient records"], ["📅", "Today's appointments", loading ? "…" : error ? "N/A" : todaysAppointments.length, error ? "Hospital APIs unavailable" : "Live appointment records"], ["🩺", "Available doctors", loading ? "…" : error ? "N/A" : availableDoctors, error ? "Hospital APIs unavailable" : "Live doctor records"], ["🛏️", "Current IPD patients", "N/A", "IPD API not connected"]];
    return <section className="dashboard"><PageHeader title="Dashboard" description="An overview of the hospital system and connected services." /><div className="summary-cards">{cards.map(([icon, label, value, note]) => <article className="summary-card" key={label}><span className="card-icon">{icon}</span><div><p>{label}</p><h2>{value}</h2><small>{note}</small></div></article>)}</div>
        {error && <StatePanel title="Hospital data is unavailable" description={error} tone="error" action={<button className="secondary-button" onClick={loadDashboard}>Try again</button>} />}
        <section className="dashboard-section"><div className="section-header"><div><h2>Today's appointments</h2><p>{error ? "Appointment data could not be loaded." : `${todaysAppointments.length} appointment${todaysAppointments.length === 1 ? "" : "s"} scheduled today.`}</p></div><button className="secondary-button" onClick={() => navigate("/appointments")}>View appointments</button></div>{error ? <StatePanel title="Unable to load appointments" description={error} tone="error" /> : todaysAppointments.length ? <StatePanel title={`${todaysAppointments.length} appointment${todaysAppointments.length === 1 ? "" : "s"} scheduled`} description="Open Appointments to review the live directory." /> : <StatePanel title="No appointments today" description="New bookings will appear here after they are saved." />}</section>
        <section className="dashboard-section"><div className="section-header"><div><h2>Quick actions</h2><p>Jump straight to common hospital workflows.</p></div></div><div className="quick-actions"><button className="action-button" onClick={() => navigate("/patients?register=true")}>Register patient</button><button className="action-button" onClick={() => navigate("/appointments")}>Book appointment</button><button className="action-button" onClick={() => navigate("/doctors")}>View doctors</button><button className="action-button" onClick={() => navigate("/opd")}>View OPD</button></div></section>
    </section>;
}

export default Dashboard;