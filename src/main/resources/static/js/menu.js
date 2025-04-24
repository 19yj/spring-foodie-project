document.addEventListener("DOMContentLoaded", () => {
    fetchMenu();
});

function fetchMenu() {
    fetch('/api/menu')
        .then(response => {
            console.log("API call made to /api/menu");
            if (!response.ok) {
                throw new Error(`HTTP status ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (!Array.isArray(data)) {
                console.log("menuItems is not an array", data);
                return;
            }
            renderMenu(data);
        })
        .catch(error => {
            console.error("Error fetching menu: ", error);
        });
}

function renderMenu(menuItems) {
    if (!Array.isArray(menuItems)) {
        console.error("menuItems is not an array", menuItems);
        return;
    }

    const container = document.getElementById('menu-container');
    container.innerHTML = "";

    menuItems.forEach(item => {
        const card = `
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <img src="${item.imageUrl}" class="card-img-top" alt="${item.itemName}">
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${item.itemName}</h5>
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
        id: itemId,
        quantity: 1
    };

    console.log("Raw token from localStorage:", localStorage.getItem('token'));

    // user is logged in
    if (token) {
        fetch("/api/order-item/user", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                // "Authorization": `Bearer ${token}`
                'Authorization': 'Bearer ' + localStorage.getItem('token')
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