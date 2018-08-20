package com.capgemini.dao;

import java.util.Date;
import java.util.Set;

import com.capgemini.domain.CarEntity;
import com.capgemini.domain.CarType;

public interface CarDao extends Dao<CarEntity, Long> {
	//- Znajdź samochody, które zostały wypożyczone przez więcej niż 10 osób (nie 10 razy, a przez 10 różnych osób)
//	- Policz samochody, które były wypożyczone 
	//(były w użytku, nie sama czynność wypożyczenia) w zadanym przedziale czasowym

	Set<CarEntity> findByBrandAndType(String brand, CarType carType);
	Set<CarEntity> findCarByEmployee(Long employeeId);
	Set<CarEntity> findCarLoanedByMoreThanTenDistinctCustomers();
	Long countNumberOfCarsLoanedInACertainPeriodOfTime(Date dateFrom, Date dateTo);
}
