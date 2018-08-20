package com.capgemini.dao;

import java.util.Date;
import java.util.Set;

import com.capgemini.domain.CarEntity;
import com.capgemini.domain.CarType;

public interface CarDao extends Dao<CarEntity, Long> {
	Set<CarEntity> findByBrandAndType(String brand, CarType carType);
	Set<CarEntity> findCarByEmployee(Long employeeId);
	Set<CarEntity> findCarLoanedByMoreThanTenDistinctCustomers();
	Long countNumberOfCarsLoanedInACertainPeriodOfTime(Date dateFrom, Date dateTo);
}
