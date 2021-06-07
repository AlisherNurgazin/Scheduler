package kz.spring.product.repository.schedule;

import kz.spring.product.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule , Long> {
    Optional<Schedule> findById(Long id);
    List<Schedule> findAllByCertainTimeBetweenAndUser_Id(LocalDateTime from , LocalDateTime to , UUID id);
    List<Schedule> findAllByCertainTimeIsStartingWithAndUser_Id(LocalDateTime dateTime , UUID id);
}
