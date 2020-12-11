package me.jaeuk.hr_module.service.employee;

import lombok.extern.slf4j.Slf4j;
import me.jaeuk.hr_module.domain.employee.Employee;
import me.jaeuk.hr_module.domain.work.WorkShift;
import me.jaeuk.hr_module.domain.work.WorkType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @BeforeAll
    public void init() {
        employeeService.addEmployee(new Employee("최구상"));
        employeeService.addEmployee(new Employee("최상주"));
        employeeService.addEmployee(new Employee("최변형"));
    }

    @Test
    public void getEmployeeById() {
        Employee emp = employeeService.getEmployeeById(1L);
        log.info("emp {}", emp);
    }

    @Test
    public void getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        log.info("employees {}", employees);
    }

    @Test
    public void deleteEmployee() {
        List<Employee> beforeAmployees = employeeService.getAllEmployees();
        Employee emp = new Employee();
        emp.setEmpId(1l);
        employeeService.deleteEmployee(emp);
        List<Employee> afterEmployees = employeeService.getAllEmployees();
        assertEquals(beforeAmployees.size()-1,afterEmployees.size());
    }

    @Transactional
    @Test
    public void addEmployee() {
        List<Employee> beforeAmployees = employeeService.getAllEmployees();
        employeeService.addEmployee(new Employee("최구상"));
        employeeService.addEmployee(new Employee("최상주"));
        employeeService.addEmployee(new Employee("최변형"));
        List<Employee> afterEmployees = employeeService.getAllEmployees();
        assertEquals(beforeAmployees.size()+3, afterEmployees.size());
    }
}