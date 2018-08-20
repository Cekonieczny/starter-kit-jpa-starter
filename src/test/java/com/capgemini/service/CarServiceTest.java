package com.capgemini.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.CarDao;
import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.OfficeDao;
import com.capgemini.domain.CarEntity;
import com.capgemini.domain.CarType;
import com.capgemini.domain.Color;
import com.capgemini.domain.CompanyPosition;
import com.capgemini.domain.CustomerEntity;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.LoanEntity;
import com.capgemini.domain.Name;
import com.capgemini.domain.OfficeEntity;
import com.capgemini.mappers.CarMapper;
import com.capgemini.types.CarTO;
import com.capgemini.types.CarTO.CarTOBuilder;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
//@SpringBootTest(properties = "spring.profiles.active=mysql")
public class CarServiceTest {
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private CarService carService;

	@Autowired
	private CarDao carRepository;

	@Autowired
	private EmployeeDao employeeRepository;

	@Autowired
	private OfficeDao officeRepository;

	@Test
	public void shouldSaveCar() {

		// given
		CarTO testCar = getTestCarTO();
		// when
		CarTO savedCar = carService.saveCar(testCar);
		CarEntity carEntity = carRepository.findOne(savedCar.getId());
		CarTO selectedCar = CarMapper.toCarTO(carEntity);

		// then
		assertNotNull(savedCar);
		assertTrue(selectedCar.equals(savedCar));
		/*assertEquals(savedCar.getBrand(), carEntity.getBrand());
		assertEquals(savedCar.getCarType(), carEntity.getCarType());
		assertEquals(savedCar.getColor(), carEntity.getColor());
		assertEquals(savedCar.getEngineCapacity(), carEntity.getEngineCapacity());
		assertEquals(savedCar.getHorsepower(), carEntity.getHorsepower());
		assertEquals(savedCar.getMileage(), carEntity.getMileage());
		assertEquals(savedCar.getModel(), carEntity.getModel());
		assertEquals(savedCar.getYearOfManufacture(), carEntity.getYearOfManufacture());*/
	}

	@Test
	public void shouldUpdateCar() {

		// given
		CarTO testCar = getTestCarTO();
		CarTO savedCar = carService.saveCar(testCar);
		// when
		CarTO updatedCar = savedCar;
		String updatedBrand = "updatedBrand";
		int updatedHorsepower = 210;
		updatedCar.setBrand(updatedBrand);
		updatedCar.setHorsepower(updatedHorsepower);

		carService.updateCar(updatedCar);
		CarEntity carEntity = carRepository.findOne(savedCar.getId());
		CarTO selectedCar = CarMapper.toCarTO(carEntity);

		// then
		assertNotNull(updatedCar);
		assertTrue(selectedCar.equals(updatedCar));
		/*assertEquals(carEntity.getBrand(), updatedCar.getBrand());
		assertEquals(carEntity.getCarType(), updatedCar.getCarType());
		assertEquals(carEntity.getColor(), updatedCar.getColor());
		assertEquals(carEntity.getEngineCapacity(), updatedCar.getEngineCapacity());
		assertEquals(carEntity.getHorsepower(), updatedCar.getHorsepower());
		assertEquals(carEntity.getMileage(), updatedCar.getMileage());
		assertEquals(carEntity.getModel(), updatedCar.getModel());
		assertEquals(carEntity.getYearOfManufacture(), updatedCar.getYearOfManufacture());*/
	}

	@Test
	public void shouldRemoveCar() {

		// given
		CarTO testCar = getTestCarTO();
		// when
		CarTO savedCar = carService.saveCar(testCar);
		CarEntity carEntity = carRepository.findOne(savedCar.getId());
		assertNotNull(carEntity);
		carService.removeCar(savedCar.getId());
		carEntity = carRepository.findOne(savedCar.getId());
		// then
		assertEquals(null, carEntity);
	}

