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
import io.restassured.specification.RequestSpecification;

public class EmployeePayrollJsonTests {

	
	@Before
	public void setup()
	{
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 4000;
	}
	
	public Employee[] getEmployeeList()
	{
		Response response = RestAssured.get("/employee");
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
		Assert.assertEquals(3, entries);
	}
	
	private Response addEmployeeToJSONServer(Employee employee) {
		String empJson = new Gson().toJson(employee);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(empJson);
		return request.post("/employee");
	}
	
	@Test
	public void givenNewEmployee_whenAdded_ShouldMatch201ResponseAndCount()
	{
		Employee[] arrayOfEmps = getEmployeeList();
		EmployeeRepo employeeRepo;
		employeeRepo = new EmployeeRepo(Arrays.asList(arrayOfEmps));
		Employee employee = null;
		employee = new Employee(0, "Gill", "Thomas", 200000);
		
		Response response = addEmployeeToJSONServer(employee);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);
	
		employee = new Gson().fromJson(response.asString(), Employee.class);
		employeeRepo.addEmployeeToPayroll(employee);
		long entries = employeeRepo.countEntries();
		Assert.assertEquals(3, entries);
	}
}
