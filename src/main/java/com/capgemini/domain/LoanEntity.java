package com.capgemini.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "LOAN")
public class LoanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    /*@JoinTable(name = "OFFICE",
            joinColumns = {@JoinColumn(name = "OFFICE_ID", nullable = false)})*/
	private OfficeEntity officeFrom;
	
	@ManyToOne(fetch = FetchType.LAZY)
    /*@JoinTable(name = "OFFICE",
            joinColumns = {@JoinColumn(name = "OFFICE_ID", nullable = false)})*/
	private OfficeEntity officeTo;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "CAR",
            joinColumns = {@JoinColumn(name = "CAR_ID", nullable = false)})
	private CarEntity carId;

	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "CUSTOMER",
            joinColumns = {@JoinColumn(name = "CUSTOMER_ID", nullable = false)})
	private CustomerEntity customerId;
	
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
		this.officeFrom = officeFrom;
		this.officeTo = officeTo;
		this.carId = carId;
		this.customerId = customerId;
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


	public OfficeEntity getOfficeFrom() {
		return officeFrom;
	}


	public void setOfficeFrom(OfficeEntity officeFrom) {
		this.officeFrom = officeFrom;
	}


	public OfficeEntity getOfficeTo() {
		return officeTo;
	}


	public void setOfficeTo(OfficeEntity officeTo) {
		this.officeTo = officeTo;
	}


	public CarEntity getCarId() {
		return carId;
	}


	public void setCarId(CarEntity carId) {
		this.carId = carId;
	}


	public CustomerEntity getCustomerId() {
		return customerId;
	}


	public void setCustomerId(CustomerEntity customerId) {
		this.customerId = customerId;
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
