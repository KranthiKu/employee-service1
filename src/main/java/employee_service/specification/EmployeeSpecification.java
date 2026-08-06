package employee_service.specification;

import employee_service.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

    public static Specification<Employee> hasFirstName(String firstName) {

        return (root, query, cb) ->

                firstName == null || firstName.isBlank()

                        ? null

                        : cb.like(
                        cb.lower(root.get("firstName")),
                        "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<Employee> hasStatus(String status) {

        return (root, query, cb) ->

                status == null || status.isBlank()

                        ? null

                        : cb.equal(root.get("status"), status);
    }

    public static Specification<Employee> hasDepartment(Long departmentId) {

        return (root, query, cb) ->

                departmentId == null

                        ? null

                        : cb.equal(root.get("department").get("id"), departmentId);
    }
}
