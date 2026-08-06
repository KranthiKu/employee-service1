package employee_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeStatusRequest {

    @NotBlank(message = "Status is required")
  //  private EmployeeStatus status;
    private String status;

}