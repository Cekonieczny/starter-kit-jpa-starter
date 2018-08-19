package com.capgemini.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.impl.EmployeeDaoImpl;
import com.capgemini.dao.impl.OfficeDaoImpl;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.OfficeEntity;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.mappers.OfficeMapper;
import com.capgemini.service.OfficeService;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.OfficeTO;
@Service
public class OfficeServiceImpl implements OfficeService {

	@Autowired
	private OfficeDaoImpl officeRepository;

	@Autowired
	private EmployeeDaoImpl employeeRepository;

	@Override
	public Set<EmployeeTO> findEmployeesByOffice(Long officeId) {
		return EmployeeMapper.map2TOs(employeeRepository.findByOffice(officeId));
	}

	@Override
	public Set<EmployeeTO> findEmployeesByOfficeAndCarKeeped(Long officeId, Long carId) {
		return EmployeeMapper.map2TOs(employeeRepository.findByOfficeAndCarKeeped(officeId, carId));
	}

	@Override
	public OfficeTO saveOffice(OfficeTO officeTO) {
		OfficeEntity officeEntity = (OfficeMapper.toOfficeEntity(officeTO));
		return OfficeMapper.toOfficeTO(officeRepository.save(officeEntity));
	}

	@Override
	public OfficeTO updateOffice(OfficeTO officeTO) {
		OfficeEntity officeEntity = (OfficeMapper.toOfficeEntity(officeTO));
		return OfficeMapper.toOfficeTO(officeRepository.update(officeEntity));
	}

	@Override
	public void removeOffice(Long officeId) {
		officeRepository.delete(officeId);
	}

	@Override
	public EmployeeTO addEmployeeToOffice(Long officeId, Long employeeId) {
		OfficeEntity officeEntity = officeRepository.findOne(officeId);
		EmployeeEntity employeeEntity = employeeRepository.findOne(employeeId);

		employeeEntity.setOfficeEntity(officeEntity);
		officeEntity.getEmployeeEntities().add(employeeEntity);

		officeRepository.update(officeEntity);
		employeeRepository.update(employeeEntity);
		return EmployeeMapper.toEmployeeTO(employeeEntity);
	}

	@Override
	public EmployeeTO removeEmployeeFromOffice(Long officeId, Long employeeId) {
		OfficeEntity officeEntity = officeRepository.findOne(officeId);
		EmployeeEntity employeeEntity = employeeRepository.findOne(employeeId);

		officeEntity.getEmployeeEntities().remove(employeeEntity);
		employeeEntity.setOfficeEntity(null);

		officeRepository.update(officeEntity);
		employeeRepository.update(employeeEntity);
		return EmployeeMapper.toEmployeeTO(employeeEntity);
	}

}
