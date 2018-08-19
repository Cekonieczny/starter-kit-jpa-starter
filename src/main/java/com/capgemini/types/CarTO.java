package com.capgemini.types;

import com.capgemini.domain.CarType;
import com.capgemini.domain.Color;

public class CarTO {

	private Long id;
	private Integer mileage;
	private String brand;
	private String model;
	private int engineCapacity;
	private int horsepower;
	private int yearOfManufacture;
	private Color color;
	private CarType carType;

	public CarTO(Long id, Integer mileage, String brand, String model, int engineCapacity, int horsepower,
			int yearOfManufacture, Color color, CarType carType) {
		this.id = id;
		this.mileage = mileage;
		this.brand = brand;
		this.model = model;
		this.engineCapacity = engineCapacity;
		this.horsepower = horsepower;
		this.yearOfManufacture = yearOfManufacture;
		this.color = color;
		this.carType = carType;
	}
	
	public CarTO(Integer mileage, String brand, String model, int engineCapacity, int horsepower,
			int yearOfManufacture, Color color, CarType carType) {
		this.mileage = mileage;
		this.brand = brand;
		this.model = model;
		this.engineCapacity = engineCapacity;
		this.horsepower = horsepower;
		this.yearOfManufacture = yearOfManufacture;
		this.color = color;
		this.carType = carType;
	}

	public static class CarTOBuilder {

		private Long id;
		private Integer mileage;
		private String brand;
		private String model;
		private int engineCapacity;
		private int horsepower;
		private int yearOfManufacture;
		private Color color;
		private CarType carType;

		public CarTOBuilder() {
		}
		
		
		public CarTOBuilder withId (Long id) {
			this.id = id;
			return this;
		}

		public CarTOBuilder withMileage(Integer mileage) {
			this.mileage = mileage;
			return this;
		}

		public CarTOBuilder withBrand(String brand) {
			this.brand = brand;
			return this;
		}

		public CarTOBuilder withModel(String model) {
			this.model = model;
			return this;
		}

		public CarTOBuilder withEngineCapacity(int engineCapacity) {
			this.engineCapacity = engineCapacity;
			return this;
		}

		public CarTOBuilder withYearOfManufacture(int yearOfManufacture) {
			this.yearOfManufacture = yearOfManufacture;
			return this;
		}
		public CarTOBuilder withHorsepower(int horsepower) {
			this.horsepower = horsepower;
			return this;
		}
		public CarTOBuilder withColor(Color color) {
			this.color = color;
			return this;
		}

		public CarTOBuilder withCarType(CarType carType) {
			this.carType = carType;
			return this;
		}

		public CarTO build() {
			checkBeforeBuild(mileage, brand, model, engineCapacity, horsepower, yearOfManufacture, color, carType);
			return new CarTO(id, mileage, brand, model, engineCapacity, horsepower, yearOfManufacture, color, carType);
		}

		private void checkBeforeBuild(Integer mileage, String brand, String model, int engineCapacity, int horsepower,
				int yearOfManufacture, Color color, CarType carType) {
			if (mileage == null || brand == null || brand.isEmpty() || model == null||model.isEmpty()||engineCapacity == 0
					|| horsepower == 0 || yearOfManufacture == 0 || color == null || carType == null) {
				throw new RuntimeException("Incorrect car to be created");
			}

		}

		
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mileage == null) ? 0 : mileage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + engineCapacity;
		result = prime * result + horsepower;
		result = prime * result + yearOfManufacture;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((carType == null) ? 0 : carType.hashCode());
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
		CarTO other = (CarTO) obj;
		if (mileage == null) {
			if (other.mileage!= null)
				return false;
		} else if (!mileage.equals(other.mileage))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (carType == null) {
			if (other.carType != null)
				return false;
		} else if (!carType.equals(other.carType))
			return false;
		if (engineCapacity != other.engineCapacity)
			return false;
		if (horsepower != other.horsepower)
			return false;
		if (yearOfManufacture != other.yearOfManufacture)
			return false;
		return true;
	}
}
