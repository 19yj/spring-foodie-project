document.getElementById("register-form").addEventListener("submit", function(e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const name = document.getElementById("name").value;
    const phoneNumber = document.getElementById("phone_number").value;
    const email = document.getElementById("email").value;
    const role = "customer"; // or admin if have a dropdown

    fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            username,
            password,
            name,
            phoneNumber,
            email,
            role
        })
    })
        .then(async res => {
            const text = await res.text();
            console.log("Response text:", text);

            if (!res.ok) {
                throw new Error("Server responded with error");
            }

            alert("Registration successful! You can now log in.");
            window.location.href = "/login.html";
        })
        .catch(err => {
            console.error("Registration failed:", err);
            alert("Registration failed. Please try again.");
        });
});
