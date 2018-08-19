package com.capgemini.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.capgemini.domain.OfficeEntity;
import com.capgemini.types.OfficeTO;
import com.capgemini.types.OfficeTO.OfficeTOBuilder;

public class OfficeMapper {
	public static OfficeTO toOfficeTO(OfficeEntity officeEntity) {
		if (officeEntity == null)
			return null;

		return new OfficeTOBuilder().withAddress(officeEntity.getAddress()).withId(officeEntity.getId())
				.withPhoneNumber(officeEntity.getPhoneNumber()).build();
	}

	public static OfficeEntity toOfficeEntity(OfficeTO officeTO) {
		if (officeTO == null)
			return null;
		OfficeEntity officeEntity = new OfficeEntity();
		officeEntity.setAddress(officeTO.getAddress());
		officeEntity.setId(officeTO.getId());
		officeEntity.setPhoneNumber(officeTO.getPhoneNumber());
		return officeEntity;
	}

	public static List<OfficeTO> map2TOs(List<OfficeEntity> officeEntities) {
		return officeEntities.stream().map(OfficeMapper::toOfficeTO).collect(Collectors.toList());
	}

	public static List<OfficeEntity> map2Entities(List<OfficeTO> officeTOs) {
		return officeTOs.stream().map(OfficeMapper::toOfficeEntity).collect(Collectors.toList());
	}
}
