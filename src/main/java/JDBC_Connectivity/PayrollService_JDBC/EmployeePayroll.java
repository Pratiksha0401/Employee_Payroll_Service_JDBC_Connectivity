package JDBC_Connectivity.PayrollService_JDBC;

import java.sql.SQLException;
import java.util.Scanner;

public class EmployeePayroll 
{
  
    	public static void main(String[] args) throws ClassNotFoundException, SQLException {
			Scanner s = new Scanner(System.in);
			
			System.out.println("Enter FirstName");
			EmployeeDetails details = new EmployeeDetails();
			details.setFirstName(s.next());
			
			System.out.println("Enter LastName");
			details.setLastName(s.next());
			
			EmployeeRepo repo = new EmployeeRepo();
			repo.insertRecord(details);
				
		
    }
}
