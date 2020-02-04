package com.bytatech.ayoos.consultation.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A BasicCheckUp.
 */
@Entity
@Table(name = "basic_check_up")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "basiccheckup")
public class BasicCheckUp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "bp")
    private String bp;

    @Column(name = "check_up_status")
    private String checkUpStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getHeight() {
        return height;
    }

    public BasicCheckUp height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public BasicCheckUp weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getTemperature() {
        return temperature;
    }

    public BasicCheckUp temperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getBp() {
        return bp;
    }

    public BasicCheckUp bp(String bp) {
        this.bp = bp;
        return this;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getCheckUpStatus() {
        return checkUpStatus;
    }

    public BasicCheckUp checkUpStatus(String checkUpStatus) {
        this.checkUpStatus = checkUpStatus;
        return this;
    }

    public void setCheckUpStatus(String checkUpStatus) {
        this.checkUpStatus = checkUpStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BasicCheckUp)) {
            return false;
        }
        return id != null && id.equals(((BasicCheckUp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BasicCheckUp{" +
            "id=" + getId() +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", temperature=" + getTemperature() +
            ", bp='" + getBp() + "'" +
            ", checkUpStatus='" + getCheckUpStatus() + "'" +
            "}";
    }
}
