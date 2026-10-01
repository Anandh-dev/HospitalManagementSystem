import { request } from "./api";

const notificationService = {
    getAllNotifications: async () => {
        return request("/api/notifications");
    },

    getUnreadNotifications: async () => {
        return request("/api/notifications/unread");
    },

    getUnreadCount: async () => {
        return request("/api/notifications/count");
    },

    getNotificationById: async (id) => {
        return request(`/api/notifications/${id}`);
    },

    createNotification: async (data) => {
        return request("/api/notifications", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });
    },

    markAsRead: async (id) => {
        return request(`/api/notifications/${id}/read`, {
            method: "PUT",
        });
    },

    markAllAsRead: async () => {
        return request("/api/notifications/read-all", {
            method: "PUT",
        });
    },

    deleteNotification: async (id) => {
        return request(`/api/notifications/${id}`, {
            method: "DELETE",
        });
    },
};

export default notificationService;