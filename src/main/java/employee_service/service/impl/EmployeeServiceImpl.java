package employee_service.service.impl;

import employee_service.dto.EmployeeRequest;
import employee_service.dto.EmployeeResponse;
import employee_service.dto.EmployeeStatusRequest;
import employee_service.entity.Department;
import employee_service.entity.Employee;
import employee_service.entity.EmployeeStatus;
import employee_service.exception.DuplicateResourceException;
import employee_service.exception.ResourceNotFoundException;
import employee_service.mapper.EmployeeMapper;
import employee_service.repository.DepartmentRepository;
import employee_service.repository.EmployeeRepository;
import employee_service.service.EmployeeService;
import employee_service.specification.EmployeeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        // Check duplicate employee code
        if (employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new DuplicateResourceException("Employee Code already exists");
        }

        // Check duplicate email
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // Fetch department
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        // Build employee entity
        Employee employee = Employee.builder()
                .employeeCode(request.getEmployeeCode())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .salary(request.getSalary())
                .status(request.getStatus())
                .department(department)
                .build();

        // Save employee
        Employee savedEmployee = employeeRepository.save(employee);

        // Return response DTO
//        return EmployeeResponse.builder()
//                .id(savedEmployee.getId())
//                .employeeCode(savedEmployee.getEmployeeCode())
//                .firstName(savedEmployee.getFirstName())
//                .lastName(savedEmployee.getLastName())
//                .email(savedEmployee.getEmail())
//                .phone(savedEmployee.getPhone())
//                .salary(savedEmployee.getSalary())
//                .status(savedEmployee.getStatus())
//                .departmentName(savedEmployee.getDepartment().getDepartmentName())
//                .departmentLocation(savedEmployee.getDepartment().getLocation())
//                .createdAt(savedEmployee.getCreatedAt())
//                .updatedAt(savedEmployee.getUpdatedAt())
//                .build();

        return employeeMapper.mapToEmployeeResponse(savedEmployee);
    }


//
//    @Override
//    public EmployeeResponse getEmployeeById(Long id) {
//       throw new UnsupportedOperationException("Not implemented yet THARAK");
//
//    }


    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id : " + id));

        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setEmployeeCode(employee.getEmployeeCode());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setEmail(employee.getEmail());
        response.setPhone(employee.getPhone());

        return response;
    }

//    @Override
//    public EmployeeResponse getEmployeeById(Long id) {
//
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        return
//    }



//    @Override
//    public List<EmployeeResponse> getAllEmployees() {
//        throw new UnsupportedOperationException("Not implemented yet");
//    }

    @Override
    public Page<EmployeeResponse> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        return employeePage.map(employeeMapper::mapToEmployeeResponse);
    }



//    @Override
//    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
//        throw new UnsupportedOperationException("Not implemented yet");
//    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id : " + id));

        // Check duplicate employee code
        employeeRepository.findByEmployeeCode(request.getEmployeeCode())
                .ifPresent(emp -> {
                    if (!emp.getId().equals(id)) {
                        throw new DuplicateResourceException("Employee Code already exists");
                    }
                });

        // Check duplicate email
        employeeRepository.findByEmail(request.getEmail())
                .ifPresent(emp -> {
                    if (!emp.getId().equals(id)) {
                        throw new DuplicateResourceException("Email already exists");
                    }
                });

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found"));

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setSalary(request.getSalary());
        employee.setStatus(request.getStatus());
        employee.setDepartment(department);

        Employee updatedEmployee = employeeRepository.save(employee);

        return employeeMapper.mapToEmployeeResponse(updatedEmployee);
    }

//    @Override
//    public void deleteEmployee(Long id) {
//
//       // throw new UnsupportedOperationException("Not implemented yet");
//
//
//    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id : " + id));

        employeeRepository.delete(employee);

    }



    @Override
    public Page<EmployeeResponse> searchEmployees(
            String firstName,
            String status,
            Long departmentId,
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Employee> specification =
                Specification.where(EmployeeSpecification.hasFirstName(firstName))
                        .and(EmployeeSpecification.hasStatus(status))
                        .and(EmployeeSpecification.hasDepartment(departmentId));

        Page<Employee> employeePage =
                employeeRepository.findAll(specification, pageable);

        return employeePage.map(employeeMapper::mapToEmployeeResponse);
    }





    @Override
    public EmployeeResponse updateEmployeeStatus(
            Long id,
            EmployeeStatusRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id : " + id));

        employee.setStatus(EmployeeStatus.valueOf(request.getStatus()));

        Employee updatedEmployee =
                employeeRepository.save(employee);

        return employeeMapper.mapToEmployeeResponse(updatedEmployee);
    }


}
