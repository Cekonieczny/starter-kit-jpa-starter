package com.capgemini.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, length = 30)
	private String name;
	@Column(nullable = false, length = 30)
	private String surname;
	@Column(nullable = false)
	private Date birthDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COLOR_ID", nullable = false)
	private ColorEntity colorEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OFFICE_ID", nullable = false)
	private OfficeEntity officeEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_POSITION_ID", nullable = false)
	private CompanyPositionEntity companyPositionEntity;
	
	@OneToMany()
	private Collection<KeeperEntity> keepersEntities;

	// for hibernate
	public EmployeeEntity() {
	}

	public EmployeeEntity(Long id, String name, String surname, Date birthDate, ColorEntity colorEntity,
			OfficeEntity officeEntity, CompanyPositionEntity companyPositionEntity) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.colorEntity = colorEntity;
		this.officeEntity = officeEntity;
		this.companyPositionEntity = companyPositionEntity;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public ColorEntity getColorEntity() {
		return colorEntity;
	}

	public void setColorEntity(ColorEntity colorEntity) {
		this.colorEntity = colorEntity;
	}

	public OfficeEntity getOfficeEntity() {
		return officeEntity;
	}

	public void setOfficeEntity(OfficeEntity officeEntity) {
		this.officeEntity = officeEntity;
	}

	public CompanyPositionEntity getCompanyPositionEntity() {
		return companyPositionEntity;
	}

	public void setCompanyPositionEntity(CompanyPositionEntity companyPositionEntity) {
		this.companyPositionEntity = companyPositionEntity;
	}

	
}
