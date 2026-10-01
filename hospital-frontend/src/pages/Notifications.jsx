import { useCallback, useEffect, useMemo, useState } from "react";
import notificationService from "../services/notificationService";
import PageHeader from "../components/PageHeader";
import StatePanel from "../components/StatePanel";

const TYPE_LABELS = {
    APPOINTMENT: "Appointment",
    OPD: "OPD",
    IPD: "IPD",
    SYSTEM: "System",
};

const PRIORITY_LABELS = {
    LOW: "Low",
    NORMAL: "Normal",
    HIGH: "High",
    URGENT: "Urgent",
};

function formatDateTime(value) {
    if (!value) return "—";

    const date = new Date(value);

    if (Number.isNaN(date.getTime())) {
        return value;
    }

    return date.toLocaleString("en-IN", {
        day: "2-digit",
        month: "short",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit",
    });
}

function getTypeLabel(type) {
    return TYPE_LABELS[type] ?? type;
}

function getPriorityLabel(priority) {
    return PRIORITY_LABELS[priority] ?? priority;
}

function NotificationCard({
    notification,
    onMarkAsRead,
    onDelete,
    processingId,
}) {
    const isProcessing = processingId === notification.id;

    return (
        <article
            className={`notification-card ${
                notification.read ? "notification-card-read" : "notification-card-unread"
            }`}
        >
            <div className="notification-card-main">
                <div className="notification-card-indicator">
                    {!notification.read && (
                        <span
                            className="notification-unread-dot"
                            aria-label="Unread notification"
                        />
                    )}
                </div>

                <div className="notification-content">
                    <div className="notification-card-top">
                        <div>
                            <h3 className="notification-title">
                                {notification.title}
                            </h3>

                            <div className="notification-meta">
                                <span
                                    className={`notification-type notification-type-${String(
                                        notification.type
                                    ).toLowerCase()}`}
                                >
                                    {getTypeLabel(notification.type)}
                                </span>

                                <span
                                    className={`notification-priority notification-priority-${String(
                                        notification.priority
                                    ).toLowerCase()}`}
                                >
                                    {getPriorityLabel(notification.priority)}
                                </span>

                                {!notification.read && (
                                    <span className="notification-new-badge">
                                        New
                                    </span>
                                )}
                            </div>
                        </div>

                        <span className="notification-time">
                            {formatDateTime(notification.createdAt)}
                        </span>
                    </div>

                    <p className="notification-message">
                        {notification.message}
                    </p>

                    {notification.referenceType && notification.referenceId && (
                        <div className="notification-reference">
                            Reference: {notification.referenceType} #
                            {notification.referenceId}
                        </div>
                    )}

                    <div className="notification-card-actions">
                        {!notification.read && (
                            <button
                                type="button"
                                className="notification-action notification-action-primary"
                                onClick={() => onMarkAsRead(notification.id)}
                                disabled={isProcessing}
                            >
                                {isProcessing ? "Updating..." : "Mark as read"}
                            </button>
                        )}

                        <button
                            type="button"
                            className="notification-action notification-action-danger"
                            onClick={() => onDelete(notification.id)}
                            disabled={isProcessing}
                        >
                            Delete
                        </button>
                    </div>
                </div>
            </div>
        </article>
    );
}

