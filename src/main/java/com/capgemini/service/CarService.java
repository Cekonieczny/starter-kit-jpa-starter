package com.capgemini.service;

import java.util.Set;

import com.capgemini.domain.CarType;
import com.capgemini.types.CarTO;

public interface CarService {

	Set<CarTO> findCarByBrandAndType(String brand, CarType carType);

	Set<CarTO> findCarByKeeper(Long employeeId);

	CarTO saveCar(CarTO carTO);

	CarTO assignCarToKeeper(Long employeeId, Long carId);

	CarTO updateCar(CarTO carTO);

	void removeCar(Long carId);

}