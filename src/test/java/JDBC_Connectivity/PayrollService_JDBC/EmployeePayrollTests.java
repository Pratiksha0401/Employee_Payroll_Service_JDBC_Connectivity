package JDBC_Connectivity.PayrollService_JDBC;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class EmployeePayrollTests {

	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
		EmployeeRepo repo = new EmployeeRepo(); 
		List<Employee> empData = repo.findAll();
		Assert.assertEquals(7, empData.size());
	}
}
