package velykyi.vladyslav.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import velykyi.vladyslav.dto.PatientDto;
import velykyi.vladyslav.service.impl.TransactionalPropagationServiceImpl;

@RestController
@RequiredArgsConstructor
public class BookAppointmentController {

    private final TransactionalPropagationServiceImpl transactionalPropagationService;

    @PostMapping("/propagation")
    public String propagation(@RequestBody PatientDto patientDto) {
        return transactionalPropagationService.doPropagation(patientDto);
    }

    @PostMapping("/propagationNoTransaction")
    public String doPropagationWithoutTransactional(@RequestBody PatientDto patientDto) {
        return transactionalPropagationService.doPropagationWithoutTransactional(patientDto);
    }
}
