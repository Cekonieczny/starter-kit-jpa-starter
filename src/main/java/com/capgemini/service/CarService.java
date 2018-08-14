package com.capgemini.service;
import java.util.List;

import com.capgemini.types.CarTO;

public interface CarService {
	CarTO setCarToKeeper(CarTO car, Long keeperId);

	CarTO updateCar(CarTO car);

	void deleteCar(CarTO car);

	CarTO saveCar(CarTO car);
	
	List<CarTO> findByBrandAndType(String brand, Long carTypeId);
}
