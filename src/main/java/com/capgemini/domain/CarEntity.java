package com.capgemini.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "CAR")
public class CarEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, length = 30)
	private String mileage;
	@Column(nullable = false, length = 30)
	private String brand;
	@Column(nullable = false, length = 30)
	private String model;
	@Column(nullable = false)
	private int engineCapacity;
	@Column(nullable = false)
	private int horsepower;
	@Column(nullable = false, length = 4)
	private int yearOfManufacture;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COLOR_ID", nullable = false)
	private ColorEntity colorEntity;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "CAR_TYPE_ID", nullable = false)
	private CarTypeEntity carTypeEntity;

	@ManyToMany
	@JoinTable(name="KEEPER",joinColumns={ @JoinColumn(name = "employee_id") },inverseJoinColumns = { @JoinColumn(name = "car_id")})
	private Collection<EmployeeEntity> employeeEntities;
	
	// for hibernate
	public CarEntity() {
	}
	
	public CarEntity(Long id, String mileage, String brand, String model, ColorEntity colorEntity,
			CarTypeEntity carTypeEntity) {
		this.id = id;
		this.mileage = mileage;
		this.brand = brand;
		this.model = model;
		this.colorEntity = colorEntity;
		this.carTypeEntity = carTypeEntity;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMileage() {
		return mileage;
	}


	public void setMileage(String mileage) {
		this.mileage = mileage;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}

	public ColorEntity getColorEntity() {
		return colorEntity;
	}

	public void setColorEntity(ColorEntity colorEntity) {
		this.colorEntity = colorEntity;
	}

	public CarTypeEntity getCarTypeEntity() {
		return carTypeEntity;
	}

	public void setCarTypeEntity(CarTypeEntity carTypeEntity) {
		this.carTypeEntity = carTypeEntity;
	}

	public Collection<EmployeeEntity> getEmployeeEntities() {
		return employeeEntities;
	}

	public void setEmployeeEntities(Collection<EmployeeEntity> employeeEntities) {
		this.employeeEntities = employeeEntities;
	}

	public int getEngineCapacity() {
		return engineCapacity;
	}

	public void setEngineCapacity(int engineCapacity) {
		this.engineCapacity = engineCapacity;
	}

	public int getHorsepower() {
		return horsepower;
	}

	public void setHorsepower(int horsepower) {
		this.horsepower = horsepower;
	}
	
}
