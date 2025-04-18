const menuItems = [
    { id: 1, name: "Burger", price: 12.99, image: "https://via.placeholder.com/150" },
    { id: 2, name: "Pizza", price: 18.5, image: "https://via.placeholder.com/150" },
    { id: 3, name: "Sushi", price: 22.0, image: "https://via.placeholder.com/150" },
];

function renderMenu() {
    const container = document.getElementById('menu-container');
    container.innerHTML = "";

    menuItems.forEach(item => {
        const card = `
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <img src="${item.image}" class="card-img-top" alt="${item.name}">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${item.name}</h5>
                        <p class="card-text">RM ${item.price.toFixed(2)}</p>
                        <button class="btn btn-primary mt-auto" onclick="addToCart(${item.id})">Add to Cart</button>
                    </div>
                </div>
            </div>
        `;
        container.innerHTML += card;
    });
}

function addToCart(itemId) {
    const token = localStorage.getItem("token");

    let payload = {
        itemId: itemId,
        quantity: 1
    };

    // user is logged in
    if (token) {
        fetch("/api/order-item", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify(payload)
        }).then(res => {
            if (res.ok) {
                alert("Item added to cart!");
            } else {
                alert("Failed to add item");
            }
        });
    } else {
        // guest user
        const guestId = getGuestId(); // guest session identifier

        // add guestSessionID to payload
        payload.guestSessionId = guestId;

        fetch("api/order-item/guest", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        }).then(res => {
            if (res.ok) {
                alert("Item added to cart!");
            } else {
                alert("Failed to add item");
            }
        });
    }
}

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
    window.location.href = "login.html";
}

document.addEventListener("DOMContentLoaded", renderMenu);