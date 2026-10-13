package com.hospital.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNumber;

    private double consultationCharge;

    private double medicineCharge;

    private double labCharge;

    private double roomCharge;

    private double totalAmount;

    private double paidAmount;

    private LocalDate invoiceDate;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Invoice() {
    }

    @PrePersist
    public void createInvoice() {

        if (invoiceNumber == null) {
            invoiceNumber =
                    "INV-" + System.currentTimeMillis();
        }

        if (invoiceDate == null) {
            invoiceDate = LocalDate.now();
        }

        calculateTotal();

        if (status == null) {
            status = InvoiceStatus.UNPAID;
        }
    }

    public void calculateTotal() {

        totalAmount =
                consultationCharge
                + medicineCharge
                + labCharge
                + roomCharge;
    }

    public Long getId() {
        return id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public double getConsultationCharge() {
        return consultationCharge;
    }

    public void setConsultationCharge(double charge) {
        this.consultationCharge = charge;
    }

    public double getMedicineCharge() {
        return medicineCharge;
    }

    public void setMedicineCharge(double charge) {
        this.medicineCharge = charge;
    }

    public double getLabCharge() {
        return labCharge;
    }

    public void setLabCharge(double charge) {
        this.labCharge = charge;
    }

    public double getRoomCharge() {
        return roomCharge;
    }

    public void setRoomCharge(double charge) {
        this.roomCharge = charge;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {

        this.paidAmount = paidAmount;

        if (paidAmount >= totalAmount) {
            status = InvoiceStatus.PAID;
        } else if (paidAmount > 0) {
            status = InvoiceStatus.PARTIAL;
        } else {
            status = InvoiceStatus.UNPAID;
        }
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}