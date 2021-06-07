package kz.spring.product.controller;

import kz.spring.product.model.dto.request.AddScheduleRequest;
import kz.spring.product.model.dto.request.EditScheduleRequest;
import kz.spring.product.model.dto.request.ScheduleRequest;
import kz.spring.product.service.schedule.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/schedule")
@PreAuthorize("isAuthenticated()")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody AddScheduleRequest request){
        return scheduleService.add(request);
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<?> edit(@RequestBody EditScheduleRequest request){
        return scheduleService.edit(request);
    }

    @PostMapping(value = "/by/id")
    public ResponseEntity<?> getById(@RequestBody Map<String  , Long> id){
        return scheduleService.getById(id.get("id"));
    }

    @PutMapping(value = "/mark")
    public ResponseEntity<?> mark(@RequestBody Map<String , Long> id){
        return scheduleService.mark(id.get("id"));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return scheduleService.delete(id);
    }

    @PostMapping(value = "/get/by/date")
    public ResponseEntity<?> getByDate(@RequestBody ScheduleRequest request){
        return scheduleService.allSchedulesByDate(request);
    }

    @GetMapping(value = "/for/today")
    public ResponseEntity<?> forToday(){
        return scheduleService.getForToday();
    }

    @GetMapping(value = "/all")
    public ResponseEntity allSchedules(){
        return scheduleService.allSchedules();
    }
}
