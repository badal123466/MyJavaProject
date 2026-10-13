async function loadDepartments() {

    try {

        const response =
            await fetch("/api/departments");

        if (!response.ok) {

            throw new Error(
                "HTTP Error: " + response.status
            );
        }

        const departments =
            await response.json();

        const table =
            document.getElementById(
                "departmentTable"
            );

        table.innerHTML = "";

        departments.forEach(
            department => {

                table.innerHTML += `

                    <tr>

                        <td>${department.id}</td>

                        <td>${department.name}</td>

                        <td>${department.description}</td>

                        <td>${department.location}</td>

                        <td>

                            <button
                                class="delete-btn"
                                onclick="deleteDepartment(${department.id})">

                                Delete

                            </button>

                        </td>

                    </tr>

                `;
            }
        );

    } catch (error) {

        console.error(
            "Department loading error:",
            error
        );
    }
}



document
    .getElementById("departmentForm")
    .addEventListener(
        "submit",
        async function(event) {

            event.preventDefault();

            const department = {

                name:
                    document
                        .getElementById("name")
                        .value
                        .trim(),

                description:
                    document
                        .getElementById("description")
                        .value
                        .trim(),

                location:
                    document
                        .getElementById("location")
                        .value
                        .trim()
            };


            try {

                const response =
                    await fetch(
                        "/api/departments",
                        {
                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(
                                    department
                                )
                        }
                    );


                if (!response.ok) {

                    const error =
                        await response.text();

                    throw new Error(error);
                }


                alert(
                    "Department added successfully!"
                );


                document
                    .getElementById(
                        "departmentForm"
                    )
                    .reset();


                loadDepartments();


            } catch(error) {

                alert(
                    "Department not added: "
                    + error.message
                );
            }

        }
    );



async function deleteDepartment(id) {

    try {

        const response =
            await fetch(
                `/api/departments/${id}`,
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
            "Department deleted successfully!"
        );


        loadDepartments();


    } catch(error) {

        alert(error.message);
    }
}



function logout() {

    sessionStorage.removeItem(
        "loggedInUser"
    );

    window.location.href =
        "login.html";
}



loadDepartments();