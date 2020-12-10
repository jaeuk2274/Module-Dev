package me.jaeuk.hr_module.repository.employee;

import me.jaeuk.hr_module.domain.employee.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    Employee selectEmployeeById(Long id);
    List<Employee> selectAllEmployees();
    void insertEmployee(Employee employee);
    void deleteEmployee(Employee employee);
}
