function StatePanel({ title, description, action, tone = "default" }) {
    return <div className={`state-panel ${tone}`}><strong>{title}</strong>{description && <p>{description}</p>}{action}</div>;
}

export default StatePanel;
