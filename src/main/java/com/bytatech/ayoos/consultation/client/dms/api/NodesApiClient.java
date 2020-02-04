package com.bytatech.ayoos.consultation.client.dms.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.bytatech.ayoos.consultation.client.dms.ClientConfiguration;

@FeignClient(name="${dms.name:dms}", url="${dms.url}", configuration = ClientConfiguration.class)
public interface NodesApiClient extends NodesApi {
}
