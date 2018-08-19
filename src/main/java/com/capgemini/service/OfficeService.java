package com.capgemini.service;

import java.util.Set;

import com.capgemini.types.EmployeeTO;
import com.capgemini.types.OfficeTO;

public interface OfficeService {
	/**
	 * 
	 * @param officeId
	 * @return
	 */
	Set<EmployeeTO> findEmployeesByOffice(Long officeId);

	/**
	 * 
	 * @param officeId
	 * @param carId
	 * @return
	 */
	Set<EmployeeTO> findEmployeesByOfficeAndCarKeeped(Long officeId, Long carId);

	/**
	 * 
	 * @param officeTO
	 * @return
	 */
	OfficeTO saveOffice(OfficeTO officeTO);

	/**
	 * 
	 * @param officeTO
	 * @return
	 */
	OfficeTO updateOffice(OfficeTO officeTO);

	/**
	 * 
	 * @param officeId
	 */
	void removeOffice(Long officeId);

	/**
	 * 
	 * @param officeId
	 * @param employeeId
	 * @return
	 */
	EmployeeTO addEmployeeToOffice(Long officeId, Long employeeId);

	/**
	 * 
	 * @param officeId
	 * @param employeeId
	 * @return
	 */
	EmployeeTO removeEmployeeFromOffice(Long officeId, Long employeeId);
}
