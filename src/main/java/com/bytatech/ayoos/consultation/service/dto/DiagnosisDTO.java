package com.bytatech.ayoos.consultation.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bytatech.ayoos.consultation.domain.Diagnosis} entity.
 */
public class DiagnosisDTO implements Serializable {

    private Long id;

    private String diagnosisRef;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiagnosisRef() {
        return diagnosisRef;
    }

    public void setDiagnosisRef(String diagnosisRef) {
        this.diagnosisRef = diagnosisRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DiagnosisDTO diagnosisDTO = (DiagnosisDTO) o;
        if (diagnosisDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), diagnosisDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DiagnosisDTO{" +
            "id=" + getId() +
            ", diagnosisRef='" + getDiagnosisRef() + "'" +
            "}";
    }
}
