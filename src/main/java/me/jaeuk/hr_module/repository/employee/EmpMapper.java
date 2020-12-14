package me.jaeuk.hr_module.repository.employee;

import me.jaeuk.hr_module.domain.employee.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpMapper {
    Emp selectEmployeeById(Long id);
    List<Emp> selectAllEmployees();
    void insertEmployee(Emp employee);
    void deleteEmployee(Emp employee);
}
