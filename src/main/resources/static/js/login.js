// document.getElementById("login-form").addEventListener("submit", function(e) {
//     e.preventDefault();
//
//     const username = document.getElementById("username").value;
//     const password = document.getElementById("password").value;
//
//     fetch("http://localhost:8080/login", {
//         method: "POST",
//         headers: {"Content-Type": "application/json"},
//         body: JSON.stringify({username, password})
//     })
//         .then(res => res.text())
//         .then(token => {
//         localStorage.setItem("token", token);
//         alert("Login successful!");
//         window.location.href = "/menu.html";
//     })
//         .catch(err => {
//         console.error("Login failed:", err);
//         alert("Login failed!");
//     });
//
//         if (username === 'admin' && password === 'password123') {
//         // Simulate successful login (JWT would be returned here)
//         alert("Login successful!");
//         window.location.href = '/menu.html';  // Redirect to the menu or another page
//     } else {
//         // Show error message
//         document.getElementById('errorMessage').style.display = 'block';
//     }
// });

document.getElementById("login-form").addEventListener("submit", function(e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ username, password })
    })
        .then(res => {
            if (!res.ok) {
                throw new Error("Invalid credentials");
            }
            return res.text(); // Assuming backend returns token as plain text
        })
        .then(token => {
            localStorage.setItem("token", token);
            alert("Login successful!");
            window.location.href = "/menu.html";
        })
        .catch(err => {
            console.error("Login failed:", err);
            document.getElementById('errorMessage').style.display = 'block';
        });
});

