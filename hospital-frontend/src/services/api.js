export async function request(path, options = {}) {
    let response;

    try {
        response = await fetch(path, options);
    } catch {
        throw new Error("Network error. Check that the hospital API is running.");
    }

    const body = await response.json().catch(() => null);
    if (response.ok) return body;

    if (body?.message) throw new Error(body.message);
    if (body && typeof body === "object") {
        const details = Object.values(body).filter(Boolean).join(" ");
        if (details) throw new Error(details);
    }

    const messages = {
        400: "Please review the entered details and try again.",
        401: "Your session has expired. Please sign in again.",
        403: "You do not have permission to perform this action.",
        404: "The requested record could not be found.",
        409: "A record with these details already exists.",
        500: "The hospital server encountered an error. Please try again.",
    };
    throw new Error(messages[response.status] ?? "The request could not be completed.");
}
