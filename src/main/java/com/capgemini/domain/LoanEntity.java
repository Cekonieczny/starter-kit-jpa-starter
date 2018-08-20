package com.capgemini.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "LOAN")
public class LoanEntity extends AbstractTimestampEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OFFICE_FROM_ID", nullable = false)
	private OfficeEntity officeEntityFrom;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OFFICE_TO_ID", nullable = false)
	private OfficeEntity officeEntityTo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAR_ID", nullable = false)
	private CarEntity carEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	private CustomerEntity customerEntity;
	
	@Column(nullable = false)
	private Date dateFrom;
	private Date dateTo;
	private int price;
	

	// for hibernate
	public LoanEntity() {
	}


	public LoanEntity(Long id, OfficeEntity officeFrom, OfficeEntity officeTo, CarEntity carId,
			CustomerEntity customerId, Date dateFrom, Date dateTo, int price) {
		this.id = id;
		this.officeEntityFrom = officeFrom;
		this.officeEntityTo = officeTo;
		this.carEntity = carId;
		this.customerEntity = customerId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.price = price;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public OfficeEntity getOfficeEntityFrom() {
		return officeEntityFrom;
	}


	public void setOfficeEntityFrom(OfficeEntity officeEntityFrom) {
		this.officeEntityFrom = officeEntityFrom;
	}


	public OfficeEntity getOfficeEntityTo() {
		return officeEntityTo;
	}


	public void setOfficeEntityTo(OfficeEntity officeEntityTo) {
		this.officeEntityTo = officeEntityTo;
	}


	public CarEntity getCarEntity() {
		return carEntity;
	}


	public void setCarEntity(CarEntity carEntity) {
		this.carEntity = carEntity;
	}


	public CustomerEntity getCustomerEntity() {
		return customerEntity;
	}


	public void setCustomerEntity(CustomerEntity customerEntity) {
		this.customerEntity = customerEntity;
	}


	public Date getDateFrom() {
		return dateFrom;
	}


	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}


	public Date getDateTo() {
		return dateTo;
	}


	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}
}
