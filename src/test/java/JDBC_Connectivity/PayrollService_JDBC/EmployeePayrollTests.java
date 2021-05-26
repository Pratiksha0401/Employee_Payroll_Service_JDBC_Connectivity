package JDBC_Connectivity.PayrollService_JDBC;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EmployeePayrollTests {

	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws SQLException {
		EmployeeRepo repo = new EmployeeRepo(); 
		List<Employee> empData = repo.findAll();
		Assert.assertEquals(4, empData.size());
	}
	
	@Before
	public void setup()
	{
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}
	
	public Employee[] getEmployeeList()
	{
		Response response = RestAssured.get("/employees");
		System.out.println("Employee Payroll Entries In JSONServer:\n" + response.asString());	
		Employee[] arrayOfEmps = new Gson().fromJson(response.asString(), Employee[].class);
		return arrayOfEmps;
	}
	@Test
	public void givenemployeeDataInJSONServer_WhenRetrieved_ShouldMatchTheCount()
	{
		Employee[] arrayOfEmps = getEmployeeList();
		EmployeeRepo employeeRepo;
		employeeRepo = new EmployeeRepo(Arrays.asList(arrayOfEmps));
		long entries = employeeRepo.countEntries();
		Assert.assertEquals(2, entries);
	}
}
