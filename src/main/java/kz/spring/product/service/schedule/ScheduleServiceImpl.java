package kz.spring.product.service.schedule;

import kz.spring.product.exceptions.CustomNotFoundException;
import kz.spring.product.model.Schedule;
import kz.spring.product.model.dto.request.AddScheduleRequest;
import kz.spring.product.model.dto.request.EditScheduleRequest;
import kz.spring.product.model.dto.request.ScheduleRequest;
import kz.spring.product.model.dto.response.ScheduleResponse;
import kz.spring.product.repository.schedule.ScheduleRepository;
import kz.spring.product.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository repository;
    private final UserService userService;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> add(AddScheduleRequest request) {
        Schedule schedule = new Schedule(request  , userService.getAuth() );
        save(schedule);
        return new ResponseEntity<>("Schedule is created" , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> edit(EditScheduleRequest request) {
        Schedule schedule = findById(request.getId());
        schedule.setName(request.getName());
        schedule.setDescription(request.getDescription());
        schedule.setCertainTime(request.getCertainTime());
        save(schedule);
        return new ResponseEntity<>("Schedule is edited"  ,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        Schedule schedule = findById(id);
        ScheduleResponse response = new ScheduleResponse(schedule.getName() , schedule.getDescription(),
                schedule.getCertainTime() , schedule.getCreatedAt() , schedule.getUpdatedAt() ,
                schedule.getUser().getFullName());
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> mark(Long id) {
        Schedule schedule = findById(id);
        if (schedule.isDone()==true){
            schedule.setDone(false);
        }
        schedule.setDone(true);
        save(schedule);
        return new ResponseEntity<>("Schedule isDone marked as "+ schedule.isDone() , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Schedule schedule = findById(id);
        schedule.setUser(null);
        save(schedule);
        repository.delete(schedule);
        return new ResponseEntity<>("Schedule is deleted" , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> allSchedulesByDate(ScheduleRequest request) {
        LocalDateTime start = request.getFrom().toLocalDate().atStartOfDay().minusSeconds(1);
        LocalDateTime end = request.getTo().toLocalDate().atTime(LocalTime.MAX).minusSeconds(1);
        List<Schedule> allSchedules = repository.findAllByCertainTimeBetweenAndUser_Id(start ,
                end , userService.getAuth().getId());
        List<ScheduleResponse> response = new ArrayList<>();
        for (Schedule s : allSchedules){
            response.add(new ScheduleResponse(s.getName() , s.getDescription() , s.getCertainTime(),
                    s.getCreatedAt() , s.getUpdatedAt() , s.getUser().getFullName()));
        }
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getForToday() {
        LocalDateTime now = LocalDateTime.now().toLocalDate().atStartOfDay().minusSeconds(1);
        List<Schedule> schedules = repository.findAllByCertainTimeIsStartingWithAndUser_Id(now , userService.getAuth().getId());
        List<ScheduleResponse> response = new ArrayList<>();
        for (Schedule s : schedules){
            response.add(new ScheduleResponse(s.getName() , s.getDescription() , s.getCertainTime(),
                    s.getCreatedAt() , s.getUpdatedAt() , s.getUser().getFullName()));
        }
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> allSchedules() {
        List<Schedule> schedules = repository.findAll();
        List<ScheduleResponse> response = new ArrayList<>();
        for (Schedule s : schedules){
            response.add(new ScheduleResponse(s.getName() , s.getDescription() , s.getCertainTime(),
                    s.getCreatedAt() , s.getUpdatedAt() , s.getUser().getFullName()));
        }
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @Override
    public Schedule findById(Long id) {
        return repository.findById(id).orElseThrow(()->new CustomNotFoundException(
                String.format("Schedule with id : %s not found" , id)
        ));
    }

    @Override
    public void save(Schedule schedule) {
        repository.save(schedule);
    }
}
