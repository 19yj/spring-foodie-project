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

function logout() {
    localStorage.removeItem("token");
    window.location.href = "login.html";
}

document.addEventListener("DOMContentLoaded", renderMenu);