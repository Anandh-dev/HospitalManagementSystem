import { useState } from "react";
import Modal from "./Modal";
import PageHeader from "./PageHeader";
import StatePanel from "./StatePanel";

function PendingDirectory({ title, description, addLabel, columns, filters = [] }) {
    const [showForm, setShowForm] = useState(false);
    return <section className="module-page"><PageHeader title={title} description={description} action={<button className="primary-button" onClick={() => setShowForm(true)}>+ {addLabel}</button>} />
        <div className="integration-notice"><strong>Backend integration pending</strong><span>This module has no API in the current Spring Boot backend. Data is not being simulated or saved.</span></div>
        <section className="data-card"><div className="directory-toolbar"><input aria-label={`Search ${title}`} placeholder={`Search ${title.toLowerCase()}…`} disabled />{filters.map((filter) => <select key={filter} aria-label={filter} disabled><option>{filter}</option></select>)}</div>
            <div className="table-scroll"><table className="directory-table"><thead><tr>{columns.map((column) => <th key={column}>{column}</th>)}</tr></thead><tbody><tr><td colSpan={columns.length}><StatePanel title={`No ${title.toLowerCase()} data connected`} description="Connect the corresponding backend API to load and manage these records." /></td></tr></tbody></table></div></section>
        {showForm && <Modal title={addLabel} onClose={() => setShowForm(false)}><div className="modal-body"><StatePanel title="Not available yet" description={`The ${title.toLowerCase()} API has not been implemented, so this form cannot save records yet.`} /><div className="form-actions"><button className="secondary-button" onClick={() => setShowForm(false)}>Close</button></div></div></Modal>}
    </section>;
}
export default PendingDirectory;
