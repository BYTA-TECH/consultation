package com.bytatech.ayoos.consultation.service.impl;

import java.util.ArrayList; 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytatech.ayoos.consultation.client.SMS.SMSResourceApiIN;
import com.bytatech.ayoos.consultation.client.SMS.SMSResourceApiUK;
import com.bytatech.ayoos.consultation.client.activiti.api.FormsApi;
import com.bytatech.ayoos.consultation.client.activiti.api.ProcessInstancesApi;
import com.bytatech.ayoos.consultation.client.activiti.model.ProcessInstanceCreateRequest;
import com.bytatech.ayoos.consultation.client.activiti.model.ProcessInstanceResponse;
import com.bytatech.ayoos.consultation.client.activiti.model.RestFormProperty;
import com.bytatech.ayoos.consultation.client.activiti.model.RestVariable;
import com.bytatech.ayoos.consultation.client.activiti.model.SubmitFormRequest;
import com.bytatech.ayoos.consultation.domain.BasicCheckUp;
import com.bytatech.ayoos.consultation.domain.Consultation;
import com.bytatech.ayoos.consultation.domain.OTPChallenge;
import com.bytatech.ayoos.consultation.domain.OTPResponse;
import com.bytatech.ayoos.consultation.domain.Prescription;
import com.bytatech.ayoos.consultation.repository.BasicCheckUpRepository;
import com.bytatech.ayoos.consultation.resource.assembler.NextTaskAssembler;
import com.bytatech.ayoos.consultation.resource.assembler.NextTaskResource;
import com.bytatech.ayoos.consultation.service.ConsultationCommandService;
 
 
@Service
@Transactional
public class ConsultationCommandServiceImpl implements ConsultationCommandService {
	private final Logger log = LoggerFactory.getLogger(ConsultationCommandServiceImpl.class);
	@Autowired
	private SMSResourceApiUK smsResourceApiUK;
	@Autowired
	private SMSResourceApiIN smsResourceApiIN;
	
	Long number=918848429930L;
	@Value("${smsgateway.credentials.in-apiKey}")
	private String apiKey_IN;

	@Value("${smsgateway.in-sender}")
	private String SMSsender_IN;

	@Value("${smsgateway.credentials.uk-apiKey}")
	private String apiKey_UK;

	@Value("${smsgateway.uk-sender}")
	private String SMSsender_UK;
    
