package kz.iitu.java.userserviceclient.payload.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientFullInfoRequest {
    private String iin;
    private String lastname;
    private String firstname;
    private String middleName;
    private LocalDate birthDate;
    private String gender;
    private String documentNumber;
    private String documentIssuedBy;
    private LocalDate documentIssuedDate;
    private LocalDate documentValidUntilDate;
    private int salary;
    private int communalPayment;
}
