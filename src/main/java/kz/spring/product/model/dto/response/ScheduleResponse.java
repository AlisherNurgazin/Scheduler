package kz.spring.product.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ScheduleResponse {
    private String name;
    private String description;
    private LocalDateTime certainTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String author;
}
