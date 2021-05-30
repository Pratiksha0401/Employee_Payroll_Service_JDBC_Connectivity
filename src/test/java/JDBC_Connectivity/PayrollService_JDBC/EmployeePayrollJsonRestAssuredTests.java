package JDBC_Connectivity.PayrollService_JDBC;


import static io.restassured.RestAssured.*;
import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EmployeePayrollJsonRestAssuredTests {

	
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
	
	//@Test
	public void givenemployeeDataInJSONServer_WhenRetrieved_ShouldMatchTheCount()
	{
		Employee[] arrayOfEmps = getEmployeeList();
		EmployeeRepo employeeRepo;
		employeeRepo = new EmployeeRepo(Arrays.asList(arrayOfEmps));
		long entries = employeeRepo.countEntries();
		Assert.assertEquals(7, entries);
	}
	
	private Response addEmployeeToJSONServer(Employee employee) {
		String empJson = new Gson().toJson(employee);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(empJson);
		return request.post("/employee");
	}
	
	//@Test
	public void givenNewEmployee_whenAdded_ShouldMatch201ResponseAndCount()
	{
		Employee[] arrayOfEmps = getEmployeeList();
		EmployeeRepo employeeRepo;
		employeeRepo = new EmployeeRepo(Arrays.asList(arrayOfEmps));
		Employee employee = null;
		employee = new Employee("Gill", "Thomas", 200000);
		
		Response response = addEmployeeToJSONServer(employee);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);
	
		employee = new Gson().fromJson(response.asString(), Employee.class);
		employeeRepo.addEmployeeToPayroll(employee);
		long entries = employeeRepo.countEntries();
		Assert.assertEquals(3, entries);
	}
	
	//@Test
	public void givenListOfNewEmployee_WhenAdded_ShouldMatch201ResponseAndCount()
	{
		Employee[] arrayOfEmps = getEmployeeList();
		EmployeeRepo employeeRepo;
		employeeRepo = new EmployeeRepo(Arrays.asList(arrayOfEmps));
		
		Employee[] arrayOfEmpPayrolls = {
			new Employee( "Mark","Smith", 600000),
			new Employee("Gary","Lu", 1000000),
			new Employee("Sam", "Pichai", 200000)
		};
		for(Employee employeePayroll : arrayOfEmpPayrolls)
		{
			Response response = addEmployeeToJSONServer(employeePayroll);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(201, statusCode);
			
			employeePayroll = new Gson().fromJson(response.asString(), Employee.class);
			employeeRepo.addEmployeeToPayroll(employeePayroll);
		}
		long entries = employeeRepo.countEntries();
		Assert.assertEquals(8, entries);
	}
	
	@DataProvider(name = "dataforpost")
	public Object[][] dataforPost() {
		return new Object[][] {
			{"Nia", "Sharma",75000},
			{"Rohan", "ri",85000}
		};	
	}
	
	//@Test(dataProvider = "dataforpost")
	public void givenMultipleRecords_shouldReturn_201statusCode(String firstName, String lastName, int basicPay) {
		JSONObject request = new JSONObject();
		
		request.put("firstName", firstName);
		request.put("lastName",  lastName);
		request.put("BasicPay", basicPay);
		
		baseURI ="http://localhost";
		port = 4000;
		
		given().
		       contentType(ContentType.JSON).
		       accept(ContentType.JSON).
		       header("Content-Type", "application/json").
		       body(request.toJSONString()).
		when().
		      post("/employee").
		then().
		      statusCode(201).
		      log().all();
	}
	
	//@Test
	public void updateExistingRecord_shouldReturn_200statusCode() {
		JSONObject request = new JSONObject();
		request.put("basicPay", 50000 );
		baseURI ="http://localhost";
		port = 4000;
		given().
		       contentType(ContentType.JSON).
		       accept(ContentType.JSON).
		       header("Content-Type", "application/json").
		       body(request.toJSONString()).
		       when().
		       patch("/employee/3").
		       then().
		       statusCode(200).
		       log().all();
	}
	
	
	

	@Test
	public void deleteMultipleDatafromExistingRecord_shouldReturn_200statusCode() {
		baseURI ="http://localhost";
		port = 4000;
		
		when().
		       delete("/employee/6").
		then().
		       statusCode(200).
		       log().all();
		
	}	
	
}
