package JDBC_Connectivity.PayrollService_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
	
	public void insertRecord(Employee details) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		Statement statement = null;
		try {
		//Step1: Load & Register Driver Class
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
		
		//Step2: Establish a MySql Connection
		 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_payroll_service", "root", "root");
		
		 connection.setAutoCommit(false);
		 
		//Step3: Create Statement
		 statement = connection.createStatement();
		
		//Step4: Execute Query
		String query = "insert into employee_payroll(FirstName,LastName) value('"+details.getFirstName()+"','"+details.getLastName()+"')";
		int result = statement.executeUpdate(query);
		connection.commit();
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
	
	public List<Employee> findAll() throws SQLException {
		List<Employee> details=new ArrayList<>();
		
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
		String query =" select * from employee_payroll";
		ResultSet resultset = statement.executeQuery(query);
	
		while(resultset.next()) {
			Employee info = new Employee();
			
			int id=resultset.getInt(1);
			info.setId(id);
			
			String name = resultset.getString(2);
			info.setFirstName(name);
			
			String lastname = resultset.getString(3);
			info.setLastName(lastname);
			
			float pay =resultset.getFloat(4);
			info.setBasicPay(pay);
			
			details.add(info);
		}
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
		return details;
	}
	
	public void updatedata(int id, float basicPay) throws SQLException {
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
		String query ="Update employee_payroll set basic_pay="+basicPay+"where Id="+id+"";
		statement.executeUpdate(query);
		System.out.print("Records Updated!");
		
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
