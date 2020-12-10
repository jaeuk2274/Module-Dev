package me.jaeuk.hr_module.service.employee;

import lombok.extern.slf4j.Slf4j;
import me.jaeuk.hr_module.domain.employee.Employee;
import me.jaeuk.hr_module.domain.work.WorkShift;
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
        employeeService.addEmployee(new Employee("최재욱", WorkShift.TWO_GROUP_TWO_SHIFT_A));
        employeeService.addEmployee(new Employee("최현욱", WorkShift.TWO_GROUP_TWO_SHIFT_B));
        employeeService.addEmployee(new Employee("최영근", WorkShift.THREE_GROUP_TWO_SHIFT_A));
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
        employeeService.addEmployee(new Employee("최재욱", WorkShift.TWO_GROUP_TWO_SHIFT_A));
        employeeService.addEmployee(new Employee("최현", WorkShift.TWO_GROUP_TWO_SHIFT_B));
        employeeService.addEmployee(new Employee("최영근", WorkShift.THREE_GROUP_TWO_SHIFT_A));
        List<Employee> afterEmployees = employeeService.getAllEmployees();
        assertEquals(beforeAmployees.size()+3, afterEmployees.size());
    }
}