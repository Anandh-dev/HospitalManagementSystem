import { NavLink } from "react-router-dom";

const navigationGroups = [
    {
        title: "MAIN",
        items: [
            {
                to: "/dashboard",
                label: "Dashboard",
                icon: "⌂",
            },
        ],
    },
    {
        title: "CLINICAL",
        items: [
            {
                to: "/patients",
                label: "Patients",
                icon: "♙",
            },
            {
                to: "/appointments",
                label: "Appointments",
                icon: "▣",
            },
            {
                to: "/opd",
                label: "OPD",
                icon: "▤",
            },
            {
                to: "/ipd",
                label: "IPD",
                icon: "▥",
            },
        ],
    },
    {
        title: "STAFF",
        items: [
            {
                to: "/doctors",
                label: "Doctors",
                icon: "♙",
            },
            {
                to: "/nurses",
                label: "Nurses",
                icon: "♙",
            },
        ],
    },
    {
        title: "SYSTEM",
        items: [
            {
                to: "/notifications",
                label: "Notifications",
                icon: "♢",
            },
            {
                to: "/settings",
                label: "Settings",
                icon: "⚙",
            },
        ],
    },
];

function Sidebar({ isOpen, onClose }) {
    const handleNavigation = () => {
        onClose();
    };

    return (
        <>
            {isOpen && (
                <div
                    className="sidebar-overlay"
                    onClick={onClose}
                    aria-hidden="true"
                />
            )}

            <aside
                className={`sidebar ${
                    isOpen ? "sidebar-open" : ""
                }`}
            >
                <div className="sidebar-brand">
                    <div className="sidebar-brand-mark">
                        HMS
                    </div>

                    <div className="sidebar-brand-text">
                        <strong>HMS</strong>
                        <span>Hospital Management</span>
                    </div>
                </div>

                <div className="sidebar-divider" />

                <nav className="sidebar-navigation">
                    {navigationGroups.map((group) => (
                        <div
                            className="sidebar-group"
                            key={group.title}
                        >
                            <div className="sidebar-group-title">
                                {group.title}
                            </div>

                            <div className="sidebar-group-items">
                                {group.items.map((item) => (
                                    <NavLink
                                        key={item.to}
                                        to={item.to}
                                        onClick={handleNavigation}
                                        className={({ isActive }) =>
                                            `sidebar-link ${
                                                isActive
                                                    ? "sidebar-link-active"
                                                    : ""
                                            }`
                                        }
                                    >
                                        <span
                                            className="sidebar-link-icon"
                                            aria-hidden="true"
                                        >
                                            {item.icon}
                                        </span>

                                        <span className="sidebar-link-label">
                                            {item.label}
                                        </span>
                                    </NavLink>
                                ))}
                            </div>
                        </div>
                    ))}
                </nav>

                <div className="sidebar-footer">
                    <div className="sidebar-footer-status">
                        <span className="sidebar-status-dot" />

                        <div>
                            <strong>System Online</strong>
                            <span>Hospital HMS</span>
                        </div>
                    </div>
                </div>
            </aside>
        </>
    );
}

export default Sidebar;