package com.capgemini.domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "KEEPER")
public class KeeperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false, updatable = false)
	private EmployeeEntity employeeEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CAR_ID", nullable = false, updatable = false)
	private CarEntity carEntity;
	
}