package kz.spring.product.model;

import kz.spring.product.model.dto.request.AddScheduleRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description" , columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at" , updatable = false , nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at" , nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "certain_time" , updatable = false )
    private LocalDateTime certainTime;

    @Column(name = "is_done")
    private boolean done;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Schedule(AddScheduleRequest request, User user) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.certainTime = request.getCertainTime();
        this.user = user;
    }
}
