package me.jaeuk.hr_module.service.employee;

import me.jaeuk.hr_module.domain.employee.Employee;
import me.jaeuk.hr_module.repository.employee.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    public Employee getEmployeeById(Long id) {
        return employeeMapper.selectEmployeeById(id);
    }
    public List<Employee> getAllEmployees() {
        return employeeMapper.selectAllEmployees();
    }

    @Transactional
    public void addEmployee(Employee employee) { employeeMapper.insertEmployee(employee); }
    public void deleteEmployee(Employee employee) { employeeMapper.deleteEmployee(employee); }
}
