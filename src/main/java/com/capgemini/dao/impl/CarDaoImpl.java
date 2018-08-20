package com.capgemini.dao.impl;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.dao.CarDao;
import com.capgemini.domain.CarEntity;
import com.capgemini.domain.CarType;

@Repository
public class CarDaoImpl extends AbstractDao<CarEntity, Long> implements CarDao {

	@Override
	public Set<CarEntity> findByBrandAndType(String brand, CarType carType) {
		TypedQuery<CarEntity> query = entityManager.createQuery("SELECT car FROM CarEntity car WHERE car.carType=:carType AND car.brand=:brand)",
				CarEntity.class);
		query.setParameter("carType", carType);
		query.setParameter("brand", brand);
		return query.getResultList().stream().collect(Collectors.toSet());
	}

	@Override
	public Set<CarEntity> findCarByEmployee(Long employeeId) {
		TypedQuery<CarEntity> query = entityManager.createQuery("SELECT c FROM CarEntity c JOIN c.employeeEntities e WHERE e.id=:employeeId)",
				CarEntity.class);
		query.setParameter("employeeId", employeeId);
		return query.getResultList().stream().collect(Collectors.toSet());
	}

	@Override
	public Set<CarEntity> findCarLoanedByMoreThanTenDistinctCustomers() {
		
		TypedQuery<CarEntity> query = entityManager.createQuery("SELECT c FROM CarEntity c JOIN c.loanEntities l GROUP BY c.id HAVING (count(DISTINCT l.customerEntity)) > 10",
				CarEntity.class);
		return query.getResultList().stream().collect(Collectors.toSet());
	}

	@Override
	public Long countNumberOfCarsLoanedInACertainPeriodOfTime(Date dateFrom, Date dateTo) {
		TypedQuery<Long> query = entityManager.createQuery("SELECT count(c) FROM CarEntity c JOIN c.loanEntities l WHERE l.dateFrom <= :dateTo AND l.dateTo >= :dateFrom",
				Long.class);
		query.setParameter("dateTo", dateTo);
		query.setParameter("dateFrom", dateFrom);
		return query.getSingleResult();
	}
	
}
