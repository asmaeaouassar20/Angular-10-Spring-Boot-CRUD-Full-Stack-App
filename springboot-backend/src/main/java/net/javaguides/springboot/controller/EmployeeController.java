package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;


	// get all employees
	@GetMapping("/employees")
	public String getAllEmployees(Model model){
		model.addAttribute("ListEmployees",employeeRepository.findAll());
		return "employees";
	}



	
	// create employee
	@PostMapping("/employees")
	public String createEmployee(@ModelAttribute("employee") Employee employee) {
		 employeeRepository.save(employee);
		 return "redirect:employees";
	}


	
	// get employee by id rest api
	@GetMapping("/showDetails/{id}")
	public String getEmployeeById(
			@PathVariable Long id,
			Model model
	) {
		Employee employee = employeeRepository.findById(id).get();
		model.addAttribute("employee",employee);
		return "showdetails";
	}



	// update employee rest api
	@GetMapping("/update/{id}")
	public String updateEmployee(
			@PathVariable Long id,
			Model model
			){
		Employee employee = employeeRepository.findById(id).get();
		model.addAttribute("employee",employee);
		return "updateEmployee";
	}

	@PostMapping("/updateEmployee")
	public String updateEmployee(@ModelAttribute("employee") Employee employee){

		employeeRepository.save(employee);
		return "redirect:/api/v1/employees";
	}



	// delete employee rest api
	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id).get();
		
		employeeRepository.delete(employee);
		return "redirect:/api/v1/employees";
	}
	
	// ajouter nouveau employee
	@GetMapping("/addNewEmployee")
	public String formAddNewEmployee(Model model){
		Employee newEmployee=new Employee();
		model.addAttribute("employee",newEmployee);
		return "newemployee";
	}
}
