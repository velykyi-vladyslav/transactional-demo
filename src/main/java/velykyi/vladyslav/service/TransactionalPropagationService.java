package velykyi.vladyslav.service;

import velykyi.vladyslav.dto.PatientDto;

public interface TransactionalPropagationService {

    String doPropagation(PatientDto patientDto);

    String doPropagationWithoutTransactional(PatientDto patientDto);
}