	@Autowired
    private ProcessInstancesApi processInstanceApi;
    @Autowired
	private NextTaskAssembler resourceAssembler;
    @Autowired
    private FormsApi formsApi;
    @Autowired
    private  BasicCheckUpRepository basicCheckUpRepository;
    @Override
    public NextTaskResource initiate() {
    	log .info("into ====================initiate()");
		ProcessInstanceCreateRequest processInstanceCreateRequest=new ProcessInstanceCreateRequest();
   		 List<RestVariable> variables=new ArrayList<RestVariable>(); 
   		processInstanceCreateRequest.setProcessDefinitionId("ConsultationNew:5:42615");
   		log.info("*****************************************************"+processInstanceCreateRequest.getProcessDefinitionId());
   		RestVariable driverRestVariable=new RestVariable();
   		driverRestVariable.setName("doctor");
   		driverRestVariable.setType("string");
   		driverRestVariable.setValue("doctor");
   		RestVariable driverRestVariable2=new RestVariable();
   		driverRestVariable2.setName("patient");
   		driverRestVariable2.setType("string");
   		driverRestVariable2.setValue("patient");
   		variables.add(driverRestVariable);
   		variables.add(driverRestVariable2);
   		processInstanceCreateRequest.setVariables(variables);
   		ResponseEntity<ProcessInstanceResponse> processInstanceResponse = processInstanceApi.createProcessInstance(processInstanceCreateRequest);
		String processInstanceId = processInstanceResponse.getBody().getId();
		String processDefinitionId = processInstanceCreateRequest.getProcessDefinitionId();
		log.info("++++++++++++++++processDefinitionId++++++++++++++++++"+ processDefinitionId);
		log.info("++++++++++++++++ProcessInstanceId is+++++++++++++ " + processInstanceId);
		
   		processInstanceApi.createProcessInstance(processInstanceCreateRequest);
   		NextTaskResource nextTaskResource = resourceAssembler.toResource(processInstanceId);
		 
		return nextTaskResource;
	}
    @Override
    public NextTaskResource basicCheckUpTask(String processId, BasicCheckUp basicCheckUp) {
 
        log .info("into ====================basicCheckUpTask()");
   		List<RestFormProperty>formProperties=new ArrayList<RestFormProperty>();
   		SubmitFormRequest submitFormRequest = new SubmitFormRequest();
   		submitFormRequest.setAction("completed");
   		NextTaskResource nextTaskResource = resourceAssembler.toResource(processId);
   		submitFormRequest.setTaskId(nextTaskResource.getNextTaskId());
   		 
   		  
   		RestFormProperty checkUpStatusFormProperty = new RestFormProperty();
   		checkUpStatusFormProperty.setId("checkUpStatus");
   		checkUpStatusFormProperty.setName("checkUpStatus");
   		checkUpStatusFormProperty.setType("String");
   		checkUpStatusFormProperty.setReadable(true);
   		checkUpStatusFormProperty.setValue((basicCheckUp.getCheckUpStatus()) );
   		formProperties.add(checkUpStatusFormProperty);
   		
   		 submitFormRequest.setProperties(formProperties); 
   		formsApi.submitForm(submitFormRequest);
   		if(basicCheckUp.getCheckUpStatus().equals("Required"))
   		basicCheckUpRepository.save(basicCheckUp);
		System.out.println("Task id=##"+nextTaskResource.getNextTaskId());
		nextTaskResource = resourceAssembler.toResource(processId);
		return nextTaskResource;
  	}
	@Override
	public NextTaskResource requestPatientHistory(String processId, String choice ) {
		log .info("into ====================requestPatientHistory()"); 
   		NextTaskResource nextTaskResource = resourceAssembler.toResource(processId);
   		  if(choice.equals("Accept")) {
			 OTPResponse res= sendSMS(number);			
		}
		  else {
			    String isApproved="false";
				List<RestFormProperty>formProperties=new ArrayList<RestFormProperty>();
		   		RestFormProperty approveProperty = new RestFormProperty();
		   		approveProperty.setId("isApproved");
		   		approveProperty.setName("isApproved");
		   		approveProperty.setType("String");
		   		approveProperty.setReadable(true);
		   		approveProperty.setValue(isApproved);
		   		formProperties.add(approveProperty);
				log.info("into ================>---<"+isApproved);
				SubmitFormRequest submitFormRequest = new SubmitFormRequest();
		   		submitFormRequest.setAction("completed");
				submitFormRequest.setTaskId(nextTaskResource.getNextTaskId());
				submitFormRequest.setProperties(formProperties); 
		   		formsApi.submitForm(submitFormRequest);
				nextTaskResource = resourceAssembler.toResource(processId);				 
		 }
		return nextTaskResource;
	}
	 
 
	
	public OTPResponse sendSMS(Long numbers) {
		if (numbers.toString().substring(0, 2).equals("91")) {
			log.info("it is an indian number");
			String message = "Dear User, Enter your OTP to complete registration. OTP to verify your Mobile is ";
			return smsResourceApiIN.sendSMS(message, apiKey_IN, numbers, SMSsender_IN);
		} else {
			log.info("it is not an indian number");
			String message = "Dear User, Enter your OTP to complete registration. OTP to verify your Mobile is ";
			return smsResourceApiUK.sendSMS(message, apiKey_UK, numbers, SMSsender_UK);
		}
    }
 
