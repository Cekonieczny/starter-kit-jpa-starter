package com.capgemini.dao;

import java.util.List;
import java.util.Set;

import com.capgemini.domain.EmployeeEntity;
import com.capgemini.searchcriteria.EmployeeSearchCriteria;

public interface EmployeeDao extends Dao<EmployeeEntity, Long>{
	Set<EmployeeEntity> findByOffice(Long officeId);
	Set<EmployeeEntity> findByOfficeAndCarKeeped(Long officeId,Long carId);
	List<EmployeeEntity> findEmployeeByGivenCriteria(List<EmployeeSearchCriteria> params);
}
