import { request } from "./api";

const settingsService = {
    getAllSettings: async () => {
        return request("/api/settings");
    },

    getSetting: async (key) => {
        return request(`/api/settings/${encodeURIComponent(key)}`);
    },

    updateSetting: async (key, data) => {
        return request(`/api/settings/${encodeURIComponent(key)}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });
    },

    createSetting: async (data) => {
        return request("/api/settings", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });
    },
};

export default settingsService;