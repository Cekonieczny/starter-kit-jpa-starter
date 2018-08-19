package com.capgemini.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Embedded
	private Name name;
	@Column(nullable = false, length = 60)
	private String address;
	@Column(nullable = false, length = 60)
	private String email;
	@Column(nullable = false)
	private Date birthDate;
	@Column(length = 16)
	private String creditCardNumber;
	@Column(length = 12)
	private String phoneNumber;

	// for hibernate
	public CustomerEntity() {
	}

	public CustomerEntity(Long id, Name name, String address, String email, Date birthDate, String creditCardNumber,
			String phoneNumber) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.email = email;
		this.birthDate = birthDate;
		this.creditCardNumber = creditCardNumber;
		this.phoneNumber = phoneNumber;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
