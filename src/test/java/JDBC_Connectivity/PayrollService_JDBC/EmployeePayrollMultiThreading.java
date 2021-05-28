package JDBC_Connectivity.PayrollService_JDBC;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;


public class EmployeePayrollMultiThreading 
{
	@Test
	public void given6Employees_ShouldMatchEmployeeEntries() throws SQLException, ClassNotFoundException 
	{
		Employee[] arrayOfEmps = {
				new Employee(0, "Jeff Bezos", "M", 100000),
				new Employee(0, "Bill Gates", "M", 200000),
				new Employee(0, "Mark Zuckerberg", "M", 300000),
				new Employee(0, "Sunder", "M", 600000),
				new Employee(0, "Mukesh", "M", 100000),
				new Employee(0, "Anil", "M", 200000)
		};
		EmployeeRepo employeeRepo = new EmployeeRepo();
		employeeRepo.findAll();
		Instant start = Instant.now();
		employeeRepo.addEmployeeToPayroll(Arrays.asList(arrayOfEmps));
		Instant end = Instant.now();
		System.out.println("Duration without Thread: "+ Duration.between(start, end));
		Assert.assertEquals(6, employeeRepo.countEntries());
	}
}