	@Test
	public void shouldFindCarsByBrandAndType() {

		// given
		String searchedBrand = "Opel";
		CarType searchedType = CarType.SEDAN;
		CarTO testCar = getTestCarTO();
		CarTO testCar2 = new CarTOBuilder().withBrand(searchedBrand).withCarType(searchedType).withColor(Color.RED)
				.withHorsepower(123).withEngineCapacity(400).withMileage(300000).withModel("Insignia")
				.withYearOfManufacture(2004).build();
		CarTO testCar3 = new CarTOBuilder().withBrand(searchedBrand).withCarType(searchedType).withColor(Color.BLUE)
				.withHorsepower(123).withEngineCapacity(400).withMileage(300000).withModel("Astra")
				.withYearOfManufacture(2004).build();
		CarTO testCar4 = new CarTOBuilder().withBrand("Skoda").withCarType(CarType.SEDAN).withColor(Color.RED)
				.withHorsepower(123).withEngineCapacity(400).withMileage(300000).withModel("Octavia")
				.withYearOfManufacture(2004).build();
		carService.saveCar(testCar);
		carService.saveCar(testCar2);
		carService.saveCar(testCar3);
		carService.saveCar(testCar4);
		// when
		Set<CarTO> selectedCars = carService.findCarByBrandAndType(searchedBrand, searchedType);
		// then
		assertNotNull(selectedCars);
		assertEquals(2, selectedCars.size());
		assertEquals(searchedBrand, selectedCars.iterator().next().getBrand());
		assertEquals(searchedBrand, selectedCars.iterator().next().getBrand());
		assertEquals(searchedType, selectedCars.iterator().next().getCarType());
		assertEquals(searchedType, selectedCars.iterator().next().getCarType());
	}

	@Test
	public void shouldAssignCarToKeeper() {

		// given
		CarTO testCar = getTestCarTO();
		CarTO savedCar = carService.saveCar(testCar);

		EmployeeEntity testEmployee = getTestEmployeeEntity();

		EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);
		// when

		carService.assignCarToKeeper(savedEmployee.getId(), savedCar.getId());
		CarEntity finalCarEntity = carRepository.findOne(savedCar.getId());
		EmployeeEntity finalEmployeeEntity = employeeRepository.findOne(testEmployee.getId());

