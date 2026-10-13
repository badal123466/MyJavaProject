document
.getElementById("loginForm")
.addEventListener("submit", async function(event) {

    event.preventDefault();

    const username =
        document.getElementById("username").value;

    const password =
        document.getElementById("password").value;

    const errorElement =
        document.getElementById("errorMessage");

    errorElement.innerText = "";

    try {

        const response =
            await fetch("/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(
                data.error || "Invalid username or password"
            );
        }

        sessionStorage.setItem(
            "loggedInUser",
            JSON.stringify(data)
        );

        window.location.href = "dashboard.html";

    } catch (error) {

        errorElement.innerText =
            error.message;
    }
});