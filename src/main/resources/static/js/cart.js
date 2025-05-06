document.addEventListener("DOMContentLoaded", () => {
    fetchCart();
});

function fetchCart() {
    const token = localStorage.getItem("token");
    const guestId = getGuestId();

    let apiUrl;
    let headers = { "Content-Type": "application/json" };

    if (token) {
        apiUrl = "/api/cart/user";
        headers.Authorization = `Bearer ${token}`;
    } else if (guestId) {
        apiUrl = `/api/cart/guest?guestSessionId=${guestId}`;
    } else {
        renderEmptyCart();
        return;
    }

    console.log("Fetching cart from:", apiUrl);
    fetch(apiUrl, { headers })
        .then(response => {
            if (response.status === 403) throw new Error("Unauthorized");
            if (!response.ok) throw new Error("Failed to fetch cart");
            return response.json();
        })
        .then(renderCart)
        .catch(error => {
            console.error("Error:", error);
            if (error.message.includes("Unauthorized")) {
                window.location.href = "login.html";
            }
            renderEmptyCart();
        });


    console.log("Making request to:", apiUrl, "with headers:", headers);
    fetch(apiUrl, { headers })
        .then(response => {
            console.log("Response status:", response.status);  // <-- Add this
            if (!response.ok) throw new Error("Failed to fetch cart");
            return response.json();
        })
        .catch(error => {
            console.error("Full error details:", error);  // <-- Enhanced logging
        });
}

// Render cart items in the UI
function renderCart(cartItems) {
    const cartContainer = document.getElementById("cart-container");
    cartContainer.innerHTML = "";

    let totalPrice = 0;

    cartItems.forEach(item => {
        const itemTotal = item.price * item.quantity;
        totalPrice += itemTotal;

        const cartItemElement = `
            <div class="cart-item mb-3 p-3 border rounded">
                <h5>${item.itemName}</h5>
                <p>Price: RM ${item.price.toFixed(2)}</p>
                <p>Quantity: ${item.quantity}</p>
                <p>Total: RM ${itemTotal.toFixed(2)}</p>
                <button class="btn btn-danger btn-sm" onclick="removeFromCart(${item.id})">Remove</button>
            </div>
        `;
        cartContainer.innerHTML += cartItemElement;
    });

    // Add total price display
    cartContainer.innerHTML += `
        <div class="cart-total mt-3 p-3 bg-light rounded">
            <h4>Total: RM ${totalPrice.toFixed(2)}</h4>
            <button class="btn btn-success" onclick="checkout()">Checkout</button>
        </div>
    `;
}

// Show empty cart message
function renderEmptyCart() {
    const cartContainer = document.getElementById("cart-container");
    cartContainer.innerHTML = `
        <div class="alert alert-info">
            Your cart is empty. <a href="/menu.html">Browse menu</a>
        </div>
    `;
}

// Remove item from cart (example stub)
function removeFromCart(itemId) {
    console.log("Remove item:", itemId);
    // Call DELETE /api/cart/{itemId}
    // Then refresh the cart: fetchCart();
}

// Checkout (example stub)
function checkout() {
    alert("Proceeding to checkout...");
    // Redirect to checkout page or call API
}