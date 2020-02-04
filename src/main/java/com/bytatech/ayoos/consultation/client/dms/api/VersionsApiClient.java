package com.bytatech.ayoos.consultation.client.dms.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.bytatech.ayoos.consultation.client.dms.ClientConfiguration;

@FeignClient(name="${dms.name:dms}", url="${dms.url:http://localhost/alfresco/api/-default-/public/alfresco/versions/1}", configuration = ClientConfiguration.class)
public interface VersionsApiClient extends VersionsApi {
}