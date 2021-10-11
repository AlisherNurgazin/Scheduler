package kz.iitu.java.userserviceclient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity @Table(name = "users") @Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Client {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "iin")
    private String iin;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "middle_name")
    private String middleName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "gender")
    private String gender;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at" , nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "salary")
    private int salary;

    @Column(name = "communal_payment")
    private int communalPayment;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "issued_by")
    private String documentIssuedBy;

    //    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "issued_date")
    private LocalDate documentIssuedDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "valid_until_date")
    private LocalDate documentValidUntilDate;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id" , referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id" , referencedColumnName = "id"
            )
    )
    private List<Role> roles;
}
