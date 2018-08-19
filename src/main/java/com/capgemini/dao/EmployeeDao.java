package com.capgemini.dao;

import java.util.Set;

import com.capgemini.domain.EmployeeEntity;

public interface EmployeeDao extends Dao<EmployeeEntity, Long>{
	Set<EmployeeEntity> findByOffice(Long officeId);
	Set<EmployeeEntity> findByOfficeAndCarKeeped(Long officeId,Long carId);
}
