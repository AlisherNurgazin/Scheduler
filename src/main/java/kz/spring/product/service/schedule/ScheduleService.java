package kz.spring.product.service.schedule;

import kz.spring.product.model.Schedule;
import kz.spring.product.model.dto.request.AddScheduleRequest;
import kz.spring.product.model.dto.request.EditScheduleRequest;
import kz.spring.product.model.dto.request.ScheduleRequest;
import org.springframework.http.ResponseEntity;

public interface ScheduleService {
    ResponseEntity<?> add(AddScheduleRequest request);
    ResponseEntity<?> edit(EditScheduleRequest request);
    ResponseEntity<?> getById(Long id);
    ResponseEntity<?> mark(Long id);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> allSchedulesByDate(ScheduleRequest request);
    ResponseEntity<?> getForToday();
    ResponseEntity<?> allSchedules();
    Schedule findById(Long id);
    void save(Schedule schedule);
}
