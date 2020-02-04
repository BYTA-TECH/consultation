package com.bytatech.ayoos.consultation.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bytatech.ayoos.consultation.domain.BasicCheckUp;
import com.bytatech.ayoos.consultation.domain.Consultation;
import com.bytatech.ayoos.consultation.domain.OTPChallenge;
import com.bytatech.ayoos.consultation.domain.OTPResponse;
import com.bytatech.ayoos.consultation.domain.Prescription;
import com.bytatech.ayoos.consultation.resource.assembler.NextTaskResource;
import com.bytatech.ayoos.consultation.service.ConsultationCommandService; 
 

@RestController
@RequestMapping("/api")
public class ConsultationCommandResource {
    @Autowired
	private final ConsultationCommandService consultationCommandService;
    public ConsultationCommandResource(ConsultationCommandService consultationCommandService) {
        this.consultationCommandService = consultationCommandService;
    }
	@PostMapping("/initiate")
	public NextTaskResource initiateConsultation() {
		NextTaskResource task=consultationCommandService.initiate();
		return task;
	}
	@PostMapping("/basicCheckup/{processId}")
	public NextTaskResource basicCheckUpTask(@PathVariable String processId, @RequestBody BasicCheckUp basicCheckUp) {
		NextTaskResource task=consultationCommandService.basicCheckUpTask(processId,basicCheckUp);
		return task;
	}
	@PostMapping("/requestPatientHistory/{processId}")
	public NextTaskResource requestPatientHistory(@PathVariable String processId, @RequestParam(value = "choice", required = false) String choice) {
		NextTaskResource task=consultationCommandService.requestPatientHistory(processId,choice);
		return task;
	}
	 @PostMapping("/patient/storeHistory/{processId}")
		 public NextTaskResource storeHistory(@PathVariable String processId, @RequestParam(value = "otp", required = false) String otp,@RequestParam(value = "choice", required = false) String choice) {  			
			return consultationCommandService.storeHistory( processId, otp ,choice);
		}	
	@PostMapping("/consultPatient/{processId}")
	public Consultation consultPatient(@PathVariable String processId, @RequestBody Prescription prescription) {
		Consultation result=consultationCommandService.consultPatient(processId,prescription );
		return result;
	}
	@PostMapping("/savePatientPrescription/{processId}")
	public NextTaskResource patientPrescription(@PathVariable String processId, @RequestBody Prescription prescription) {
		NextTaskResource task=consultationCommandService.savePatientPrescription(processId,prescription );
		return task;
	}

	
    
}
