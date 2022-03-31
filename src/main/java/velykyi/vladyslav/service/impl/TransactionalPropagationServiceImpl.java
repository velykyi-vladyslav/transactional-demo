package velykyi.vladyslav.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import velykyi.vladyslav.dto.PatientDto;
import velykyi.vladyslav.repository.PatientRepository;
import velykyi.vladyslav.repository.model.Patient;
import velykyi.vladyslav.service.TransactionalPropagationService;

import static org.springframework.transaction.annotation.Propagation.*;

@Service
@RequiredArgsConstructor
public class TransactionalPropagationServiceImpl implements TransactionalPropagationService {

    private static final Logger log = LoggerFactory.getLogger(TransactionalPropagationServiceImpl.class);

    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public String doPropagation(PatientDto patientDto) {
        /* todo investigate why propagation is not working
        Log: Participating in existing transaction
        for all inner methods.
         */
        log.info("- - - > INSIDE OUTER doPropagation() METHOD < - - -");

        innerSupports(patientDto);

        log.info("- - - > EXIT innerSupports() METHOD < - - -");

        innerNotSupported(patientDto);

        log.info("- - - > EXIT innerNotSupported() METHOD < - - -");

        innerRequired(patientDto);

        log.info("- - - > EXIT innerRequired() METHOD < - - -");

        innerMandatory(patientDto);

        log.info("- - - > EXIT innerMandatory() METHOD < - - -");

        try {
            innerNever(patientDto);
        }catch (Exception ex){
            log.info(ex.getCause().getMessage());
        }

        log.info("- - - > EXIT innerNever() METHOD < - - -");

        return patientRepository.save(mapPatientDtoToPatient(patientDto)).toString();
    }

    @Override
    public String doPropagationWithoutTransactional(PatientDto patientDto) {
        /* todo investigate why propagation is not working
        Log: Creating new transaction with name [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
        for all inner methods.
         */
        log.info("- - - > INSIDE OUTER doPropagationWithoutTransactional() METHOD < - - -");

        innerSupports(patientDto);

        log.info("- - - > EXIT innerSupports() METHOD < - - -");

        try {
            innerMandatory(patientDto);
        }catch (Exception ex){
            log.info(ex.getCause().getMessage());
        }

        log.info("- - - > EXIT innerMandatory() METHOD < - - -");

        innerNever(patientDto);

        log.info("- - - > EXIT innerNever() METHOD < - - -");

        return patientRepository.save(mapPatientDtoToPatient(patientDto)).toString();
    }

    /**
     * SUPPORTS
     * <p>If method is called from another service method:
     * <li> that has the transaction - uses the existing one.
     * <li> that does not have the transaction - will not create a new one.
     *
     * @param patientDto some dto.
     */
    @Transactional(propagation = SUPPORTS)
    public void innerSupports(PatientDto patientDto) {
        log.info("- - - > INSIDE innerSupports() METHOD < - - -");

        patientRepository.save(mapPatientDtoToPatient(patientDto));
    }

    /**
     * NOT_SUPPORTED
     * <p>If a current transaction exists, first Spring suspends it, and then the business logic is executed
     * without a transaction.
     *
     * @param patientDto some dto
     */
    @Transactional(propagation = NOT_SUPPORTED)
    public void innerNotSupported(PatientDto patientDto) {
        log.info("- - - > INSIDE innerNotSupported() METHOD < - - -");

        patientRepository.save(mapPatientDtoToPatient(patientDto));
    }

    /**
     * REQUIRES_NEW
     * <p>Spring suspends the current transaction if it exists, and then creates a new one.
     *
     * @param patientDto some dto
     */
    @Transactional(propagation = REQUIRES_NEW)
    public void innerRequired(PatientDto patientDto) {
        log.info("- - - > INSIDE innerRequired() METHOD < - - -");

        patientRepository.save(mapPatientDtoToPatient(patientDto));
    }

    /**
     * MANDATORY
     * <p>If the active transaction:
     * <li>exists - then it will be used.
     * <li>NOT exists - then Spring throw an exception.
     *
     * @param patientDto some dto
     */
    @Transactional(propagation = MANDATORY)
    public void innerMandatory(PatientDto patientDto) {
        log.info("- - - > INSIDE innerMandatory() METHOD < - - -");

        patientRepository.save(mapPatientDtoToPatient(patientDto));
    }

    /**
     * NEVER
     * <p>Spring throws an exception if there's an active transaction.
     *
     * @param patientDto some dto
     */
    @Transactional(propagation = NEVER)
    public void innerNever(PatientDto patientDto) {
        log.info("- - - > INSIDE innerNever() METHOD < - - -");

        patientRepository.save(mapPatientDtoToPatient(patientDto));
    }

    private Patient mapPatientDtoToPatient(PatientDto patientDto){
        return new ObjectMapper().convertValue(patientDto, Patient.class);
    }
}
