package JDBC_Connectivity.PayrollService_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeePayrollService {
	
	//initiatilizing queries for both tables
		public static final String INSERT_EMPLOYEE_QUERY = "insert into employee_details (EmployeeID, Name, gender, Mobile_Number , Address) values (?,?,?,?,?)";

		public static final String INSERT_PAYROLL_QUERY = "insert into employee_payroll (ID,FirstName, LastName, basic_pay) values (?,?,?,?)";
		
	
	public static void main(String[] args) throws SQLException {
		
		Connection connection = null;
		try {
			//loading and registering the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Step2: Establish a MySql Connection
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_payroll_service", "root", "root");
			
			//setting AutoCommit false
			connection.setAutoCommit(false);
			
			//adding new employee details using transaction
			insertEmployeeDetail(connection,15,"Mayuri","F","132546","Bhandara");
			insertPayrollData(connection,15,"Mayuri","Makade",40000);
			
			//now commit transaction
			connection.commit();
			
		}catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				System.out.println("JDBC Transaction rolled back successfully");
			} catch (SQLException e1) {
				System.out.println("SQLException in rollback"+e.getMessage());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}


	private static void insertPayrollData(Connection connection, int ID, String firstName, String lastName, int basic_pay) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(INSERT_PAYROLL_QUERY);
		stmt.setInt(1, ID);
		stmt.setString(2, firstName);
		stmt.setString(3, lastName);
		stmt.setInt(4, basic_pay);
		
		
		stmt.executeUpdate();

		System.out.println("Payroll Data inserted successfully for ID=" + ID);
		stmt.close();
		
	}


	private static void insertEmployeeDetail(Connection connection, int EmployeeID, String Name, String gender,
			String Mobile_No, String address) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(INSERT_EMPLOYEE_QUERY);
		stmt.setInt(1, EmployeeID);
		stmt.setString(2, Name);
		stmt.setString(3, gender);
		stmt.setString(4, Mobile_No);
		stmt.setString(5, address);

		stmt.executeUpdate();

		System.out.println("Employee Data inserted successfully for ID=" + EmployeeID);
		stmt.close();
		//cascadingdelete();
	}
	
	public static void cascadingdelete() throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepstatement = null;
		
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_payroll_service", "root", "root");
			
			String query = "Delete from employee_payroll where Id =15";
			
			prepstatement = connection.prepareStatement(query);
			prepstatement.executeUpdate();
			System.out.println(" Record deleted!");
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
		 	if(connection != null) {
		 		connection.close();
			}
			if(prepstatement != null) {
				prepstatement.close();
			}
		 }
		
	}

}
