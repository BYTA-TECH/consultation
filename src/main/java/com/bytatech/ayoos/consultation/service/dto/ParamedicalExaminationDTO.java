package com.bytatech.ayoos.consultation.service.dto;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bytatech.ayoos.consultation.domain.ParamedicalExamination} entity.
 */
public class ParamedicalExaminationDTO implements Serializable {

    private Long id;

    /**
     * @deprecated bp
     */
    @ApiModelProperty(value = "@deprecated bp")
    private String bp;

    /**
     * @deprecated height
     */
    @ApiModelProperty(value = "@deprecated height")
    private Double height;

    /**
     * @deprecated weight
     */
    @ApiModelProperty(value = "@deprecated weight")
    private Double weight;

    /**
     * @deprecated temperature
     */
    @ApiModelProperty(value = "@deprecated temperature")
    private Double temperature;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParamedicalExaminationDTO paramedicalExaminationDTO = (ParamedicalExaminationDTO) o;
        if (paramedicalExaminationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paramedicalExaminationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParamedicalExaminationDTO{" +
            "id=" + getId() +
            ", bp='" + getBp() + "'" +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", temperature=" + getTemperature() +
            "}";
    }
}
