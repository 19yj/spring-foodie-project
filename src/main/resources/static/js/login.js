document.getElementById("login-form").addEventListener("submit", function (e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
    })
        .then(res => {
            if (!res.ok) {
                throw new Error("Invalid credentials");
            }
            return res.text(); // Assuming backend returns "Bearer <token>"
        })
        .then(token => {
            console.log("Raw token from server:", token);

            // Strip "Bearer " if it's included
            if (token.startsWith("Bearer ")) {
                token = token.split(" ")[1]; // Get the JWT part only
            }

            localStorage.setItem("token", token);

            alert("Login successful!");
            window.location.href = "/menu.html";
        })
        .catch(err => {
            console.error("Login failed:", err);
            document.getElementById('errorMessage').style.display = 'block';
        });
});

