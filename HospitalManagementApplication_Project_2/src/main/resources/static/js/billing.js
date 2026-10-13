async function loadInvoices() {

    try {

        const response =
            await fetch("/api/invoices");

        if (!response.ok) {
            throw new Error(
                "Invoice API not available"
            );
        }

        const invoices =
            await response.json();

        const table =
            document.getElementById(
                "invoiceTable"
            );

        table.innerHTML = "";

        invoices.forEach(invoice => {

            table.innerHTML += `
                <tr>
                    <td>${invoice.id}</td>
                    <td>${invoice.invoiceNumber}</td>
                    <td>${invoice.patient?.name || ""}</td>
                    <td>${invoice.consultationCharge}</td>
                    <td>${invoice.medicineCharge}</td>
                    <td>${invoice.labCharge}</td>
                    <td>${invoice.roomCharge}</td>
                    <td>${invoice.totalAmount}</td>
                    <td>${invoice.paidAmount}</td>
                    <td>${invoice.status}</td>
                    <td>${invoice.invoiceDate}</td>
                </tr>
            `;
        });

    } catch (error) {

        console.error(error);

        alert(
            "Unable to load invoices"
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


loadInvoices();