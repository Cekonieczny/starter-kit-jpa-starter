package com.capgemini.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.CarDao;
import com.capgemini.dao.EmployeeDao;
import com.capgemini.domain.CarEntity;
import com.capgemini.domain.CarType;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.mappers.CarMapper;
import com.capgemini.service.CarService;
import com.capgemini.types.CarTO;

@Service
@Transactional(readOnly = true)
public class CarServiceImpl implements CarService {
	@Autowired
	private CarDao carRepository;

	@Autowired
	private EmployeeDao employeeRepository;

	@Override
	public Set<CarTO> findCarByBrandAndType(String brand, CarType carType) {
		return CarMapper.map2TOs(carRepository.findByBrandAndType(brand, carType));
	}

	@Override
	public Set<CarTO> findCarByKeeper(Long employeeId) {
		return CarMapper.map2TOs(carRepository.findCarByEmployee(employeeId));
	}

	@Override
	@Transactional(readOnly = false)
	public CarTO saveCar(CarTO carTO) {
		return CarMapper.toCarTO(carRepository.save(CarMapper.toCarEntity(carTO)));
	}

	@Override
	@Transactional(readOnly = false)
	public CarTO assignCarToKeeper(Long employeeId, Long carId) {

		CarEntity carEntity = carRepository.findOne(carId);
		EmployeeEntity employeeEntity = employeeRepository.findOne(employeeId);

		employeeEntity.getCarEntities().add(carEntity);
		carEntity.getEmployeeEntities().add(employeeEntity);

		carRepository.update(carEntity);
		employeeRepository.update(employeeEntity);
		return CarMapper.toCarTO(carEntity);
	}

	@Override
	@Transactional(readOnly = false)
	public CarTO updateCar(CarTO carTO) {
		return CarMapper.toCarTO(carRepository.update(CarMapper.toCarEntity(carTO)));
	}

	@Override
	@Transactional(readOnly = false)
	public void removeCar(Long carId) {
		carRepository.delete(carId);
	}

}
