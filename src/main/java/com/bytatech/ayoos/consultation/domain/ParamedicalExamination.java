package com.bytatech.ayoos.consultation.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A ParamedicalExamination.
 */
@Entity
@Table(name = "paramedical_examination")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "paramedicalexamination")
public class ParamedicalExamination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    /**
     * @deprecated bp
     */
    @Column(name = "bp")
    private String bp;

    /**
     * @deprecated height
     */
    @Column(name = "height")
    private Double height;

    /**
     * @deprecated weight
     */
    @Column(name = "weight")
    private Double weight;

    /**
     * @deprecated temperature
     */
    @Column(name = "temperature")
    private Double temperature;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBp() {
        return bp;
    }

    public ParamedicalExamination bp(String bp) {
        this.bp = bp;
        return this;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public Double getHeight() {
        return height;
    }

    public ParamedicalExamination height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public ParamedicalExamination weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getTemperature() {
        return temperature;
    }

    public ParamedicalExamination temperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParamedicalExamination)) {
            return false;
        }
        return id != null && id.equals(((ParamedicalExamination) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ParamedicalExamination{" +
            "id=" + getId() +
            ", bp='" + getBp() + "'" +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", temperature=" + getTemperature() +
            "}";
    }
}
