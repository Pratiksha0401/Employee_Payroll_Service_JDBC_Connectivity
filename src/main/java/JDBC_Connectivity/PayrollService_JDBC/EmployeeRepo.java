package JDBC_Connectivity.PayrollService_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeRepo {
	
	public void insertRecord(EmployeeDetails details) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		Statement statement = null;
		try {
		//Step1: Load & Register Driver Class
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
		
		//Step2: Establish a MySql Connection
		 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_payroll_service", "root", "root");
		
		//Step3: Create Statement
		 statement = connection.createStatement();
		
		//Step4: Execute Query
		String query = "insert into employee_payroll(FirstName,LastName) value('"+details.getFirstName()+"','"+details.getLastName()+"')";
		int result = statement.executeUpdate(query);
		System.out.print(result + " rows affected");
		
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(connection != null) {
			statement.close();
			}
			if(statement != null) {
			connection.close();
			}
		}
		
	}
}
