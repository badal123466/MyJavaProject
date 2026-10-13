const API_URL = "/api/patients";


// ========================================
// LOAD PATIENTS WHEN PAGE OPENS
// ========================================

document.addEventListener("DOMContentLoaded", function () {

    loadPatients();

    document
        .getElementById("patientForm")
        .addEventListener("submit", savePatient);

});


// ========================================
// GET ALL PATIENTS
// ========================================

async function loadPatients() {

    const table = document.getElementById("patientTable");

    try {

        console.log("Calling API:", API_URL);

        const response = await fetch(API_URL);

        console.log("HTTP Status:", response.status);

        if (!response.ok) {

            throw new Error(
                "HTTP Error: " + response.status
            );
        }

        const patients = await response.json();

        console.log("Patients received:", patients);

        table.innerHTML = "";

        if (!patients || patients.length === 0) {

            table.innerHTML = `
                <tr>
                    <td colspan="10"
                        style="text-align:center;">
                        No patients found in database
                    </td>
                </tr>
            `;

            return;
        }


        // ========================================
        // DISPLAY PATIENTS
        // ========================================

        patients.forEach(function (patient) {

            const row = document.createElement("tr");

            row.innerHTML = `

                <td>${patient.id ?? ""}</td>

                <td>${patient.patientCode ?? ""}</td>

                <td>${patient.name ?? ""}</td>

                <td>${patient.age ?? ""}</td>

                <td>${patient.gender ?? ""}</td>

                <td>${patient.phone ?? ""}</td>

                <td>${patient.email ?? ""}</td>

                <td>${patient.bloodGroup ?? ""}</td>

                <td>${patient.address ?? ""}</td>

                <td>

                    <button
                        class="delete-btn"
                        onclick="deletePatient(${patient.id})">

                        Delete

                    </button>

                </td>
            `;

            table.appendChild(row);

        });

    }
    catch (error) {

        console.error(
            "ERROR loading patients:",
            error
        );

        table.innerHTML = `
            <tr>
                <td colspan="10"
                    style="
                        text-align:center;
                        color:red;
                        padding:20px;
                    ">

                    Unable to load patients.
                    Check browser console.

                </td>
            </tr>
        `;
    }
}


// ========================================
// SAVE PATIENT
// ========================================

async function savePatient(event) {

    event.preventDefault();

    const patient = {

        name:
            document.getElementById("name").value,

        age:
            Number(
                document.getElementById("age").value
            ),

        gender:
            document.getElementById("gender").value,

        phone:
            document.getElementById("phone").value,

        email:
            document.getElementById("email").value,

        bloodGroup:
            document.getElementById("bloodGroup").value,

        address:
            document.getElementById("address").value
    };


    console.log("Sending patient:", patient);


    try {

        const response = await fetch(
            API_URL,
            {
                method: "POST",

                headers: {
                    "Content-Type":
                        "application/json"
                },

                body:
                    JSON.stringify(patient)
            }
        );


        if (!response.ok) {

            throw new Error(
                "Save failed: " + response.status
            );
        }


        const savedPatient =
            await response.json();


        console.log(
            "Patient saved:",
            savedPatient
        );


        alert(
            "Patient registered successfully!"
        );


        document
            .getElementById("patientForm")
            .reset();


        // Refresh patient list
        loadPatients();

    }
    catch (error) {

        console.error(
            "ERROR saving patient:",
            error
        );

        alert(
            "Unable to save patient"
        );
    }
}


// ========================================
// DELETE PATIENT
// ========================================

async function deletePatient(id) {

    const confirmDelete =
        confirm(
            "Are you sure you want to delete this patient?"
        );


    if (!confirmDelete) {

        return;
    }


    try {

        const response = await fetch(
            `${API_URL}/${id}`,
            {
                method: "DELETE"
            }
        );


        if (!response.ok) {

            throw new Error(
                "Delete failed"
            );
        }


        alert(
            "Patient deleted successfully!"
        );


        loadPatients();

    }
    catch (error) {

        console.error(
            "DELETE ERROR:",
            error
        );

        alert(
            "Unable to delete patient"
        );
    }
}