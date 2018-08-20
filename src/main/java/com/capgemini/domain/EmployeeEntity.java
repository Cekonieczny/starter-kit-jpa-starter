package com.capgemini.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity extends AbstractTimestampEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Embedded
	private Name name;
	@Column(nullable = false)
	private Date birthDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OFFICE_ID")
	private OfficeEntity officeEntity;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private CompanyPosition companyPosition;

	@ManyToMany(mappedBy = "employeeEntities", fetch = FetchType.LAZY)
	private Set<CarEntity> carEntities = new HashSet<CarEntity>();

	// for hibernate
	public EmployeeEntity() {
	}

	public EmployeeEntity(Long id, Name name, Date birthDate, OfficeEntity officeEntity,
			CompanyPosition companyPosition, Set<CarEntity> carEntities) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.officeEntity = officeEntity;
		this.companyPosition = companyPosition;
		this.carEntities = carEntities;
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

	public OfficeEntity getOfficeEntity() {
		return officeEntity;
	}

	public void setOfficeEntity(OfficeEntity officeEntity) {
		this.officeEntity = officeEntity;
	}

	public CompanyPosition getCompanyPosition() {
		return companyPosition;
	}

	public void setCompanyPosition(CompanyPosition companyPosition) {
		this.companyPosition = companyPosition;
	}

	public Set<CarEntity> getCarEntities() {
		return carEntities;
	}

	public void setCarEntities(Set<CarEntity> carEntities) {
		this.carEntities = carEntities;
	}
}