export default function Notifications() {
    const [notifications, setNotifications] = useState([]);
    const [loading, setLoading] = useState(true);
    const [refreshing, setRefreshing] = useState(false);
    const [error, setError] = useState("");
    const [processingId, setProcessingId] = useState(null);
    const [actionError, setActionError] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    const loadNotifications = useCallback(async (showRefreshState = false) => {
        try {
            setError("");

            if (showRefreshState) {
                setRefreshing(true);
            } else {
                setLoading(true);
            }

            const data = await notificationService.getAllNotifications();

            setNotifications(Array.isArray(data) ? data : []);
        } catch (err) {
            setError(
                err?.message ||
                    "Unable to load notifications. Please try again."
            );
        } finally {
            setLoading(false);
            setRefreshing(false);
        }
    }, []);

    useEffect(() => {
        loadNotifications();
    }, [loadNotifications]);

    const unreadCount = useMemo(
        () => notifications.filter((notification) => !notification.read).length,
        [notifications]
    );

    const totalCount = notifications.length;

    const handleMarkAsRead = async (id) => {
        try {
            setProcessingId(id);
            setActionError("");
            setSuccessMessage("");

            const updatedNotification =
                await notificationService.markAsRead(id);

            setNotifications((current) =>
                current.map((notification) =>
                    notification.id === id
                        ? updatedNotification
                        : notification
                )
            );

            setSuccessMessage("Notification marked as read.");
        } catch (err) {
            setActionError(
                err?.message ||
                    "Unable to update the notification."
            );
        } finally {
            setProcessingId(null);
        }
    };

    const handleMarkAllAsRead = async () => {
        if (unreadCount === 0) {
            return;
        }

        try {
            setActionError("");
            setSuccessMessage("");

            await notificationService.markAllAsRead();

            setNotifications((current) =>
                current.map((notification) => ({
                    ...notification,
                    read: true,
                    readAt: notification.readAt ?? new Date().toISOString(),
                }))
            );

            setSuccessMessage(
                `${unreadCount} notification${
                    unreadCount === 1 ? "" : "s"
                } marked as read.`
            );
        } catch (err) {
            setActionError(
                err?.message ||
                    "Unable to mark all notifications as read."
            );
        }
    };

    const handleDelete = async (id) => {
        const confirmed = window.confirm(
            "Are you sure you want to delete this notification?"
        );

        if (!confirmed) {
            return;
        }

        try {
            setProcessingId(id);
            setActionError("");
            setSuccessMessage("");

            await notificationService.deleteNotification(id);

            setNotifications((current) =>
                current.filter((notification) => notification.id !== id)
            );

            setSuccessMessage("Notification deleted.");
        } catch (err) {
            setActionError(
                err?.message ||
                    "Unable to delete the notification."
            );
        } finally {
            setProcessingId(null);
        }
    };

    return (
        <div className="notifications-page">
            <PageHeader
                title="Notifications"
                subtitle="Stay updated with important hospital activities and system events."
            />

            <div className="notifications-toolbar">
                <div className="notifications-summary">
                    <div className="notification-summary-card">
                        <span className="notification-summary-label">
                            Total
                        </span>
                        <strong>{totalCount}</strong>
                    </div>

                    <div className="notification-summary-card notification-summary-unread">
                        <span className="notification-summary-label">
                            Unread
                        </span>
                        <strong>{unreadCount}</strong>
                    </div>
                </div>

                <div className="notifications-toolbar-actions">
                    <button
                        type="button"
                        className="notification-toolbar-button"
                        onClick={() => loadNotifications(true)}
                        disabled={refreshing}
                    >
                        {refreshing ? "Refreshing..." : "Refresh"}
                    </button>

                    <button
                        type="button"
                        className="notification-toolbar-button notification-toolbar-primary"
                        onClick={handleMarkAllAsRead}
                        disabled={unreadCount === 0}
                    >
                        Mark all as read
                    </button>
                </div>
            </div>

            {actionError && (
                <div className="notification-alert notification-alert-error">
                    {actionError}
                </div>
            )}

            {successMessage && (
                <div className="notification-alert notification-alert-success">
                    {successMessage}
                </div>
            )}

            {loading ? (
                <StatePanel
                    type="loading"
                    title="Loading notifications"
                    message="Fetching the latest notifications..."
                />
            ) : error ? (
                <StatePanel
                    type="error"
                    title="Unable to load notifications"
                    message={error}
                    actionLabel="Try again"
                    onAction={() => loadNotifications()}
                />
            ) : notifications.length === 0 ? (
                <StatePanel
                    type="empty"
                    title="No notifications"
                    message="There are no notifications to display right now."
                />
            ) : (
                <section className="notifications-list">
                    {notifications.map((notification) => (
                        <NotificationCard
                            key={notification.id}
                            notification={notification}
                            onMarkAsRead={handleMarkAsRead}
                            onDelete={handleDelete}
                            processingId={processingId}
                        />
                    ))}
                </section>
            )}
        </div>
    );
}