	public OTPChallenge verifyOTP(Long numbers, String code) {
		if (numbers.toString().substring(0, 2).equals("91")) {
			return smsResourceApiIN.verifyOTP(numbers, code, apiKey_IN);
		} else {
			return smsResourceApiUK.verifyOTP(numbers, code, apiKey_UK);

		}
	}
	@Override
	public Consultation consultPatient(String processId, Prescription prescription) {
		// TODO Auto-generated method stub
		NextTaskResource nextTaskResource = resourceAssembler.toResource(processId);   	
		Consultation consultationResult=new Consultation();
		consultationResult.setPrescription(prescription);
		String isPrescription="false";
		if(!(prescription.equals(null)) ){
			isPrescription="true";
		}
		List<RestFormProperty>formProperties=new ArrayList<RestFormProperty>();
   		RestFormProperty prescriptionProperty = new RestFormProperty();
   		prescriptionProperty.setId("isPrescription");
   		prescriptionProperty.setName("isPrescription");
   		prescriptionProperty.setType("String");
   		prescriptionProperty.setReadable(true);
   		prescriptionProperty.setValue(isPrescription);
   		formProperties.add(prescriptionProperty);
	 
		SubmitFormRequest submitFormRequest = new SubmitFormRequest();
   		submitFormRequest.setAction("completed");
		submitFormRequest.setTaskId(nextTaskResource.getNextTaskId());
		submitFormRequest.setProperties(formProperties); 
   		formsApi.submitForm(submitFormRequest);
		nextTaskResource = resourceAssembler.toResource(processId);
		 
		return consultationResult;
	}
	@Override
	public NextTaskResource storeHistory( String processId, String otp,String choice) {
		log.info("into ====================storedHistory()");
   		 NextTaskResource nextTaskResource = resourceAssembler.toResource(processId);   		
   		 String res="no data stored";
   		 String isApproved="false";
   		
		OTPChallenge status=verifyOTP(number,otp);
		log.info("******************** * **"+status.getStatus());
		
		if((status.getStatus()).equals("success"))
		{
			res="Data been stored"; 
			isApproved="true";
	
		}
		List<RestFormProperty>formProperties=new ArrayList<RestFormProperty>();
   		RestFormProperty approveProperty = new RestFormProperty();
   		approveProperty.setId("isApproved");
   		approveProperty.setName("isApproved");
   		approveProperty.setType("String");
   		approveProperty.setReadable(true);
   		approveProperty.setValue(isApproved);
   		formProperties.add(approveProperty);
		log.info("into ==================== >>"+res+">---<"+isApproved);
		SubmitFormRequest submitFormRequest = new SubmitFormRequest();
   		submitFormRequest.setAction("completed");
		submitFormRequest.setTaskId(nextTaskResource.getNextTaskId());
		submitFormRequest.setProperties(formProperties); 
   		formsApi.submitForm(submitFormRequest);
		nextTaskResource = resourceAssembler.toResource(processId);
		return nextTaskResource;
	}
	@Override
	public NextTaskResource savePatientPrescription(String processId, Prescription prescription) {
		// TODO Auto-generated method stub
		String res="not saved prescription",prescriptionStatus=null	;
		if(!(prescription.equals(null)) ){
			res="saved prescription";
		}
		NextTaskResource nextTaskResource = resourceAssembler.toResource(processId);  
		List<RestFormProperty>formProperties=new ArrayList<RestFormProperty>();
   		RestFormProperty prescriptionProperty = new RestFormProperty();
   		prescriptionProperty.setId("prescriptionStatus");
   		prescriptionProperty.setName("prescriptionStatus");
   		prescriptionProperty.setType("String");
   		prescriptionProperty.setReadable(true);
   		prescriptionProperty.setValue(prescriptionStatus);
   		formProperties.add(prescriptionProperty);
		log.info("into ==================== >>"+res+">---<"+prescriptionStatus);
		SubmitFormRequest submitFormRequest = new SubmitFormRequest();
   		submitFormRequest.setAction("completed");
		submitFormRequest.setTaskId(nextTaskResource.getNextTaskId());
		submitFormRequest.setProperties(formProperties); 
   		formsApi.submitForm(submitFormRequest);
		 
		return nextTaskResource;
	}
	 
}
