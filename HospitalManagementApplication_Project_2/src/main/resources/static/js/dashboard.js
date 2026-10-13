async function loadDashboard() {

    try {

        const patients =
            await fetch("/api/patients")
                .then(response => response.json());

        const doctors =
            await fetch("/api/doctors")
                .then(response => response.json());

        const departments =
            await fetch("/api/departments")
                .then(response => response.json());

        const appointments =
            await fetch("/api/appointments/today")
                .then(response => response.json());

        document.getElementById(
            "patientCount"
        ).innerText = patients.length;

        document.getElementById(
            "doctorCount"
        ).innerText = doctors.length;

        document.getElementById(
            "departmentCount"
        ).innerText = departments.length;

        document.getElementById(
            "appointmentCount"
        ).innerText = appointments.length;

    } catch (error) {

        console.error(
            "Dashboard error:",
            error
        );
    }
}

function logout() {

    sessionStorage.removeItem(
        "loggedInUser"
    );

    window.location.href =
        "login.html";
}

loadDashboard();