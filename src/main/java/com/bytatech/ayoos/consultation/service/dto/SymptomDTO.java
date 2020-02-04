package com.bytatech.ayoos.consultation.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bytatech.ayoos.consultation.domain.Symptom} entity.
 */
public class SymptomDTO implements Serializable {

    private Long id;

    private String symptomRef;


    private Long consultationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymptomRef() {
        return symptomRef;
    }

    public void setSymptomRef(String symptomRef) {
        this.symptomRef = symptomRef;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SymptomDTO symptomDTO = (SymptomDTO) o;
        if (symptomDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), symptomDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SymptomDTO{" +
            "id=" + getId() +
            ", symptomRef='" + getSymptomRef() + "'" +
            ", consultationId=" + getConsultationId() +
            "}";
    }
}
