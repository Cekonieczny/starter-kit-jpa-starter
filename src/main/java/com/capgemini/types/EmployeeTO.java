package com.capgemini.types;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.capgemini.domain.CompanyPosition;
import com.capgemini.domain.Name;

public class EmployeeTO {

	private Long id;
	private Name name;
	private Date birthDate;
	private OfficeTO officeTO;
	private CompanyPosition companyPosition;
	private Set<CarTO> carTOs = new HashSet<>();

	public EmployeeTO(Long id, Name name, Date birthDate, OfficeTO officeTO, CompanyPosition companyPosition,
			Set<CarTO> carTOs) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.officeTO = officeTO;
		this.companyPosition = companyPosition;
		this.carTOs = carTOs;
	}

	public EmployeeTO(Name name, Date birthDate, OfficeTO officeTO, CompanyPosition companyPosition,
			Set<CarTO> carTOs) {
		this.name = name;
		this.birthDate = birthDate;
		this.officeTO = officeTO;
		this.companyPosition = companyPosition;
		this.carTOs = carTOs;
	}

	public static class EmployeeTOBuilder {

		private Long id;
		private Name name;
		private Date birthDate;
		private OfficeTO officeTO;
		private CompanyPosition companyPosition;
		private Set<CarTO> carTOs;

		public EmployeeTOBuilder() {
		}

		public EmployeeTOBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public EmployeeTOBuilder withName(Name name) {
			this.name = name;
			return this;
		}

		public EmployeeTOBuilder withBirthDate(Date birthDate) {
			this.birthDate = birthDate;
			return this;
		}

		public EmployeeTOBuilder withOfficeTO(OfficeTO officeTO) {
			this.officeTO = officeTO;
			return this;
		}

		public EmployeeTOBuilder withCompanyPosition(CompanyPosition companyPosition) {
			this.companyPosition = companyPosition;
			return this;
		}

		public EmployeeTOBuilder withCarTOs(Set<CarTO> carTOs) {
			this.carTOs = carTOs;
			return this;
		}

		public EmployeeTO build() {
			checkBeforeBuild(name, birthDate, companyPosition);
			return new EmployeeTO(id, name, birthDate, officeTO, companyPosition, carTOs);
		}

		private void checkBeforeBuild(Name name, Date birthDate, CompanyPosition companyPosition) {
			if (name.getFirstName() == null || name.getFirstName().isEmpty() || name.getLastName() == null
					|| name.getLastName().isEmpty() || birthDate == null || companyPosition == null) {
				throw new RuntimeException("Incorrect employee to be created");
			}

		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public OfficeTO getOfficeTO() {
		return officeTO;
	}

	public void setOfficeTO(OfficeTO officeTO) {
		this.officeTO = officeTO;
	}

	public CompanyPosition getCompanyPosition() {
		return companyPosition;
	}

	public void setCompanyPosition(CompanyPosition companyPosition) {
		this.companyPosition = companyPosition;
	}

	public Set<CarTO> getCarTOs() {
		return carTOs;
	}

	public void setCarTOs(Set<CarTO> carTOs) {
		this.carTOs = carTOs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((officeTO == null) ? 0 : officeTO.hashCode());
		result = prime * result + ((carTOs == null) ? 0 : carTOs.hashCode());
		result = prime * result + ((companyPosition == null) ? 0 : companyPosition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeTO other = (EmployeeTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (officeTO == null) {
			if (other.officeTO != null)
				return false;
		} else if (!officeTO.equals(other.officeTO))
			return false;
		if (companyPosition == null) {
			if (other.companyPosition != null)
				return false;
		} else if (!companyPosition.equals(other.companyPosition))
			return false;
		if (carTOs == null) {
			if (other.carTOs != null)
				return false;
		} else if (!carTOs.equals(other.carTOs))
			return false;
		return true;
	}
}
