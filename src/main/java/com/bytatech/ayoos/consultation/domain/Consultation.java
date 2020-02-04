package com.bytatech.ayoos.consultation.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Consultation.
 */
@Entity
@Table(name = "consultation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "consultation")
public class Consultation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "tracking_id")
    private String trackingId;

    @Column(name = "patient_idp_code")
    private String patientIdpCode;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private Prescription prescription;

    @OneToOne
    @JoinColumn(unique = true)
    private Diagnosis diagnosis;

    @OneToOne
    @JoinColumn(unique = true)
    private BasicCheckUp basicCheckUp;

    @OneToMany(mappedBy = "consultation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Symptom> symptoms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public Consultation trackingId(String trackingId) {
        this.trackingId = trackingId;
        return this;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getPatientIdpCode() {
        return patientIdpCode;
    }

    public Consultation patientIdpCode(String patientIdpCode) {
        this.patientIdpCode = patientIdpCode;
        return this;
    }

    public void setPatientIdpCode(String patientIdpCode) {
        this.patientIdpCode = patientIdpCode;
    }

    public LocalDate getDate() {
        return date;
    }

    public Consultation date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Consultation phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public Consultation prescription(Prescription prescription) {
        this.prescription = prescription;
        return this;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public Consultation diagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
        return this;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    public BasicCheckUp getBasicCheckUp() {
        return basicCheckUp;
    }

    public Consultation basicCheckUp(BasicCheckUp basicCheckUp) {
        this.basicCheckUp = basicCheckUp;
        return this;
    }

    public void setBasicCheckUp(BasicCheckUp basicCheckUp) {
        this.basicCheckUp = basicCheckUp;
    }

    public Set<Symptom> getSymptoms() {
        return symptoms;
    }

    public Consultation symptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
        return this;
    }

    public Consultation addSymptom(Symptom symptom) {
        this.symptoms.add(symptom);
        symptom.setConsultation(this);
        return this;
    }

    public Consultation removeSymptom(Symptom symptom) {
        this.symptoms.remove(symptom);
        symptom.setConsultation(null);
        return this;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consultation)) {
            return false;
        }
        return id != null && id.equals(((Consultation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Consultation{" +
            "id=" + getId() +
            ", trackingId='" + getTrackingId() + "'" +
            ", patientIdpCode='" + getPatientIdpCode() + "'" +
            ", date='" + getDate() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
