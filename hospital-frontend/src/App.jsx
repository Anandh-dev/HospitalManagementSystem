import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import MainLayout from "./layouts/MainLayout";

import Dashboard from "./pages/Dashboard";
import Patients from "./pages/Patients";
import Appointments from "./pages/Appointments";
import Doctors from "./pages/Doctors";
import Nurses from "./pages/Nurses";
import OPD from "./pages/OPD";
import IPD from "./pages/IPD";
import Notifications from "./pages/Notifications";
import Settings from "./pages/Settings";

function App() {

    return (
        <BrowserRouter>

            <Routes>

                <Route path="/" element={<MainLayout />}>

                    <Route index element={<Navigate to="/dashboard" replace />} />

                    <Route path="dashboard" element={<Dashboard />} />
                    <Route path="patients" element={<Patients />} />
                    <Route path="appointments" element={<Appointments />} />
                    <Route path="doctors" element={<Doctors />} />
                    <Route path="nurses" element={<Nurses />} />
                    <Route path="opd" element={<OPD />} />
                    <Route path="ipd" element={<IPD />} />
                    <Route path="notifications" element={<Notifications />} />
                    <Route path="settings" element={<Settings />} />

                </Route>

            </Routes>

        </BrowserRouter>
    );
}

export default App;