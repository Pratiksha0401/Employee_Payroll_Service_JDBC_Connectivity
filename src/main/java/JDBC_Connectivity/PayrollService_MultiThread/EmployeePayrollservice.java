package JDBC_Connectivity.PayrollService_MultiThread;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class EmployeePayrollservice {

	public static void main(String[] args) {
		 Employee[] payrolldata = {
					new Employee("Mark","Smith",60000),
					new Employee("Jeff","Jons",40000),
					new Employee("Bill","Lu",60000),
			    };
			    
				EmployeePayrollRepo repo = new EmployeePayrollRepo();	
				Instant start = Instant.now();
				
				Arrays.stream(payrolldata).forEach(value -> {
					Runnable task = () ->{
						try {
							repo.insertMultipleRecord(value);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					};
					Thread thread = new Thread(task, value.getFirstName());
					thread.start();
				});
				
				Instant end = Instant.now();
				System.out.println("Duration of thread:" +Duration.between(start, end));
			}

	}


