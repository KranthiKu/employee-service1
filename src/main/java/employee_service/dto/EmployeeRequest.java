package employee_service.dto;

import employee_service.entity.EmployeeStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest {

    @NotBlank(message = "Employee Code is required")
    private String employeeCode;

    @NotBlank(message = "First Name is required")
    private String firstName;

    private String lastName;

    @Email(message = "Invalid Email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone Number is required")
    private String phone;

    @Positive(message = "Salary should be greater than zero")
    private BigDecimal salary;

    @NotNull(message = "Status is required")
    private EmployeeStatus status;

    //private String status;

    @NotNull(message = "Department Id is required")
    private Long departmentId;
}
