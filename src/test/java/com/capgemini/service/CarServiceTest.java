package com.capgemini.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
// @SpringBootTest(properties = "spring.profiles.active=mysql")
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
	}

	@Test
	public void shouldUpdateCar() {

		// given
		CarTO testCar = getTestCarTO();
		CarEntity savedCar = carRepository.save(CarMapper.toCarEntity(testCar));
		// when
		CarEntity updatedCar = savedCar;
		String updatedBrand = "updatedBrand";
		int updatedHorsepower = 210;
		updatedCar.setBrand(updatedBrand);
		updatedCar.setHorsepower(updatedHorsepower);

		carService.updateCar(CarMapper.toCarTO(updatedCar));
		CarEntity selectedCar = carRepository.findOne(savedCar.getId());

		// then
		assertNotNull(updatedCar);
		assertTrue(selectedCar.equals(updatedCar));
	}

	@Test
	public void shouldRemoveCar() {

		// given
		CarTO testCar = getTestCarTO();
		CarEntity savedCar = carRepository.save(CarMapper.toCarEntity(testCar));
		// when
		assertNotNull(savedCar);
		carService.removeCar(savedCar.getId());
		CarEntity removedCar = carRepository.findOne(savedCar.getId());
		// then
		assertEquals(null, removedCar);
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
		carRepository.save(CarMapper.toCarEntity(testCar));
		carRepository.save(CarMapper.toCarEntity(testCar2));
		carRepository.save(CarMapper.toCarEntity(testCar3));
		carRepository.save(CarMapper.toCarEntity(testCar4));
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
		CarEntity savedCar = carRepository.save(CarMapper.toCarEntity(testCar));

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
	public void shouldFindCarByKeeper() {
		// given
		CarTO testCar = getTestCarTO();
		CarEntity savedCar = carRepository.save(CarMapper.toCarEntity(testCar));
		
		CarTO testCar2 = getTestCarTO();
		CarEntity savedCar2 = carRepository.save(CarMapper.toCarEntity(testCar2));

		EmployeeEntity savedEmployee = employeeRepository.save(getTestEmployeeEntity());
		EmployeeEntity savedEmployee2 = employeeRepository.save(getTestEmployeeEntity());

		carService.assignCarToKeeper(savedEmployee.getId(), savedCar.getId());
		carService.assignCarToKeeper(savedEmployee2.getId(), savedCar2.getId());

		// when

		Set<CarTO> selectedCarSet = carService.findCarByKeeper(savedEmployee.getId());

		// then
		assertEquals(1, selectedCarSet.size());
		assertTrue(selectedCarSet.contains(CarMapper.toCarTO(savedCar)));
	}

	@Test
	public void shouldRemoveAssociatedLoansOnRemoveCar() {

		// given
		Date testDate = new Date();
		CarEntity savedCar = insertTestCar();
		CustomerEntity savedCustomer = insertTestCustomer();

		LoanEntity loan1 = insertTestLoan(testDate, testDate, savedCar, savedCustomer);
		LoanEntity loan2 = insertTestLoan(testDate, testDate, savedCar, savedCustomer);
		Set<LoanEntity> loanEntities = savedCar.getLoanEntities();
		loanEntities.add(loan1);
		loanEntities.add(loan2);

		savedCar.setLoanEntities(loanEntities);
		carRepository.update(savedCar);

		// when
		assertNotNull(savedCar);
		assertNotNull(em.find(loan1.getClass(), loan1.getId()));
		assertNotNull(em.find(loan2.getClass(), loan2.getId()));
		carService.removeCar(savedCar.getId());
		CarEntity carEntity = carRepository.findOne(savedCar.getId());
		assertEquals(null, carEntity);
		// then
		assertEquals(null, em.find(loan1.getClass(), loan1.getId()));
		assertEquals(null, em.find(loan2.getClass(), loan2.getId()));
	}

	@Test
	public void shouldNotRemoveLoanAssociatedEntitiesOnRemoveCar() {

		// given
		Date testDate = new Date();
		CarEntity savedCar = insertTestCar();
		CustomerEntity savedCustomer = insertTestCustomer();

		LoanEntity loan1 = insertTestLoan(testDate, testDate, savedCar, savedCustomer);
		LoanEntity loan2 = insertTestLoan(testDate, testDate, savedCar, savedCustomer);
		
		OfficeEntity officeEntity1 = loan1.getOfficeEntityFrom();
		OfficeEntity officeEntity2 = loan2.getOfficeEntityFrom();
		
		Set<LoanEntity> loanEntities = savedCar.getLoanEntities();
		loanEntities.add(loan1);
		loanEntities.add(loan2);

		savedCar.setLoanEntities(loanEntities);
		carRepository.update(savedCar);

		// when
		assertNotNull(savedCar);
		assertNotNull(em.find(loan1.getClass(), loan1.getId()));
		assertNotNull(em.find(loan2.getClass(), loan2.getId()));
		carService.removeCar(savedCar.getId());
		CarEntity carEntity = carRepository.findOne(savedCar.getId());
		assertEquals(null, carEntity);
		// then
		assertNotNull(em.find(savedCustomer.getClass(), savedCustomer.getId()));
		assertNotNull(em.find(officeEntity1.getClass(), officeEntity1.getId()));
		assertNotNull(em.find(officeEntity2.getClass(), officeEntity2.getId()));
	}

	@Test
	public void shouldFindCarLoanedByMoreThanTenDistinctCustomers() {
		// given
		Date testDate = new Date();

		CarEntity savedCar = insertTestCar();
		for (int i = 0; i < 11; i++) {
			insertTestLoan(testDate, testDate, savedCar, insertTestCustomer());
		}

		// when
		Set<CarEntity> searchedCars = carRepository.findCarLoanedByMoreThanTenDistinctCustomers();
		// then
		assertTrue(searchedCars.contains(savedCar));
	}
	
	@Test
	public void shouldNotFindCarLoanedByLessThanTenDistinctCustomers() {
		// given
		Date testDate = new Date();

		CarEntity savedCar = insertTestCar();
		for (int i = 0; i < 10; i++) {
			insertTestLoan(testDate, testDate, savedCar, insertTestCustomer());
		}

		// when
		Set<CarEntity> searchedCars = carRepository.findCarLoanedByMoreThanTenDistinctCustomers();
		// then
		assertTrue(!searchedCars.contains(savedCar));
	}

	@Test
	public void shouldNotFindCarsLoanedByTheSameCustomerMoreThanTenTimes() {
		// given
		Date testDate = new Date();
		CarEntity savedCar = insertTestCar();
		CustomerEntity savedCustomer = insertTestCustomer();
		for (int i = 0; i < 11; i++) {
			insertTestLoan(testDate, testDate, savedCar, savedCustomer);
		}

		// when
		Set<CarEntity> searchedCars = carRepository.findCarLoanedByMoreThanTenDistinctCustomers();
		// then
		assertTrue(!searchedCars.contains(savedCar));
	}

	@Test
	public void shouldcountNumberOfCarsLoanedWhenLoanedBeforeDateFromAndReturnedBeforeDateTo() throws ParseException {
		// given
		Date wrongDate = new Date();
		Date loanDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2013-12-12 00:00:00");
		Date returnDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-01-01 00:00:00");
		Long presetCount = 5L;
		for (int i = 0; i < presetCount; i++) {
			insertTestLoan(loanDate, returnDate, insertTestCar(), insertTestCustomer());
		}
		for (int i = 0; i < presetCount; i++) {
			insertTestLoan(wrongDate, wrongDate, insertTestCar(), insertTestCustomer());
		}
		// when
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-01-01 00:00:00");
		Date dateTo = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-02-02 00:00:00");
		Long count = carRepository.countNumberOfCarsLoanedInACertainPeriodOfTime(dateFrom, dateTo);
		// then
		assertTrue(presetCount.equals(count));
	}

	@Test
	public void shouldcountNumberOfCarsLoanedWhenLoanedBeforeDateFromAndReturnedOnDateTo() throws ParseException {
		// given
		Date wrongDate = new Date();
		Date loanDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2013-12-12 00:00:00");
		Date returnDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-01-01 00:00:00");
		Long presetCount = 5L;
		for (int i = 0; i < presetCount; i++) {
			insertTestLoan(loanDate, returnDate, insertTestCar(), insertTestCustomer());
		}
		for (int i = 0; i < presetCount; i++) {
			insertTestLoan(wrongDate, wrongDate, insertTestCar(), insertTestCustomer());
		}
		// when
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-01-01 00:00:00");
		Date dateTo = returnDate;
		Long count = carRepository.countNumberOfCarsLoanedInACertainPeriodOfTime(dateFrom, dateTo);
		// then
		assertTrue(presetCount.equals(count));
	}

	@Test
	public void shouldcountNumberOfCarsLoanedWhenLoanedAfterDateFromAndReturnedBeforeDateTo() throws ParseException {
		// given
		Date wrongDate = new Date();
		Date loanDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-01-01 00:00:00");
		Date returnDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-01-01 00:00:00");
		Long presetCount = 5L;
		for (int i = 0; i < presetCount; i++) {
			insertTestLoan(loanDate, returnDate, insertTestCar(), insertTestCustomer());
		}
		for (int i = 0; i < presetCount; i++) {
			insertTestLoan(wrongDate, wrongDate, insertTestCar(), insertTestCustomer());
		}
		// when
		Date dateFrom = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2013-12-12 00:00:00");
		Date dateTo = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-02-02 00:00:00");
		Long count = carRepository.countNumberOfCarsLoanedInACertainPeriodOfTime(dateFrom, dateTo);
		// then
		assertTrue(presetCount.equals(count));
	}

	@Test
	public void shouldcountNumberOfCarsLoanedWhenLoanedOnDateFromAndReturnedBeforeDateTo() throws ParseException {
		// given
		Date wrongDate = new Date();
		Date loanDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-01-01 00:00:00");
		Date returnDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-01-01 00:00:00");
		Long presetCount = 5L;
		for (int i = 0; i < presetCount; i++) {
			insertTestLoan(loanDate, returnDate, insertTestCar(), insertTestCustomer());
		}
		for (int i = 0; i < presetCount; i++) {
			insertTestLoan(wrongDate, wrongDate, insertTestCar(), insertTestCustomer());
		}
		// when
		Date dateFrom = loanDate;
		Date dateTo = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-02-02 00:00:00");
		Long count = carRepository.countNumberOfCarsLoanedInACertainPeriodOfTime(dateFrom, dateTo);
		// then
		assertTrue(presetCount.equals(count));
	}

	@Test
	public void shouldcountNumberOfCarsLoanedWhenLoanedOnDateFromAndReturnedOnDateTo() throws ParseException {
		// given
		Date wrongDate = new Date();
		Date loanDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-01-01 00:00:00");
		Date returnDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2014-01-01 00:00:00");
		Long presetCount = 5L;
		for (int i = 0; i < presetCount; i++) {
			insertTestLoan(loanDate, returnDate, insertTestCar(), insertTestCustomer());
		}
		for (int i = 0; i < presetCount; i++) {
			insertTestLoan(wrongDate, wrongDate, insertTestCar(), insertTestCustomer());
		}
		// when
		Date dateFrom = loanDate;
		Date dateTo = returnDate;
		Long count = carRepository.countNumberOfCarsLoanedInACertainPeriodOfTime(dateFrom, dateTo);
		// then
		assertTrue(presetCount.equals(count));
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

	private EmployeeEntity getTestEmployeeEntity() {
		OfficeEntity testOffice = new OfficeEntity();
		testOffice.setAddress("Poznanska 123, Kalisz");
		testOffice.setPhoneNumber("+48222333444");
		OfficeEntity savedOffice = officeRepository.save(testOffice);

		Date testDate = new Date();
		EmployeeEntity testEmployee = new EmployeeEntity();
		Name testName = new Name("Adam", "Maciejewski");
		testEmployee.setName(testName);
		testEmployee.setCompanyPosition(CompanyPosition.DEALER);
		testEmployee.setOfficeEntity(savedOffice);
		testEmployee.setBirthDate(testDate);
		return testEmployee;
	}

	private CustomerEntity getTestCustomerEntity() {
		Date testDate = new Date();
		CustomerEntity testCustomer = new CustomerEntity();
		testCustomer.setAddress("customerAddres1");
		testCustomer.setBirthDate(testDate);
		testCustomer.setCreditCardNumber("1234567891012145");
		testCustomer.setEmail("email1");
		Name testName = new Name("Adam", "Maciejewski");
		testCustomer.setName(testName);
		return testCustomer;
	}

	private LoanEntity getTestLoanEntity(Date dateFrom, Date dateTo, CarEntity carEntity,
			CustomerEntity customerEntity) {

		OfficeEntity office1 = new OfficeEntity();
		office1.setAddress("officeAddress1");
		office1.setPhoneNumber("+48555666777");
		OfficeEntity office2 = new OfficeEntity();
		office2.setAddress("officeAddress2");
		office2.setPhoneNumber("+48222666777");
		office1 = officeRepository.save(office1);
		office2 = officeRepository.save(office2);

		LoanEntity testLoan = new LoanEntity();
		testLoan.setCustomerEntity(customerEntity);
		testLoan.setCarEntity(carEntity);
		testLoan.setDateFrom(dateFrom);
		testLoan.setDateTo(dateTo);
		testLoan.setOfficeEntityFrom(office1);
		testLoan.setOfficeEntityTo(office2);
		testLoan.setPrice(1234);
		return testLoan;
	}

	private LoanEntity insertTestLoan(Date dateFrom, Date dateTo, CarEntity carEntity, CustomerEntity customerEntity) {
		LoanEntity testLoan = getTestLoanEntity(dateFrom, dateTo, carEntity, customerEntity);
		em.persist(testLoan);
		return testLoan;
	}

	private CarEntity insertTestCar() {
		CarEntity testCarEntity = CarMapper.toCarEntity(getTestCarTO());
		em.persist(testCarEntity);
		return testCarEntity;
	}

	private CustomerEntity insertTestCustomer() {
		CustomerEntity testCustomer = getTestCustomerEntity();
		em.persist(testCustomer);
		return testCustomer;
	}

}
