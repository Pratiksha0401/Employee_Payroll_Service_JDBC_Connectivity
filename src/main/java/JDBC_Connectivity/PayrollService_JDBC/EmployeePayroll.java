package JDBC_Connectivity.PayrollService_JDBC;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmployeePayroll 
{
  
	 static final Scanner s = new Scanner(System.in);
		
		public static void main(String[] args) throws ClassNotFoundException, SQLException {
			
			System.out.println("Press 1 to Insert Data\nPress 2 to Reterive data");
			int choice = s.nextInt();
			
			switch(choice) {
			case 1:
				  insertData();
				  break;
			case 2:
				  reteriveData();
				  break;	
			}
					
		}

		private static void reteriveData() throws SQLException {
			EmployeeRepo repo = new EmployeeRepo();
			List<Employee> details = repo.findAll();
			details.forEach(System.out::println);
		}
			
		private static void insertData() throws ClassNotFoundException, SQLException {
			System.out.println("Enter FirstName");
			Employee details = new Employee();
			details.setFirstName(s.next());
			
			System.out.println("Enter LastName");
			details.setLastName(s.next());
			
			EmployeeRepo repo = new EmployeeRepo();
			repo.insertRecord(details);
				
		}
}
