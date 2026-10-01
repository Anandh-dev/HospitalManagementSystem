import { NavLink } from "react-router-dom";

function Header({ onMenuClick }) {
    return (
        <header className="header">

            <div className="header-left">

                <button
                    type="button"
                    className="menu-button"
                    onClick={onMenuClick}
                    aria-label="Open navigation menu"
                >
                    <span />
                    <span />
                    <span />
                </button>

                <div className="header-brand">
                    <span className="header-brand-name">
                        Hospital Management System
                    </span>

                    <span className="header-brand-divider">
                        /
                    </span>

                    <span className="header-brand-section">
                        HMS
                    </span>
                </div>

            </div>

            <div className="header-right">

                <div className="header-search">
                    <span
                        className="header-search-icon"
                        aria-hidden="true"
                    >
                        ⌕
                    </span>

                    <input
                        type="search"
                        placeholder="Search..."
                        aria-label="Search"
                    />

                    <span className="header-search-shortcut">
                        Ctrl K
                    </span>
                </div>

                <NavLink
                    to="/notifications"
                    className="header-notification"
                    aria-label="Notifications"
                >
                    <span className="header-notification-icon">
                        ♢
                    </span>

                    <span className="header-notification-dot" />
                </NavLink>

                <div className="header-divider" />

                <button
                    type="button"
                    className="header-user"
                    aria-label="Admin profile"
                >
                    <span className="header-user-avatar">
                        A
                    </span>

                    <span className="header-user-details">
                        <strong>Admin</strong>
                        <small>Administrator</small>
                    </span>

                    <span className="header-user-arrow">
                        ▾
                    </span>
                </button>

            </div>

        </header>
    );
}

export default Header;