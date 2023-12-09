package com.accenture.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accenture.auth.entity.EmployeeEntity;

public interface EmployeeDAO  extends JpaRepository<EmployeeEntity, Integer>{

}
