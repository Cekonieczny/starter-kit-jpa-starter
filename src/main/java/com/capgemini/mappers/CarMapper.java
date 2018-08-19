package com.capgemini.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import com.capgemini.domain.CarEntity;
import com.capgemini.types.CarTO;
import com.capgemini.types.CarTO.CarTOBuilder;

public class CarMapper {

	public static CarTO toCarTO(CarEntity carEntity) {
		if (carEntity == null)
			return null;

		return new CarTOBuilder().withBrand(carEntity.getBrand()).withCarType(carEntity.getCarType())
				.withColor(carEntity.getColor()).withEngineCapacity(carEntity.getEngineCapacity())
				.withMileage(carEntity.getMileage()).withModel(carEntity.getModel())
				.withYearOfManufacture(carEntity.getYearOfManufacture()).withHorsepower(carEntity.getHorsepower())
				.withId(carEntity.getId()).build();

	}

	public static CarEntity toCarEntity(CarTO carTO) {
		if (carTO == null)
			return null;
		CarEntity carEntity = new CarEntity();
		carEntity.setBrand(carTO.getBrand());
		carEntity.setCarType(carTO.getCarType());
		carEntity.setColor(carTO.getColor());
		carEntity.setEngineCapacity(carTO.getEngineCapacity());
		carEntity.setHorsepower(carTO.getHorsepower());
		carEntity.setId(carTO.getId());
		carEntity.setMileage(carTO.getMileage());
		carEntity.setModel(carTO.getModel());
		carEntity.setYearOfManufacture(carTO.getYearOfManufacture());
		return carEntity;
	}

	public static Set<CarTO> map2TOs(Set<CarEntity> carEntities) {
		return carEntities.stream().map(CarMapper::toCarTO).collect(Collectors.toSet());
	}

	public static Set<CarEntity> map2Entities(Set<CarTO> carTOs) {
		return carTOs.stream().map(CarMapper::toCarEntity).collect(Collectors.toSet());
	}

}
