package com.bytatech.ayoos.consultation.client.SMS;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "SMSResource", url= "${smsgateway.uk-url}")
public interface SMSResourceApiClientUK extends SMSResourceApiUK{

}
