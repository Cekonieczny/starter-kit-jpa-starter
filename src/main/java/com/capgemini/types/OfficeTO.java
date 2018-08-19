package com.capgemini.types;

public class OfficeTO {
	private Long id;
	private String address;
	private String phoneNumber;

	public OfficeTO(Long id, String address, String phoneNumber) {
		this.id = id;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public OfficeTO(String address, String phoneNumber) {
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public static class OfficeTOBuilder {
		private Long id;
		private String address;
		private String phoneNumber;

		public OfficeTOBuilder() {
		}

		public OfficeTOBuilder withAddress(String address) {
			this.address = address;
			return this;
		}

		public OfficeTOBuilder withPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public OfficeTOBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public OfficeTO build() {
			checkBeforeBuild(address, phoneNumber);
			return new OfficeTO(id, address, phoneNumber);
		}

		private void checkBeforeBuild(String address, String phoneNumber) {
			if (address == null || address.isEmpty() || phoneNumber == null || phoneNumber.isEmpty()) {
				throw new RuntimeException("Incorrect office to be created");
			}

		}

	}

	@Override
	public String toString() {
		return "OfficeTO [address=" + address + ", phone number=" + phoneNumber + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
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
		OfficeTO other = (OfficeTO) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber!= null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}
}
