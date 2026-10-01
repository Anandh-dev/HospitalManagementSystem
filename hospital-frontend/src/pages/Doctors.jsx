import { useCallback, useEffect, useMemo, useState } from "react";
import Modal from "../components/Modal";
import PageHeader from "../components/PageHeader";
import StatePanel from "../components/StatePanel";
import StatusBadge from "../components/StatusBadge";
import { createDoctor, getDoctors } from "../services/doctorService";

const emptyDoctor = { fullName: "", specialization: "", department: "", mobileNumber: "", email: "", availability: "Available", status: "ACTIVE" };

function Doctors() {
	const [doctors, setDoctors] = useState([]);
	const [searchTerm, setSearchTerm] = useState("");
	const [availability, setAvailability] = useState("All availability");
	const [status, setStatus] = useState("All status");
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState("");
	const [isFormOpen, setIsFormOpen] = useState(false);
	const [formData, setFormData] = useState(emptyDoctor);
	const [formError, setFormError] = useState("");
	const [saving, setSaving] = useState(false);
	const [successMessage, setSuccessMessage] = useState("");

	const loadDoctors = useCallback(async () => {
		setLoading(true); setError("");
		try { const data = await getDoctors(); setDoctors(Array.isArray(data) ? data : []); }
		catch (requestError) { setError(requestError.message); }
		finally { setLoading(false); }
	}, []);

	useEffect(() => { const initialLoad = window.setTimeout(loadDoctors, 0); return () => window.clearTimeout(initialLoad); }, [loadDoctors]);

	const visibleDoctors = useMemo(() => doctors.filter((doctor) => {
		const query = searchTerm.trim().toLowerCase();
		const matchesSearch = !query || [doctor.doctorNumber, doctor.fullName, doctor.specialization, doctor.department, doctor.mobileNumber, doctor.email].filter(Boolean).some((value) => value.toLowerCase().includes(query));
		return matchesSearch && (availability === "All availability" || doctor.availability === availability) && (status === "All status" || doctor.status === status);
	}), [doctors, searchTerm, availability, status]);

	const handleChange = ({ target: { name, value } }) => setFormData((current) => ({ ...current, [name]: value }));
	const closeForm = () => { if (!saving) { setIsFormOpen(false); setFormData(emptyDoctor); setFormError(""); } };
	const submit = async (event) => {
		event.preventDefault(); setFormError(""); setSaving(true);
		const payload = Object.fromEntries(Object.entries(formData).map(([key, value]) => [key, value.trim?.() ?? value]));
		if (!payload.email) payload.email = null;
		try { const created = await createDoctor(payload); setIsFormOpen(false); setFormData(emptyDoctor); setSuccessMessage(`Doctor ${created.doctorNumber} registered successfully.`); await loadDoctors(); }
		catch (requestError) { setFormError(requestError.message); }
		finally { setSaving(false); }
	};

	return <section className="module-page">
		<PageHeader title="Doctors" description="Manage physician profiles, availability, and departments." action={<button className="primary-button" type="button" onClick={() => setIsFormOpen(true)}>+ Add doctor</button>} />
		{successMessage && <div className="success-banner" role="status">{successMessage}<button type="button" onClick={() => setSuccessMessage("")} aria-label="Dismiss success message">×</button></div>}
		{isFormOpen && <Modal title="Add doctor" onClose={closeForm}><form className="patient-form modal-body" onSubmit={submit}>
			<label>Full name *<input name="fullName" value={formData.fullName} onChange={handleChange} maxLength="100" required /></label>
			<label>Specialization *<input name="specialization" value={formData.specialization} onChange={handleChange} maxLength="100" required /></label>
			<label>Department *<input name="department" value={formData.department} onChange={handleChange} maxLength="100" required /></label>
			<label>Mobile number *<input name="mobileNumber" value={formData.mobileNumber} onChange={handleChange} inputMode="numeric" pattern="[0-9]{10,15}" required /></label>
			<label>Email<input name="email" type="email" value={formData.email} onChange={handleChange} /></label>
			<label>Availability *<select name="availability" value={formData.availability} onChange={handleChange} required><option>Available</option><option>Unavailable</option></select></label>
			<label>Status *<select name="status" value={formData.status} onChange={handleChange} required><option value="ACTIVE">Active</option><option value="INACTIVE">Inactive</option></select></label>
			{formError && <p className="form-error" role="alert">{formError}</p>}<div className="form-actions"><button className="secondary-button" type="button" onClick={closeForm}>Cancel</button><button className="primary-button" type="submit" disabled={saving}>{saving ? "Saving…" : "Save doctor"}</button></div>
		</form></Modal>}
		<section className="data-card"><div className="directory-toolbar"><input aria-label="Search doctors" placeholder="Search name, ID, specialty…" value={searchTerm} onChange={(event) => setSearchTerm(event.target.value)} /><select aria-label="Availability" value={availability} onChange={(event) => setAvailability(event.target.value)}><option>All availability</option>{[...new Set(doctors.map((doctor) => doctor.availability).filter(Boolean))].map((value) => <option key={value}>{value}</option>)}</select><select aria-label="Status" value={status} onChange={(event) => setStatus(event.target.value)}><option>All status</option>{[...new Set(doctors.map((doctor) => doctor.status).filter(Boolean))].map((value) => <option key={value}>{value}</option>)}</select><button className="secondary-button" type="button" onClick={loadDoctors} disabled={loading}>Refresh</button></div>
			{error ? <StatePanel title="Unable to load doctors" description={error} tone="error" action={<button className="secondary-button" type="button" onClick={loadDoctors}>Try again</button>} /> : loading ? <StatePanel title="Loading doctors…" /> : visibleDoctors.length === 0 ? <StatePanel title={doctors.length === 0 ? "No doctors registered yet." : "No doctors match your filters."} /> : <div className="table-scroll"><table className="directory-table"><thead><tr><th>Doctor</th><th>Doctor ID</th><th>Specialization</th><th>Department</th><th>Phone</th><th>Email</th><th>Availability</th><th>Status</th></tr></thead><tbody>{visibleDoctors.map((doctor) => <tr key={doctor.doctorId ?? doctor.doctorNumber}><td data-label="Doctor"><strong>{doctor.fullName}</strong></td><td data-label="Doctor ID">{doctor.doctorNumber}</td><td data-label="Specialization">{doctor.specialization}</td><td data-label="Department">{doctor.department}</td><td data-label="Phone">{doctor.mobileNumber}</td><td data-label="Email">{doctor.email || "—"}</td><td data-label="Availability">{doctor.availability}</td><td data-label="Status"><StatusBadge tone={doctor.status?.toLowerCase() === "active" ? "success" : "neutral"}>{doctor.status}</StatusBadge></td></tr>)}</tbody></table></div>}
		</section>
	</section>;
}

export default Doctors;
