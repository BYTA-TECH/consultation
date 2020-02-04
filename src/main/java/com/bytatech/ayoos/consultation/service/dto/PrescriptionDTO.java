package com.bytatech.ayoos.consultation.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bytatech.ayoos.consultation.domain.Prescription} entity.
 */
public class PrescriptionDTO implements Serializable {

    private Long id;

    private String prescriptionDMSURL;

    private String drug;

    private String dose;

    private String frequency;

    private String period;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrescriptionDMSURL() {
        return prescriptionDMSURL;
    }

    public void setPrescriptionDMSURL(String prescriptionDMSURL) {
        this.prescriptionDMSURL = prescriptionDMSURL;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrescriptionDTO prescriptionDTO = (PrescriptionDTO) o;
        if (prescriptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prescriptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrescriptionDTO{" +
            "id=" + getId() +
            ", prescriptionDMSURL='" + getPrescriptionDMSURL() + "'" +
            ", drug='" + getDrug() + "'" +
            ", dose='" + getDose() + "'" +
            ", frequency='" + getFrequency() + "'" +
            ", period='" + getPeriod() + "'" +
            "}";
    }
}
