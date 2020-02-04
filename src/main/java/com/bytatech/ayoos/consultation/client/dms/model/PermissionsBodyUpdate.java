package com.bytatech.ayoos.consultation.client.dms.model;

import java.util.Objects;
import com.bytatech.ayoos.consultation.client.dms.model.PermissionElement;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PermissionsBodyUpdate
 */
@Validated
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2020-01-24T12:34:56.196+05:30[Asia/Colombo]")

public class PermissionsBodyUpdate   {
  @JsonProperty("isInheritanceEnabled")
  private Boolean isInheritanceEnabled = null;

  @JsonProperty("locallySet")
  @Valid
  private List<PermissionElement> locallySet = null;

  public PermissionsBodyUpdate isInheritanceEnabled(Boolean isInheritanceEnabled) {
    this.isInheritanceEnabled = isInheritanceEnabled;
    return this;
  }

  /**
   * Get isInheritanceEnabled
   * @return isInheritanceEnabled
  **/
  @ApiModelProperty(value = "")


  public Boolean isIsInheritanceEnabled() {
    return isInheritanceEnabled;
  }

  public void setIsInheritanceEnabled(Boolean isInheritanceEnabled) {
    this.isInheritanceEnabled = isInheritanceEnabled;
  }

  public PermissionsBodyUpdate locallySet(List<PermissionElement> locallySet) {
    this.locallySet = locallySet;
    return this;
  }

  public PermissionsBodyUpdate addLocallySetItem(PermissionElement locallySetItem) {
    if (this.locallySet == null) {
      this.locallySet = new ArrayList<PermissionElement>();
    }
    this.locallySet.add(locallySetItem);
    return this;
  }

  /**
   * Get locallySet
   * @return locallySet
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PermissionElement> getLocallySet() {
    return locallySet;
  }

  public void setLocallySet(List<PermissionElement> locallySet) {
    this.locallySet = locallySet;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PermissionsBodyUpdate permissionsBodyUpdate = (PermissionsBodyUpdate) o;
    return Objects.equals(this.isInheritanceEnabled, permissionsBodyUpdate.isInheritanceEnabled) &&
        Objects.equals(this.locallySet, permissionsBodyUpdate.locallySet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isInheritanceEnabled, locallySet);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PermissionsBodyUpdate {\n");
    
    sb.append("    isInheritanceEnabled: ").append(toIndentedString(isInheritanceEnabled)).append("\n");
    sb.append("    locallySet: ").append(toIndentedString(locallySet)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

