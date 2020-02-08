package com.bytatech.ayoos.consultation.service;
  

import com.bytatech.ayoos.consultation.domain.BasicCheckUp;
import com.bytatech.ayoos.consultation.domain.Consultation;
import com.bytatech.ayoos.consultation.domain.OTPChallenge;
import com.bytatech.ayoos.consultation.domain.OTPResponse;
import com.bytatech.ayoos.consultation.domain.Prescription;
import com.bytatech.ayoos.consultation.resource.assembler.NextTaskResource;

public interface ConsultationCommandService {
	NextTaskResource initiate(); 

	NextTaskResource basicCheckUpTask(String processId, BasicCheckUp basicCheckUp);

	NextTaskResource requestPatientHistory(String processId, String choice );
	Consultation consultPatient( String processId, Prescription prescription );
    NextTaskResource storeHistory( String processId, String otp,String choice);

	NextTaskResource savePatientPrescription(String processId, Prescription prescription);
}
