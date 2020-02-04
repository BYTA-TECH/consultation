package com.bytatech.ayoos.consultation.client.activiti.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.bytatech.ayoos.consultation.client.activiti.ClientConfiguration;

@FeignClient(name="${activiti.name:activiti}", url="${activiti.url:http://localhost:8080/activiti-rest/service}", configuration = ClientConfiguration.class)
public interface RuntimeApiClient extends RuntimeApi {
}