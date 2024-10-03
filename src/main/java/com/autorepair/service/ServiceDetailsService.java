package com.autorepair.service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.autorepair.request.ServiceDetailsRequest;
import com.autorepair.response.ServiceDetailsResponse;

@Component
public interface ServiceDetailsService {

  List<ServiceDetailsResponse> getAllServiceDetails();

  ServiceDetailsResponse getServiceDetailsById(String serviceId);

  ServiceDetailsResponse saveServiceDetails(ServiceDetailsRequest serDetailsRequest);

  ServiceDetailsResponse updateServiceDetails(String serviceId, ServiceDetailsRequest serDetailsRequest);

  ServiceDetailsResponse deleteServiceDetails(String serviceId);
}