		// then
		assertTrue(finalCarEntity.getEmployeeEntities().contains(finalEmployeeEntity));
		assertTrue(finalEmployeeEntity.getCarEntities().contains(finalCarEntity));
	}
	
	@Test
	public void shouldFindCarByKeeper(){
		// given
				CarTO testCar = getTestCarTO();
				CarTO savedCar = carService.saveCar(testCar);
				
				EmployeeEntity savedEmployee = employeeRepository.save(getTestEmployeeEntity());

				carService.assignCarToKeeper(savedEmployee.getId(), savedCar.getId());
				
		// when
				
				Set<CarTO> selectedCarSet = carService.findCarByKeeper(savedEmployee.getId());
		
		//then 
				assertEquals(1,selectedCarSet.size());
				assertTrue(selectedCarSet.contains(savedCar));
	}
	
	@Test
	public void shouldRemoveAssociatedLoansOnRemoveCar() {

		// given
		Set<LoanEntity> loanEntities = getTestLoanEntities();
		LoanEntity loan1 = loanEntities.iterator().next();
		LoanEntity loan2 = loanEntities.iterator().next();
		// when
		CarEntity carEntity = carRepository.findOne(loan1.getCarEntity().getId());
		assertNotNull(carEntity);
		assertNotNull(em.find(loan1.getClass(), loan1.getId()));
		assertNotNull(em.find(loan2.getClass(), loan2.getId()));
		carService.removeCar(loan1.getCarEntity().getId());
		carEntity = carRepository.findOne(loan1.getCarEntity().getId());
		assertEquals(null, carEntity);
		// then
		assertEquals(null, em.find(loan1.getClass(), loan1.getId()));
		assertEquals(null, em.find(loan2.getClass(), loan2.getId()));
	}

	private Set<LoanEntity> getTestLoanEntities(){
		CarTO savedCar = carService.saveCar(getTestCarTO());
		Date testDate = new Date();
		
		OfficeEntity office1 = new OfficeEntity();
		office1.setAddress("officeAddress1");
		office1.setPhoneNumber("+48555666777");
		OfficeEntity office2 = new OfficeEntity();
		office2.setAddress("officeAddress2");
		office2.setPhoneNumber("+48222666777");
		office1 = officeRepository.save(office1);
		office2 = officeRepository.save(office2);
		

		CustomerEntity customer1 = new CustomerEntity();
		customer1.setAddress("customerAddres1");
		customer1.setBirthDate(testDate);
		customer1.setCreditCardNumber("1234567891012145");
		customer1.setEmail("email1");
		Name testName = new Name("Adam","Maciejewski");
		customer1.setName(testName);
		em.persist(customer1);
		
		CustomerEntity customer2 = new CustomerEntity();
		customer2.setAddress("customerAddres2");
		customer2.setBirthDate(testDate);
		customer2.setCreditCardNumber("1234537891012145");
		customer2.setEmail("email2");
		Name testName2 = new Name("Maciej","Adamski");
		customer2.setName(testName2);
		em.persist(customer2);
		
		LoanEntity loan1 = new LoanEntity();
		loan1.setCustomerEntity(customer1);
		loan1.setCarEntity(CarMapper.toCarEntity(savedCar));
		loan1.setDateFrom(testDate);
		loan1.setDateTo(testDate);
		loan1.setOfficeEntityFrom(office1);
		loan1.setOfficeEntityTo(office2);
		loan1.setPrice(1234);
		em.persist(loan1);
		
		LoanEntity loan2 = new LoanEntity();
		loan2.setCustomerEntity(customer2);
		loan2.setCarEntity(CarMapper.toCarEntity(savedCar));
		loan2.setDateFrom(testDate);
		loan2.setDateTo(testDate);
		loan2.setOfficeEntityFrom(office2);
		loan2.setOfficeEntityTo(office1);
		loan2.setPrice(1223);
		em.persist(loan2);
		
		Set<LoanEntity> loanEntities = new HashSet<LoanEntity>();
		loanEntities.add(loan1);
		loanEntities.add(loan2);
		
		CarEntity testCarEntity = carRepository.findOne(savedCar.getId());
		testCarEntity.setLoanEntities(loanEntities);
		
		loan1.setCarEntity(testCarEntity);
		loan2.setCarEntity(testCarEntity);	
		
		em.merge(loan1);
		em.merge(loan2);
		em.merge(testCarEntity);
		
		return loanEntities;
	}
	
	private CarTO getTestCarTO() {
		int horsepower = 200;
		int engineCapacity = 100;
		int mileage = 2000;
		CarTO testCar = new CarTOBuilder().withBrand("Skoda").withCarType(CarType.KOMBI).withColor(Color.RED)
				.withHorsepower(horsepower).withEngineCapacity(engineCapacity).withMileage(mileage).withModel("Fabia")
				.withYearOfManufacture(2001).build();
		return testCar;
	}

	private EmployeeEntity getTestEmployeeEntity(){
		OfficeEntity testOffice = new OfficeEntity();
		testOffice.setAddress("Poznanska 123, Kalisz");
		testOffice.setPhoneNumber("+48222333444");
		OfficeEntity savedOffice = officeRepository.save(testOffice);
		
		Date testDate = new Date();
		EmployeeEntity testEmployee = new EmployeeEntity();
		Name testName = new Name("Adam","Maciejewski");
		testEmployee.setName(testName);
		testEmployee.setCompanyPosition(CompanyPosition.DEALER);
		testEmployee.setOfficeEntity(savedOffice);
		testEmployee.setBirthDate(testDate);
		return testEmployee;
	}
}
