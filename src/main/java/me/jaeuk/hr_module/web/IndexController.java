package me.jaeuk.hr_module.web;

import lombok.RequiredArgsConstructor;
import me.jaeuk.hr_module.domain.employee.Emp;
import me.jaeuk.hr_module.service.employee.EmpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final EmpService employeeService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("empList", employeeService.getAllEmployees());
        return "index";
    }

    @GetMapping("/login/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        Emp emp = employeeService.getEmployeeById(id);
        model.addAttribute("emp", emp);
        return "index";
    }


    @GetMapping("/overtime/save")
    public String postsSave() {
        return "overtime-save";
    }
}
