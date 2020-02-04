package com.bytatech.ayoos.consultation.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Diagnosis.
 */
@Entity
@Table(name = "diagnosis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "diagnosis")
public class Diagnosis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "diagnosis_ref")
    private String diagnosisRef;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiagnosisRef() {
        return diagnosisRef;
    }

    public Diagnosis diagnosisRef(String diagnosisRef) {
        this.diagnosisRef = diagnosisRef;
        return this;
    }

    public void setDiagnosisRef(String diagnosisRef) {
        this.diagnosisRef = diagnosisRef;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Diagnosis)) {
            return false;
        }
        return id != null && id.equals(((Diagnosis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
            "id=" + getId() +
            ", diagnosisRef='" + getDiagnosisRef() + "'" +
            "}";
    }
}
