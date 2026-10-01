import { useCallback, useEffect, useMemo, useState } from "react";
import { useLocation } from "react-router-dom";
import Modal from "../components/Modal";
import PageHeader from "../components/PageHeader";
import StatePanel from "../components/StatePanel";
import { createPatient, getPatients } from "../services/patientService";
const emptyPatient = { fullName: "", dateOfBirth: "", gender: "", mobileNumber: "", whatsappNumber: "", email: "", address: "" };

function Patients() {
    const { search } = useLocation();
    const [patients, setPatients] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [isLoading, setIsLoading] = useState(true);
    const [loadError, setLoadError] = useState("");
    const [isFormOpen, setIsFormOpen] = useState(() => new URLSearchParams(search).get("register") === "true");
    const [formData, setFormData] = useState(emptyPatient);
    const [formError, setFormError] = useState("");
    const [isSaving, setIsSaving] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const [selectedPatient, setSelectedPatient] = useState(null);

    const loadPatients = useCallback(async () => {
        setIsLoading(true);
        setLoadError("");
        try {
            const data = await getPatients();
            setPatients(Array.isArray(data) ? data : []);
        } catch (requestError) {
            setLoadError(requestError.message);
        } finally {
            setIsLoading(false);
        }
    }, []);

    useEffect(() => {
        const initialLoad = window.setTimeout(loadPatients, 0);
        return () => window.clearTimeout(initialLoad);
    }, [loadPatients]);

    const visiblePatients = useMemo(() => {
        const query = searchTerm.trim().toLowerCase();
        if (!query) return patients;
        return patients.filter((patient) =>
            [patient.patientNumber, patient.fullName, patient.mobileNumber, patient.email]
                .filter(Boolean)
                .some((value) => value.toLowerCase().includes(query))
        );
    }, [patients, searchTerm]);

    const handleChange = ({ target: { name, value } }) => setFormData((current) => ({ ...current, [name]: value }));

    const closeForm = () => {
        if (isSaving) return;
        setIsFormOpen(false);
        setFormData(emptyPatient);
        setFormError("");
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setFormError("");
        setIsSaving(true);
        const optionalFields = new Set(["dateOfBirth", "whatsappNumber", "email", "address"]);
        const payload = Object.fromEntries(Object.entries(formData).map(([key, value]) => {
            const normalizedValue = value.trim?.() ?? value;
            return [key, !normalizedValue && optionalFields.has(key) ? null : normalizedValue];
        }));
        try {
            const responseBody = await createPatient(payload);
            setPatients((current) => [responseBody, ...current]);
            setIsFormOpen(false);
            setFormData(emptyPatient);
            setSuccessMessage(`Patient ${responseBody.patientNumber} was registered successfully.`);
        } catch (requestError) {
            setFormError(requestError.message);
        } finally {
            setIsSaving(false);
        }
    };

    return (
        <section className="patients-page" aria-labelledby="patients-title">
            <PageHeader title="Patients" description="Register and manage hospital patient records." action={<button className="primary-button" type="button" onClick={() => setIsFormOpen(true)}>+ Add patient</button>} />
            {successMessage && <div className="success-banner" role="status">{successMessage}<button onClick={() => setSuccessMessage("")} aria-label="Dismiss success message">×</button></div>}
            {isFormOpen && <div className="patient-form-card"><div className="patient-form-heading"><div><h2>Register patient</h2><p>Fields marked with * are required.</p></div><button className="close-button" type="button" onClick={closeForm} aria-label="Close registration form">×</button></div>
                <form className="patient-form" onSubmit={handleSubmit}>
                    <label>Full name *<input name="fullName" value={formData.fullName} onChange={handleChange} maxLength="100" required /></label>
                    <label>Date of birth<input name="dateOfBirth" type="date" value={formData.dateOfBirth} onChange={handleChange} /></label>
                    <label>Gender *<select name="gender" value={formData.gender} onChange={handleChange} required><option value="">Select gender</option><option value="Male">Male</option><option value="Female">Female</option><option value="Other">Other</option></select></label>
                    <label>Mobile number *<input name="mobileNumber" value={formData.mobileNumber} onChange={handleChange} inputMode="numeric" pattern="[0-9]{10,15}" title="Enter 10 to 15 digits" required /></label>
                    <label>WhatsApp number<input name="whatsappNumber" value={formData.whatsappNumber} onChange={handleChange} inputMode="numeric" pattern="[0-9]{10,15}" title="Enter 10 to 15 digits" /></label>
                    <label>Email address<input name="email" type="email" value={formData.email} onChange={handleChange} /></label>
                    <label className="form-field-wide">Address<textarea name="address" value={formData.address} onChange={handleChange} maxLength="255" rows="3" /></label>
                    {formError && <p className="form-error" role="alert">{formError}</p>}<div className="form-actions"><button className="secondary-button" type="button" onClick={closeForm}>Cancel</button><button className="primary-button" type="submit" disabled={isSaving}>{isSaving ? "Saving…" : "Save patient"}</button></div>
                </form></div>}
            <div className="patients-card"><div className="patients-toolbar"><div><h2>Patient directory</h2><p>{isLoading ? "Loading records…" : `${patients.length} patient${patients.length === 1 ? "" : "s"} registered`}</p></div><div className="directory-actions"><input aria-label="Search patients" placeholder="Search name, ID, mobile…" value={searchTerm} onChange={(event) => setSearchTerm(event.target.value)} /><button className="secondary-button" type="button" onClick={loadPatients} disabled={isLoading}>Refresh</button></div></div>
                {loadError ? <StatePanel title="Unable to load patients" description={loadError} tone="error" action={<button className="secondary-button" type="button" onClick={loadPatients}>Try again</button>} /> : isLoading ? <StatePanel title="Loading patient records" /> : visiblePatients.length === 0 ? <StatePanel title={patients.length === 0 ? "No patients registered yet" : "No patients match your search"} /> : <div className="patients-table-wrap"><table className="patients-table"><thead><tr><th>Patient</th><th>Patient ID</th><th>Gender</th><th>Date of birth</th><th>Mobile</th><th>Email</th></tr></thead><tbody>{visiblePatients.map((patient) => <tr key={patient.patientId ?? patient.patientNumber} onClick={() => setSelectedPatient(patient)} className="clickable-row"><td data-label="Patient"><strong>{patient.fullName}</strong></td><td data-label="Patient ID">{patient.patientNumber ?? "—"}</td><td data-label="Gender">{patient.gender}</td><td data-label="Date of birth">{patient.dateOfBirth ?? "—"}</td><td data-label="Mobile">{patient.mobileNumber}</td><td data-label="Email">{patient.email || "—"}</td></tr>)}</tbody></table></div>}
            </div>
            {selectedPatient && <Modal title="Patient profile" onClose={() => setSelectedPatient(null)}><div className="profile-grid">{[["Patient number", selectedPatient.patientNumber], ["Name", selectedPatient.fullName], ["Gender", selectedPatient.gender], ["Date of birth", selectedPatient.dateOfBirth], ["Mobile", selectedPatient.mobileNumber], ["WhatsApp", selectedPatient.whatsappNumber], ["Email", selectedPatient.email], ["Address", selectedPatient.address]].map(([label, value]) => <div key={label}><small>{label}</small><strong>{value || "—"}</strong></div>)}</div></Modal>}
        </section>
    );
}

export default Patients;
