package com.capgemini.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.dao.EmployeeDao;
import com.capgemini.dao.OfficeDao;
import com.capgemini.domain.CarType;
import com.capgemini.domain.Color;
import com.capgemini.domain.CompanyPosition;
import com.capgemini.domain.EmployeeEntity;
import com.capgemini.domain.Name;
import com.capgemini.domain.OfficeEntity;
import com.capgemini.mappers.CarMapper;
import com.capgemini.mappers.EmployeeMapper;
import com.capgemini.mappers.OfficeMapper;
import com.capgemini.searchcriteria.EmployeeSearchCriteria;
import com.capgemini.types.CarTO;
import com.capgemini.types.CarTO.CarTOBuilder;
import com.capgemini.types.EmployeeTO;
import com.capgemini.types.OfficeTO;
import com.capgemini.types.OfficeTO.OfficeTOBuilder;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
//@SpringBootTest(properties = "spring.profiles.active=mysql")
public class OfficeServiceTest {

	@Autowired
	private OfficeService officeService;

	@Autowired
	private EmployeeDao employeeRepository;

	@Autowired
	private OfficeDao officeRepository;

	@Autowired
	private CarService carService;

	@Test
	public void shouldAddOffice() {

		// given
		OfficeTO testOffice = getTestOfficeTO();
		// when
		OfficeTO savedOffice = officeService.saveOffice(testOffice);
		OfficeEntity officeEntity = officeRepository.findOne(savedOffice.getId());
		OfficeTO selectedOffice = OfficeMapper.toOfficeTO(officeEntity);

		// then
		assertNotNull(savedOffice);
		assertTrue(selectedOffice.equals(savedOffice));
	}

	@Test
	public void shouldUpdateOffice() {

		// given
		OfficeTO testOffice = getTestOfficeTO();
		OfficeTO savedOffice = officeService.saveOffice(testOffice);
		// when
		OfficeTO updatedOffice = savedOffice;
		String updatedPhoneNumber = "+48222333555";
		String updatedAddress = "Długa 22, Poznań";
		updatedOffice.setPhoneNumber(updatedPhoneNumber);
		updatedOffice.setAddress(updatedAddress);

		officeService.updateOffice(updatedOffice);
		OfficeTO selectedOffice = OfficeMapper.toOfficeTO(officeRepository.findOne(savedOffice.getId()));

		// then
		assertNotNull(updatedOffice);
		assertTrue(selectedOffice.equals(updatedOffice));
	}

	@Test
	public void shouldRemoveOffice() {

		// given
		OfficeTO testOffice = getTestOfficeTO();
		// when
		OfficeTO savedOffice = officeService.saveOffice(testOffice);
		OfficeEntity officeEntity = officeRepository.findOne(savedOffice.getId());
		assertNotNull(officeEntity);
		officeService.removeOffice(savedOffice.getId());
		officeEntity = officeRepository.findOne(savedOffice.getId());
		// then
		assertEquals(null, officeEntity);
	}

	@Test
	public void shouldFindEmployeeByOffice() {

		// given
		OfficeTO testOffice = getTestOfficeTO();
		EmployeeEntity testEmployee = getTestEmployeeEntity();

		OfficeTO searchedOffice = officeService.saveOffice(testOffice);
		testEmployee.setOfficeEntity(OfficeMapper.toOfficeEntity(searchedOffice));

		EmployeeEntity searchedEmployee = employeeRepository.save(testEmployee);

		// when
		Set<EmployeeTO> selectedEmployees = officeService.findEmployeesByOffice(searchedOffice.getId());
		// then
		assertNotNull(selectedEmployees);
		assertEquals(1, selectedEmployees.size());
		assertTrue(EmployeeMapper.toEmployeeTO(searchedEmployee).equals(selectedEmployees.iterator().next()));
	}

	@Test
	public void shouldFindEmployeeByOfficeAndCarKeeped() {

		// given
		OfficeTO testOffice = getTestOfficeTO();
		EmployeeEntity testEmployee = getTestEmployeeEntity();
		CarTO testCar = getTestCarTO();

		OfficeTO searchedOffice = officeService.saveOffice(testOffice);
		testEmployee.setOfficeEntity(OfficeMapper.toOfficeEntity(searchedOffice));

		EmployeeEntity searchedEmployee = employeeRepository.save(testEmployee);

		CarTO searchedCar = carService.saveCar(testCar);
		carService.assignCarToKeeper(searchedEmployee.getId(), searchedCar.getId());

		// when
		Set<EmployeeTO> selectedEmployees = officeService.findEmployeesByOfficeAndCarKeeped(searchedOffice.getId(),
				searchedCar.getId());
		// then
		assertNotNull(selectedEmployees);
		assertEquals(1, selectedEmployees.size());
		assertTrue(EmployeeMapper.toEmployeeTO(searchedEmployee).equals(selectedEmployees.iterator().next()));
	}

	@Test
	public void shouldAddEmployeeToOffice() {

		// given
		OfficeTO testOffice = getTestOfficeTO();
		OfficeTO savedOffice = officeService.saveOffice(testOffice);

		EmployeeEntity testEmployee = getTestEmployeeEntity();
		EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);
		// when

		officeService.addEmployeeToOffice(savedOffice.getId(), savedEmployee.getId());
		OfficeEntity finalOfficeEntity = officeRepository.findOne(savedOffice.getId());
		EmployeeEntity finalEmployeeEntity = employeeRepository.findOne(testEmployee.getId());

