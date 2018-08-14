package com.capgemini.dao;

import com.capgemini.domain.CarEntity;

public interface CarDao extends Dao<CarEntity, Long> {
	
	CarDao save(CarDao carDao);
	CarDao update (CarDao carDao);
	void delete(CarDao carDao);
	CarDao saveToKeeper(CarDao carDao, Long keeperId);
	

}
