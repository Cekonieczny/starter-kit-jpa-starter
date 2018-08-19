package com.capgemini.dao.impl;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.domain.EmployeeEntity;

@Repository
public class EmployeeDaoImpl extends AbstractDao<EmployeeEntity, Long> implements EmployeeDao {

	@Override
	public Set<EmployeeEntity> findByOffice(Long officeId) {
		TypedQuery<EmployeeEntity> query = entityManager.createQuery(
				"SELECT e FROM EmployeeEntity e JOIN e.officeEntity o WHERE o.id=:officeId)", EmployeeEntity.class);
		query.setParameter("officeId", officeId);
		return query.getResultList().stream().collect(Collectors.toSet());
	}

	@Override
	public Set<EmployeeEntity> findByOfficeAndCarKeeped(Long officeId, Long carId) {
		TypedQuery<EmployeeEntity> query = entityManager.createQuery(
				"SELECT e FROM EmployeeEntity e JOIN e.officeEntity o JOIN e.carEntities c WHERE o.id=:officeId AND c.id=:carId)",
				EmployeeEntity.class);
		query.setParameter("officeId", officeId);
		query.setParameter("carId", carId);
		return query.getResultList().stream().collect(Collectors.toSet());
	}
}
