package velykyi.vladyslav.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import velykyi.vladyslav.repository.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient getByName(String name);
}
