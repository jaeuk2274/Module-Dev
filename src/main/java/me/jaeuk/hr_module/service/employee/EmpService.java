package me.jaeuk.hr_module.service.employee;

import me.jaeuk.hr_module.domain.employee.Emp;
import me.jaeuk.hr_module.repository.employee.EmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpService {
    @Autowired
    private EmpMapper employeeMapper;

    public Emp getEmployeeById(Long id) {
        return employeeMapper.selectEmployeeById(id);
    }
    public List<Emp> getAllEmployees() {
        return employeeMapper.selectAllEmployees();
    }

    @Transactional
    public void addEmployee(Emp employee) { employeeMapper.insertEmployee(employee); }
    public void deleteEmployee(Emp employee) { employeeMapper.deleteEmployee(employee); }
}
