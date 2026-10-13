async function loadAppointments() {

    try {

        const response =
            await fetch("/api/appointments");

        const appointments =
            await response.json();

        const table =
            document.getElementById(
                "appointmentTable"
            );

        table.innerHTML = "";

        appointments.forEach(appointment => {

            table.innerHTML += `
                <tr>
                    <td>${appointment.id}</td>
                    <td>${appointment.appointmentCode}</td>
                    <td>${appointment.patient?.name || ""}</td>
                    <td>${appointment.doctor?.name || ""}</td>
                    <td>${appointment.appointmentDate || ""}</td>
                    <td>${appointment.appointmentTime || ""}</td>
                    <td>${appointment.reason || ""}</td>
                    <td>${appointment.status || ""}</td>
                </tr>
            `;
        });

    } catch (error) {

        console.error(error);

        alert(
            "Unable to load appointments"
        );
    }
}


async function loadPatientsForAppointment() {

    const response =
        await fetch("/api/patients");

    const patients =
        await response.json();

    const select =
        document.getElementById(
            "patientId"
        );

    select.innerHTML =
        `<option value="">Select Patient</option>`;

    patients.forEach(patient => {

        select.innerHTML += `
            <option value="${patient.id}">
                ${patient.name}
            </option>
        `;
    });
}


async function loadDoctorsForAppointment() {

    const response =
        await fetch("/api/doctors");

    const doctors =
        await response.json();

    const select =
        document.getElementById(
            "doctorId"
        );

    select.innerHTML =
        `<option value="">Select Doctor</option>`;

    doctors.forEach(doctor => {

        select.innerHTML += `
            <option value="${doctor.id}">
                ${doctor.name}
            </option>
        `;
    });
}


document
    .getElementById("appointmentForm")
    .addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();

            const patientId =
                document.getElementById(
                    "patientId"
                ).value;

            const doctorId =
                document.getElementById(
                    "doctorId"
                ).value;

            const appointment = {

                appointmentDate:
                    document.getElementById(
                        "appointmentDate"
                    ).value,

                appointmentTime:
                    document.getElementById(
                        "appointmentTime"
                    ).value,

                reason:
                    document.getElementById(
                        "reason"
                    ).value
            };

            try {

                const response =
                    await fetch(
                        `/api/appointments?patientId=${patientId}&doctorId=${doctorId}`,
                        {
                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(
                                    appointment
                                )
                        }
                    );

                if (!response.ok) {
                    throw new Error(
                        "Unable to create appointment"
                    );
                }

                alert(
                    "Appointment created successfully"
                );

                document
                    .getElementById(
                        "appointmentForm"
                    )
                    .reset();

                loadAppointments();

            } catch (error) {

                alert(error.message);
            }
        }
    );


function logout() {

    sessionStorage.removeItem(
        "loggedInUser"
    );

    window.location.href =
        "login.html";
}


loadAppointments();
loadPatientsForAppointment();
loadDoctorsForAppointment();