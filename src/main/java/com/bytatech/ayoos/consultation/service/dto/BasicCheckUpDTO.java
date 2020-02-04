package com.bytatech.ayoos.consultation.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bytatech.ayoos.consultation.domain.BasicCheckUp} entity.
 */
public class BasicCheckUpDTO implements Serializable {

    private Long id;

    private Double height;

    private Double weight;

    private Double temperature;

    private String bp;

    private String checkUpStatus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    public String getCheckUpStatus() {
        return checkUpStatus;
    }

    public void setCheckUpStatus(String checkUpStatus) {
        this.checkUpStatus = checkUpStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasicCheckUpDTO basicCheckUpDTO = (BasicCheckUpDTO) o;
        if (basicCheckUpDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), basicCheckUpDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BasicCheckUpDTO{" +
            "id=" + getId() +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", temperature=" + getTemperature() +
            ", bp='" + getBp() + "'" +
            ", checkUpStatus='" + getCheckUpStatus() + "'" +
            "}";
    }
}
