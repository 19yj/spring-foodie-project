function getGuestId() {
    let guestId = localStorage.getItem("guestId");
    if (!guestId) {
        guestId = crypto.randomUUID();
        localStorage.setItem("guestId", guestId);
    }
    return guestId;
}

function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("guestId"); // clear guest session if needed
    window.location.href = "login.html";
}