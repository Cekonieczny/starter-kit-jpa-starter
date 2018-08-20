package com.capgemini.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CAR")
public class CarEntity extends AbstractTimestampEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private Integer mileage;
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
	
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Color color;
	

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private CarType carType;

	@OneToMany(mappedBy = "carEntity",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	private Set<LoanEntity> loanEntities = new HashSet<LoanEntity>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="KEEPER",joinColumns={ @JoinColumn(name = "employee_id") },inverseJoinColumns = { @JoinColumn(name = "car_id")})
	private Set<EmployeeEntity> employeeEntities = new HashSet<EmployeeEntity>();
	
	// for hibernate
	public CarEntity() {
	}
	
	public CarEntity(Long id, Integer mileage, String brand, String model, Color color,
			CarType carType) {
		this.id = id;
		this.mileage = mileage;
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.carType = carType;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getMileage() {
		return mileage;
	}


	public void setMileage(Integer mileage) {
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color= color;
	}

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	public Set<EmployeeEntity> getEmployeeEntities() {
		return employeeEntities;
	}

	public void setEmployeeEntities(Set<EmployeeEntity> employeeEntities) {
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

	public int getYearOfManufacture() {
		return yearOfManufacture;
	}

	public void setYearOfManufacture(int yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}

	public Set<LoanEntity> getLoanEntities() {
		return loanEntities;
	}

	public void setLoanEntities(Set<LoanEntity> loanEntities) {
		this.loanEntities = loanEntities;
	}
	
	
	
	
}
