package me.jaeuk.hr_module.service.employee;

import lombok.extern.slf4j.Slf4j;
import me.jaeuk.hr_module.domain.employee.Emp;
import org.junit.jupiter.api.BeforeAll;
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
    private EmpService employeeService;

    @BeforeAll
    public void init() {
        employeeService.addEmployee(new Emp("최구상"));
        employeeService.addEmployee(new Emp("최상주"));
        employeeService.addEmployee(new Emp("최변형"));
    }

    @Test
    public void getEmployeeById() {
        Emp emp = employeeService.getEmployeeById(1L);
        log.info("emp {}", emp);
    }

    @Test
    public void getAllEmployees() {
        List<Emp> employees = employeeService.getAllEmployees();
        log.info("employees {}", employees);
    }

    @Test
    public void deleteEmployee() {
        List<Emp> beforeAmployees = employeeService.getAllEmployees();
        Emp emp = new Emp();
        emp.setEmpId(1l);
        employeeService.deleteEmployee(emp);
        List<Emp> afterEmployees = employeeService.getAllEmployees();
        assertEquals(beforeAmployees.size()-1,afterEmployees.size());
    }

    @Transactional
    @Test
    public void addEmployee() {
        List<Emp> beforeAmployees = employeeService.getAllEmployees();
        employeeService.addEmployee(new Emp("최구상"));
        employeeService.addEmployee(new Emp("최상주"));
        employeeService.addEmployee(new Emp("최변형"));
        List<Emp> afterEmployees = employeeService.getAllEmployees();
        assertEquals(beforeAmployees.size()+3, afterEmployees.size());
    }
}