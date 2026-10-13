// ======================================
// PAGE LOAD
// ======================================

document.addEventListener(
    "DOMContentLoaded",
    function () {

        loadDepartments();

        loadDoctors();

    }
);



// ======================================
// LOAD DEPARTMENTS
// ======================================

async function loadDepartments() {

    const select =
        document.getElementById(
            "departmentId"
        );


    try {

        // CORRECT URL
        const response =
            await fetch(
                "/api/departments"
            );


        if (!response.ok) {

            throw new Error(
                "Department API error: "
                + response.status
            );
        }


        const departments =
            await response.json();


        console.log(
            "Departments:",
            departments
        );


        select.innerHTML =
            '<option value="">Select Department</option>';


        if (departments.length === 0) {

            select.innerHTML =
                '<option value="">No departments found</option>';

            return;
        }


        departments.forEach(
            function (department) {

                const option =
                    document.createElement(
                        "option"
                    );


                option.value =
                    department.id;


                option.textContent =
                    department.name;


                select.appendChild(
                    option
                );
            }
        );


    } catch (error) {

        console.error(
            "Department loading error:",
            error
        );


        select.innerHTML =
            '<option value="">Failed to load departments</option>';
    }
}



// ======================================
// ADD DOCTOR
// ======================================

document
    .getElementById("doctorForm")
    .addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            const departmentId =
                document
                    .getElementById(
                        "departmentId"
                    )
                    .value;


            if (!departmentId) {

                alert(
                    "Please select department"
                );

                return;
            }


            const doctor = {

                name:
                    document
                        .getElementById(
                            "name"
                        )
                        .value
                        .trim(),


                specialization:
                    document
                        .getElementById(
                            "specialization"
                        )
                        .value
                        .trim(),


                phone:
                    document
                        .getElementById(
                            "phone"
                        )
                        .value
                        .trim(),


                email:
                    document
                        .getElementById(
                            "email"
                        )
                        .value
                        .trim(),


                qualification:
                    document
                        .getElementById(
                            "qualification"
                        )
                        .value
                        .trim(),


                consultationFee:
                    Number(
                        document
                            .getElementById(
                                "consultationFee"
                            )
                            .value
                    ),


                departmentId:
                    Number(
                        departmentId
                    )
            };


            console.log(
                "Sending doctor:",
                doctor
            );


            try {

                const response =
                    await fetch(
                        "/api/doctors",
                        {

                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(
                                    doctor
                                )
                        }
                    );


                const result =
                    await response.text();


                console.log(
                    "Doctor response:",
                    result
                );


                if (!response.ok) {

                    throw new Error(
                        result
                    );
                }


                alert(
                    "Doctor added successfully!"
                );


                document
                    .getElementById(
                        "doctorForm"
                    )
                    .reset();


                loadDoctors();


            } catch (error) {

                console.error(
                    "Doctor error:",
                    error
                );


                alert(
                    "Doctor not added: "
                    + error.message
                );
            }

        }
    );



// ======================================
// LOAD DOCTORS
// ======================================

async function loadDoctors() {

    try {

        const response =
            await fetch(
                "/api/doctors"
            );


        if (!response.ok) {

            throw new Error(
                "Doctor API error"
            );
        }


        const doctors =
            await response.json();


        const table =
            document.getElementById(
                "doctorTable"
            );


        table.innerHTML = "";


        doctors.forEach(
            function (doctor) {

                const departmentName =
                    doctor.department
                        ? doctor.department.name
                        : "Not assigned";


                table.innerHTML += `

                    <tr>

                        <td>
                            ${doctor.id}
                        </td>

                        <td>
                            ${doctor.doctorCode || ""}
                        </td>

                        <td>
                            ${doctor.name || ""}
                        </td>

                        <td>
                            ${doctor.specialization || ""}
                        </td>

                        <td>
                            ${doctor.phone || ""}
                        </td>

                        <td>
                            ${doctor.email || ""}
                        </td>

                        <td>
                            ${doctor.qualification || ""}
                        </td>

                        <td>
                            ₹${doctor.consultationFee || 0}
                        </td>

                        <td>
                            ${departmentName}
                        </td>

                        <td>

                            <button
                                class="delete-btn"
                                onclick="deleteDoctor(${doctor.id})">

                                Delete

                            </button>

                        </td>

                    </tr>

                `;
            }
        );


    } catch (error) {

        console.error(
            "Doctor loading error:",
            error
        );
    }
}



// ======================================
// DELETE DOCTOR
// ======================================

async function deleteDoctor(id) {

    if (
        !confirm(
            "Are you sure you want to delete this doctor?"
        )
    ) {

        return;
    }


    try {

        const response =
            await fetch(
                `/api/doctors/${id}`,
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
            "Doctor deleted successfully!"
        );


        loadDoctors();


    } catch (error) {

        console.error(error);


        alert(
            "Doctor delete failed"
        );
    }
}



// ======================================
// LOGOUT
// ======================================

function logout() {

    sessionStorage.removeItem(
        "loggedInUser"
    );

    window.location.href =
        "login.html";
}