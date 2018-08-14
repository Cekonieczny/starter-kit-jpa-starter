package com.capgemini.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import com.capgemini.dao.CarDao;
import com.capgemini.domain.CarEntity;

@Repository
public class CarDaoImpl extends AbstractDao<CarEntity, Long> implements CarDao {

	@Override
	public List<CarEntity> findByBrandAndType(String brand, Long typeId) {
		TypedQuery<CarEntity> query = entityManager.createQuery("SELECT car FROM CarEntity car WHERE car.car_type_id=:car_typeId AND car.brand=:brand)",
				CarEntity.class);
		query.setParameter("car_type_id", typeId);
		query.setParameter("brand", brand);
		
		KeeperEntity keeperEntity  = query.getSingleResult();		return null;
	}
	@Override
	public KeeperEntity saveToKeeper(CarEntity carEntity, Long keeperId) {
		TypedQuery<KeeperEntity> query = entityManager.createQuery(
				"SELECT keeper FROM KeeperEntity keeper WHERE keeper.id=:keeperId)",
				KeeperEntity.class);
		query.setParameter("id", keeperId);
		
		KeeperEntity keeperEntity  = query.getSingleResult();
		keeperEntity.setCarEntity(carEntity);
		
		carEntity.getKeepersEntities().add(keeperEntity);
		entityManager.merge(carEntity);
		return entityManager.merge(keeperEntity);
	}
}
