package employee_service.controller;

import employee_service.dto.EmployeeRequest;
import employee_service.dto.EmployeeResponse;
import employee_service.dto.EmployeeStatusRequest;
import employee_service.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse response = employeeService.createEmployee(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {

        EmployeeResponse response = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAllEmployees(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDir) {

        return ResponseEntity.ok(
                employeeService.getAllEmployees(page, size, sortBy, sortDir));
    }



    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        EmployeeResponse response = employeeService.updateEmployee(id, request);

        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok("Employee deleted successfully");
    }



    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeResponse>> searchEmployees(

            @RequestParam(required = false) String firstName,

            @RequestParam(required = false) String status,

            @RequestParam(required = false) Long departmentId,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDir) {

        return ResponseEntity.ok(
                employeeService.searchEmployees(
                        firstName,
                        status,
                        departmentId,
                        page,
                        size,
                        sortBy,
                        sortDir));
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<EmployeeResponse> updateEmployeeStatus(

            @PathVariable Long id,

            @Valid
            @RequestBody EmployeeStatusRequest request) {

        return ResponseEntity.ok(
                employeeService.updateEmployeeStatus(id, request));
    }

}
