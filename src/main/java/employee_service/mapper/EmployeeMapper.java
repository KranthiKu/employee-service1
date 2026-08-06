package employee_service.mapper;

import employee_service.dto.EmployeeResponse;
import employee_service.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponse mapToEmployeeResponse(Employee employee) {

        return EmployeeResponse.builder()
                .id(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .salary(employee.getSalary())
                .status(employee.getStatus())
         //       .departmentName(employee.getDepartment().getDepartmentName())
        //        .departmentLocation(employee.getDepartment().getLocation())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }
}
