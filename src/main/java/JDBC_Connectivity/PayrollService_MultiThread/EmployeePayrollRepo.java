package JDBC_Connectivity.PayrollService_MultiThread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeePayrollRepo {
public void insertMultipleRecord(Employee value) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepstatement = null;
		
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_payroll_service", "root", "root");
			
		try {
			connection.setAutoCommit(false);
			String query = "insert into employee_payroll(FirstName, LastName, basic_pay) value(?,?,?)";
			prepstatement = connection.prepareStatement(query);
			prepstatement.setString(1, value.firstName);
			prepstatement.setString(2, value.lastName);
			prepstatement.setFloat(3, value.basicPay);
			
			prepstatement.executeUpdate();
			connection.commit();

		}catch (SQLException e) {
			e.printStackTrace();
				connection.rollback();
				System.out.println("Rolled back Successfully");
			}catch (Exception e1) {
				e1.printStackTrace();
			}
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
