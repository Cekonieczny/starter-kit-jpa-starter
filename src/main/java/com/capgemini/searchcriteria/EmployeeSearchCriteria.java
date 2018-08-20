package com.capgemini.searchcriteria;

import com.capgemini.domain.CompanyPosition;

public class EmployeeSearchCriteria {
	private Long officeId;
	private Long keepedCarId;
	CompanyPosition companyPosition;

	public EmployeeSearchCriteria(){
		
	}
	public EmployeeSearchCriteria(Long officeId, Long keepedCarId, CompanyPosition companyPosition) {
		this.officeId = officeId;
		this.keepedCarId = keepedCarId;
		this.companyPosition = companyPosition;
	}
	public Long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(Long officeId) {
		this.officeId = officeId;
	}
	public Long getKeepedCarId() {
		return keepedCarId;
	}
	public void setKeepedCarId(Long keepedCarId) {
		this.keepedCarId = keepedCarId;
	}
	public CompanyPosition getCompanyPosition() {
		return companyPosition;
	}
	public void setCompanyPosition(CompanyPosition companyPosition) {
		this.companyPosition = companyPosition;
	}
	
}
