package com.capgemini.mappers;

import java.util.Set;
import java.util.stream.Collectors;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.OfficeEntity;
import com.capgemini.types.CarTO;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.EmployeeTO.EmployeeTOBuilder;
import com.capgemini.types.OfficeTO;

public class EmployeeMapper {
	public static EmployeeTO toEmployeeTO(EmployeeEntity employeeEntity) {
		if (employeeEntity == null)
			return null;
		
		OfficeTO officeTO = OfficeMapper.toOfficeTO(employeeEntity.getOfficeEntity());
		Set<CarTO> carTOs= CarMapper.map2TOs(employeeEntity.getCarEntities());

		return new EmployeeTOBuilder().withBirthDate(employeeEntity.getBirthDate()).withCarTOs(carTOs)
				.withCompanyPosition(employeeEntity.getCompanyPosition()).withId(employeeEntity.getId())
				.withName(employeeEntity.getName()).withOfficeTO(officeTO)
				.build();
	}

	public static EmployeeEntity toEmployeeEntity(EmployeeTO employeeTO) {
		if (employeeTO == null)
			return null;

		OfficeEntity officeEntity = OfficeMapper.toOfficeEntity(employeeTO.getOfficeTO());

		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setBirthDate(employeeTO.getBirthDate());
		employeeEntity.setCompanyPosition(employeeTO.getCompanyPosition());
		employeeEntity.setId(employeeTO.getId());
		employeeEntity.setName(employeeTO.getName());
		employeeEntity.setOfficeEntity(officeEntity);

		CarMapper.map2Entities(employeeTO.getCarTOs());

		employeeEntity.setCarEntities(CarMapper.map2Entities(employeeTO.getCarTOs()));

		return employeeEntity;
	}

	public static Set<EmployeeTO> map2TOs(Set<EmployeeEntity> employeeEntities) {
		return employeeEntities.stream().map(EmployeeMapper::toEmployeeTO).collect(Collectors.toSet());
	}

	public static Set<EmployeeEntity> map2Entities(Set<EmployeeTO> EmployeeTOs) {
		return EmployeeTOs.stream().map(EmployeeMapper::toEmployeeEntity).collect(Collectors.toSet());
	}
}
