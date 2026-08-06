package employee_service.dto;

import employee_service.entity.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    private Long id;

    private String employeeCode;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private BigDecimal salary;

    private EmployeeStatus status;

   // private String status;

    private String departmentName;

    private String departmentLocation;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}