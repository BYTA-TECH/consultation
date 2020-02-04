package com.bytatech.ayoos.consultation.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bytatech.ayoos.consultation.domain.Consultation} entity.
 */
public class ConsultationDTO implements Serializable {

    private Long id;

    private String trackingId;

    private String patientIdpCode;

    private LocalDate date;

    private String phoneNumber;


    private Long prescriptionId;

    private Long diagnosisId;

    private Long basicCheckUpId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getPatientIdpCode() {
        return patientIdpCode;
    }

    public void setPatientIdpCode(String patientIdpCode) {
        this.patientIdpCode = patientIdpCode;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Long getBasicCheckUpId() {
        return basicCheckUpId;
    }

    public void setBasicCheckUpId(Long basicCheckUpId) {
        this.basicCheckUpId = basicCheckUpId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsultationDTO consultationDTO = (ConsultationDTO) o;
        if (consultationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultationDTO{" +
            "id=" + getId() +
            ", trackingId='" + getTrackingId() + "'" +
            ", patientIdpCode='" + getPatientIdpCode() + "'" +
            ", date='" + getDate() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", prescriptionId=" + getPrescriptionId() +
            ", diagnosisId=" + getDiagnosisId() +
            ", basicCheckUpId=" + getBasicCheckUpId() +
            "}";
    }
}
