package com.capgemini.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.domain.EmployeeEntity;
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
	public List<EmployeeEntity> findEmployeeByGivenCriteria(EmployeeSearchCriteria param) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<EmployeeEntity> cq = cb.createQuery(EmployeeEntity.class);
		Root<EmployeeEntity> employee = cq.from(EmployeeEntity.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

			if (param.getOfficeId() != null) {
				predicates.add((employee.join("officeEntity").get("id").in(param.getOfficeId())));
			}
			if (param.getKeepedCarId() != null) {
				predicates.add((employee.join("carEntities").get("id").in(param.getKeepedCarId())));
			}
			if (param.getCompanyPosition() != null) {
				predicates.add(cb.equal(employee.get("companyPosition"), param.getCompanyPosition()));
			}
		
		cq.select(employee).where(predicates.toArray(new Predicate[] {}));
		return entityManager.createQuery(cq).getResultList();
	}

}
