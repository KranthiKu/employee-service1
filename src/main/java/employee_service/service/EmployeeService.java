package employee_service.service;

import employee_service.dto.EmployeeRequest;
import employee_service.dto.EmployeeResponse;
import employee_service.dto.EmployeeStatusRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

  //  List<EmployeeResponse> getAllEmployees();

    Page<EmployeeResponse> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String sortDir
    );



    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);


    Page<EmployeeResponse> searchEmployees(
            String firstName,
            String status,
            Long departmentId,
            int page,
            int size,
            String sortBy,
            String sortDir
    );


    EmployeeResponse updateEmployeeStatus(
            Long id,
            EmployeeStatusRequest request);



}
