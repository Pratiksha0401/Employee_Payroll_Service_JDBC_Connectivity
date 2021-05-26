package JDBC_Connectivity.PayrollService_JDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
	
	private List<Employee> empList;


	public EmployeeRepo(List<Employee> empList) {
		this();
		this.empList=empList;
	}

	public EmployeeRepo() {
		// TODO Auto-generated constructor stub
	}

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
		String query = "insert into employee_payroll(FirstName,LastName,basic_pay) value('"+details.getFirstName()+"','"+details.getLastName()+"','"+details.getBasicPay()+"')";
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
		PreparedStatement prestatement = null;
		try {
		//Step1: Load & Register Driver Class
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
		
		//Step2: Establish a MySql Connection
		 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_payroll_service", "root", "root");
		
		//Step3: Create Statement		
		String query =" select * from employee_payroll ";
		prestatement = connection.prepareStatement(query);
		
		//Step4: Execute Query
		ResultSet resultset = prestatement.executeQuery();
	
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
			
			String start=resultset.getString(9);
			info.setStart_Date(start);
			
			details.add(info);
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			if(connection != null) {
			prestatement.close();
			}
			if(prestatement != null) {
			connection.close();
			}
		}
		return details;
	}
	
	public void updatedata(int id, float basicPay) throws SQLException {
		Connection con = null;
		PreparedStatement prestatement = null;
		try {
		
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_payroll_service", "root", "root");
		
		String query ="Update employee_payroll set basic_pay=? where Id=?";
		prestatement = con.prepareStatement(query);
		prestatement.setFloat(1, basicPay);
		prestatement.setInt(2, id);
		prestatement.executeUpdate();
		 System.out.print("Records Updated!");
		
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
			prestatement.close();
			}
			if(prestatement != null) {
			con.close();
			}
		}
	}
	
	
	public void deletedata(int id , String firstName) throws SQLException {
		Connection con = null;
		PreparedStatement prestatement = null;
		try {
		
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_payroll_service", "root", "root");
		
		String query ="delete from employee_payroll where FirstName=? or Id=?";
		prestatement = con.prepareStatement(query);
		prestatement.setString(1, firstName);
		prestatement.setInt(2, id);
		prestatement.executeUpdate();
		 System.out.print("Records Deleted!");
		
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
			prestatement.close();
			}
			if(prestatement != null) {
			con.close();
			}
		}
	}
	
	public List<Employee> findAllForParticularDateRange() throws SQLException {
		
		List<Employee> details=new ArrayList<>();
		Connection connection = null;
		PreparedStatement prepstatement = null;
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_payroll_service", "root", "root");
			
			String query1 ="Select * from employee_payroll where Start_Date between Cast('2020-03-10' as date) and date(now()); ";
			prepstatement = connection.prepareStatement(query1);
			
			ResultSet resultset1 = prepstatement.executeQuery();
			
			while(resultset1.next()) {
				Employee info = new Employee();
				
				int id=resultset1.getInt(1);
				info.setId(id);
				
				String name = resultset1.getString(2);
				info.setFirstName(name);
				
				String lastname = resultset1.getString(3);
				info.setLastName(lastname);
				
				float pay =resultset1.getFloat(4);
				info.setBasicPay(pay);
				
				String start=resultset1.getString(9);
				info.setStart_Date(start);
				
				details.add(info);
			}
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
			return details;		
	}
	
	
	public void usedatabaseFunction() throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepstatement = null;
		
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_payroll_service", "root", "root");
			//sum by gender='F'
			String query1="select sum(basic_pay),gender from employee_payroll emp1 , employee_details emp2 where"
					+ "  emp1.id=emp2.EmployeeID or gender='F' group by gender" ;
			prepstatement=connection.prepareStatement(query1);
			ResultSet result1 = prepstatement.executeQuery();
			result1.next();
			String gender=result1.getString(2);
			String sum1 = result1.getString(1);
			System.out.println(gender +" Gender having Sum of BasicPay of Employees: "+sum1);		
			
			//sum by gender 'M'
			String query2="select sum(basic_pay),gender from employee_payroll emp1 , employee_details emp2 where"
					+ "  emp1.id=emp2.EmployeeID or gender='M' group by gender" ;
			prepstatement=connection.prepareStatement(query2);
			ResultSet result2 = prepstatement.executeQuery();
			result2.next();
			String gender1=result2.getString(2);
			String sum2 = result2.getString(1);
			System.out.println(gender1 +" Gender having Sum of BasicPay of Employees: "+sum2);					
			
			//avg of all
			String query3="select avg(basic_pay) from employee_payroll emp1 , employee_details emp2 where"
					+ "  emp1.id=emp2.EmployeeID " ;
			prepstatement=connection.prepareStatement(query3);
			ResultSet result3 = prepstatement.executeQuery();
			result3.next();
			String avg = result3.getString(1);
			System.out.println("Average of BasicPay of all Employees: "+avg);					
			
			//min of all
			String query4="select min(basic_pay) from employee_payroll emp1 , employee_details emp2 where"
					+ "  emp1.id=emp2.EmployeeID " ;
			prepstatement=connection.prepareStatement(query4);
			ResultSet result4 = prepstatement.executeQuery();
			result4.next();
			String min = result4.getString(1);
			System.out.println("Minimum BasicPay from all Employees: "+min);					
			
			
			//min of all
			String query5="select max(basic_pay) from employee_payroll emp1 , employee_details emp2 where"
					+ "  emp1.id=emp2.EmployeeID " ;
			prepstatement=connection.prepareStatement(query5);
			ResultSet result5 = prepstatement.executeQuery();
			result5.next();
			String max = result5.getString(1);
			System.out.println("Maximum BasicPay from all Employees: "+max);					
			
			//count of all
			String query6="select count(*) from employee_payroll " ;
			prepstatement=connection.prepareStatement(query6);
			ResultSet result6 = prepstatement.executeQuery();
			result6.next();
			int count = result6.getInt(1);
			System.out.println("Count of all Employees: "+count);
			
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
	
	
public void alterTable_EmployeePayroll() throws SQLException {
		
		Connection connection = null;
		Statement statement = null;
		
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_payroll_service", "root", "root");
			
//			//adding deduction columns from basic pay
//			String query1="alter table employee_payroll_service.employee_payroll add Deduction_Pay float AS (basic_pay * 0.2 ) after basic_pay;" ;
//			statement=connection.createStatement();
//			int result = statement.executeUpdate(query1);
//			System.out.println(result+" Column Deduction pay is added successfully!");		
//			
			
//			//adding taxable pay columns from basic pay
//			String query2="alter table employee_payroll_service.employee_payroll add Taxable_Pay float AS (basic_pay - Deduction_Pay ) after Deduction_Pay" ;
//			statement=connection.createStatement();
//			int result1 = statement.executeUpdate(query2);
//			System.out.println(result1+" Column Taxable pay is added successfully!");		
//			
			
//			//adding Tax  columns from Taxable_Pat
//			String query3="alter table employee_payroll_service.employee_payroll add Tax float AS (Taxable_Pay * 0.1 ) after Taxable_Pay" ;
//			statement=connection.createStatement();
//			int result3 = statement.executeUpdate(query3);
//			System.out.println(result3+" Column Tax is added successfully!");		
//			
			
			//adding Net_Pay  columns from basic_pay and Tax
			String query4="alter table employee_payroll_service.employee_payroll add Net_Pay float AS (basic_pay - Tax ) after Tax" ;
			statement=connection.createStatement();
			int result4 = statement.executeUpdate(query4);
			System.out.println(result4+" Column Net_Pay is added successfully!");		
			
			
		}catch (SQLException e) {
	        e.printStackTrace();
        }catch (Exception e) {
	        e.printStackTrace();
        }finally {
	         if(connection != null) {
		        connection.close();
	         } 
	         if(statement != null) {
	            statement.close();
	         }
         }
	}

	public long countEntries() {
		return empList.size();
	} 

}
