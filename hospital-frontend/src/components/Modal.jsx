function Modal({ title, onClose, children }) {
    return <div className="modal-backdrop" role="presentation" onMouseDown={onClose}>
        <section className="modal" role="dialog" aria-modal="true" aria-label={title} onMouseDown={(event) => event.stopPropagation()}>
            <div className="modal-header"><h2>{title}</h2><button className="close-button" type="button" onClick={onClose} aria-label="Close">×</button></div>
            {children}
        </section>
    </div>;
}

export default Modal;
