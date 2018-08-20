package com.capgemini.dao.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.domain.CompanyPosition;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.OfficeEntity;
import com.capgemini.searchcriteria.EmployeeSearchCriteria;

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
	@Override
	public List<EmployeeEntity> findEmployeeByGivenCriteria(List<EmployeeSearchCriteria> params) {
		String queryBody = "SELECT e FROM EmployeeEntity e ";
		TypedQuery<EmployeeEntity> query = entityManager.createQuery(queryBody, EmployeeEntity.class);

		for (EmployeeSearchCriteria param : params) {

			if (param.getOfficeId() != null) {
				queryBody = queryBody + "JOIN e.officeEntity o ON o.id=:officeId";
			}
			if (param.getKeepedCarId() != null) {
				queryBody = queryBody + " JOIN e.carEntities c ON c.id=:carId";
			}
			if (param.getCompanyPosition() != null) {
				queryBody = queryBody + " WHERE e.companyPosition=:companyPosition";	
			}
			
			query = entityManager.createQuery(queryBody, EmployeeEntity.class);
			
			if (param.getOfficeId() != null) {
				query.setParameter("officeId", param.getOfficeId());
			}
			if (param.getKeepedCarId() != null) {
				query.setParameter("carId", param.getKeepedCarId());
			}
			if (param.getCompanyPosition() != null) {
				query.setParameter("companyPosition", param.getCompanyPosition());
			}
		}
		return query.getResultList();
	}

}
