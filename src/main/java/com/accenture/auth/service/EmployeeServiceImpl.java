package com.accenture.auth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.auth.dao.EmployeeDAO;
import com.accenture.auth.entity.EmployeeEntity;
import com.accenture.auth.model.Employee;
@Service
public class EmployeeServiceImpl {

	@Autowired
	private EmployeeDAO employeeDAO;

	public int addEmployee(Employee employee) {

		EmployeeEntity employeeEntity = new EmployeeEntity();
		BeanUtils.copyProperties(employee, employeeEntity);
		EmployeeEntity emp= (EmployeeEntity)employeeDAO.save(employeeEntity);
		System.out.println(emp);
		return emp.getEmployeeId();
	}

	public Collection<Employee> getEmployeeDetails(){
		Collection<EmployeeEntity> collec =employeeDAO.findAll();
		List<Employee> listEmployee = new ArrayList<Employee> ();
		for (EmployeeEntity employeeEntity : collec) {
			Employee employee=new Employee();
			BeanUtils.copyProperties(employeeEntity, employee);
			listEmployee.add(employee);
		}
		return listEmployee;
	}
	
	public Employee getEmployeeDetailByEmployeeId(int employeeId){
		Employee employee =null;
		EmployeeEntity employeeEntity= employeeDAO.findOne(employeeId);
		if(employeeEntity!=null){
			employee= new Employee();
			BeanUtils.copyProperties(employeeEntity, employee);
		}
		return employee;
	}
	public Employee deleteEmployee(int employeeId){
		Employee employee =null;
		EmployeeEntity employeeEntity= employeeDAO.findOne(employeeId);
		if(employeeEntity!=null){
			employeeDAO.delete(employeeEntity);
			employee= new Employee();
			BeanUtils.copyProperties(employeeEntity, employee);
		}
		return employee;
	}
	
	public Employee updateEmployee(Employee employee){
		Employee employee2 =null;
		EmployeeEntity employeeEntity= employeeDAO.findOne(employee.getEmployeeId());
		if(employeeEntity!=null){
			//update operation
			BeanUtils.copyProperties(employee, employeeEntity);	
			employeeDAO.save(employeeEntity);
			
			//copying the properties back to Employee DTO Object 
			employee2= new Employee();
			BeanUtils.copyProperties(employeeEntity, employee2);
		}
		return employee2;
	}
	
	
}