		// then
		assertTrue(finalOfficeEntity.getEmployeeEntities().contains(finalEmployeeEntity));
		assertTrue(finalEmployeeEntity.getOfficeEntity().equals(finalOfficeEntity));
	}

	@Test
	public void shouldRemoveEmployeeFromOffice() {

		// given
		OfficeTO testOffice = getTestOfficeTO();
		OfficeTO savedOffice = officeService.saveOffice(testOffice);

		EmployeeEntity testEmployee = getTestEmployeeEntity();
		EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);

		officeService.addEmployeeToOffice(savedOffice.getId(), savedEmployee.getId());

		// when
		assertTrue(officeRepository.findOne(savedOffice.getId()).getEmployeeEntities().contains(savedEmployee));
		assertNotNull(savedEmployee.getOfficeEntity());
		officeService.removeEmployeeFromOffice(savedOffice.getId(), savedEmployee.getId());
		OfficeEntity finalOfficeEntity = officeRepository.findOne(savedOffice.getId());
		EmployeeEntity finalEmployeeEntity = employeeRepository.findOne(testEmployee.getId());

		// then
		assertTrue(!finalOfficeEntity.getEmployeeEntities().contains(savedEmployee));
		assertTrue(finalEmployeeEntity.getOfficeEntity() == null);
	}
	
	@Test
	public void shouldSearchForEmployeesByAllCriteria(){
		//given
		OfficeTO savedOffice = officeService.saveOffice(getTestOfficeTO());
		EmployeeEntity testEmployee = getTestEmployeeEntity();
		CarTO savedCar = carService.saveCar(getTestCarTO());
		testEmployee.setOfficeEntity(OfficeMapper.toOfficeEntity(savedOffice));
		EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);
		carService.assignCarToKeeper(savedEmployee.getId(), savedCar.getId());
		
		EmployeeSearchCriteria employeeCriteria = new EmployeeSearchCriteria(savedOffice.getId(),savedCar.getId(),CompanyPosition.DEALER);
		List<EmployeeSearchCriteria> listOfSearchCriteria = new LinkedList<EmployeeSearchCriteria>();
		listOfSearchCriteria.add(employeeCriteria);
		//when
		List<EmployeeEntity> foundEmployees = employeeRepository.findEmployeeByGivenCriteria(listOfSearchCriteria);
		//then
		assertEquals(1,foundEmployees.size());
		assertTrue(foundEmployees.contains(savedEmployee));
	}
	
	@Test
	public void shouldSearchForEmployeesByCarKeepedOnly(){
		//given
		EmployeeEntity testEmployee = getTestEmployeeEntity();
		CarTO savedCar = carService.saveCar(getTestCarTO());
		EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);
		carService.assignCarToKeeper(savedEmployee.getId(), savedCar.getId());
		
		EmployeeSearchCriteria employeeCriteria = new EmployeeSearchCriteria();
		employeeCriteria.setKeepedCarId(savedCar.getId());
		List<EmployeeSearchCriteria> listOfSearchCriteria = new LinkedList<EmployeeSearchCriteria>();
		listOfSearchCriteria.add(employeeCriteria);
		//when
		List<EmployeeEntity> foundEmployees = employeeRepository.findEmployeeByGivenCriteria(listOfSearchCriteria);
		//then
		assertEquals(1,foundEmployees.size());
		assertTrue(foundEmployees.contains(savedEmployee));
	}
	
	@Test
	public void shouldSearchForEmployeesByOfficeOnly(){
		//given
		OfficeTO savedOffice = officeService.saveOffice(getTestOfficeTO());
		EmployeeEntity testEmployee = getTestEmployeeEntity();
		testEmployee.setOfficeEntity(OfficeMapper.toOfficeEntity(savedOffice));
		EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);
		
		EmployeeSearchCriteria employeeCriteria = new EmployeeSearchCriteria();
		employeeCriteria.setOfficeId(savedOffice.getId());
		List<EmployeeSearchCriteria> listOfSearchCriteria = new LinkedList<EmployeeSearchCriteria>();
		listOfSearchCriteria.add(employeeCriteria);
		//when
		List<EmployeeEntity> foundEmployees = employeeRepository.findEmployeeByGivenCriteria(listOfSearchCriteria);
		//then
		assertEquals(1,foundEmployees.size());
		assertTrue(foundEmployees.contains(savedEmployee));
	}
	
	@Test
	public void shouldSearchForEmployeesWithNoCriteria(){
		//given
		EmployeeEntity testEmployee = getTestEmployeeEntity();
		EmployeeEntity savedEmployee = employeeRepository.save(testEmployee);
		
		EmployeeSearchCriteria employeeCriteria = new EmployeeSearchCriteria();
		List<EmployeeSearchCriteria> listOfSearchCriteria = new LinkedList<EmployeeSearchCriteria>();
		listOfSearchCriteria.add(employeeCriteria);
		//when
		List<EmployeeEntity> foundEmployees = employeeRepository.findEmployeeByGivenCriteria(listOfSearchCriteria);
		//then
		assertEquals(1,foundEmployees.size());
		assertTrue(foundEmployees.contains(savedEmployee));
	}

	private OfficeTO getTestOfficeTO() {
		return new OfficeTOBuilder().withAddress("Warszawska 555, Poznań").withPhoneNumber("+48123456789").build();

	}

	private EmployeeEntity getTestEmployeeEntity() {
		Date testDate = new Date();
		EmployeeEntity testEmployee = new EmployeeEntity();
		Name testName = new Name("Adam","Maciejewski");
		testEmployee.setName(testName);
		testEmployee.setCompanyPosition(CompanyPosition.DEALER);
		testEmployee.setBirthDate(testDate);
		return testEmployee;
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

}
