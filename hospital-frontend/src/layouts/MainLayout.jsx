import { useState } from "react";
import { Outlet } from "react-router-dom";

import Sidebar from "../components/Sidebar";
import Header from "../components/Header";

function MainLayout() {
    const [sidebarOpen, setSidebarOpen] = useState(false);

    const openSidebar = () => {
        setSidebarOpen(true);
    };

    const closeSidebar = () => {
        setSidebarOpen(false);
    };

    return (
        <div className="app-shell">
            <Sidebar
                isOpen={sidebarOpen}
                onClose={closeSidebar}
            />

            <div className="main-section">
                <Header onMenuClick={openSidebar} />

                <main className="content">
                    <Outlet />
                </main>
            </div>
        </div>
    );
}

export default MainLayout;