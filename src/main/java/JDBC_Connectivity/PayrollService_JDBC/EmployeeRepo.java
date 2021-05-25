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
			
			Date start=resultset.getDate(9);
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
	
	public List<Employee> findAllForParticularDateRange() throws SQLException {
		
		List<Employee> details=new ArrayList<>();
		Connection connection = null;
		PreparedStatement prepstatement = null;
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_payroll_service", "root", "root");
			
			String query ="Select * from employee_payroll where Start_Date between Cast('2020-03-10' as date) and date(now()); ";
			prepstatement = connection.prepareStatement(query);
			
			ResultSet resultset = prepstatement.executeQuery();
			
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
				
				Date start=resultset.getDate(9);
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
			
			String query1="select sum(basic_pay),gender from employee_payroll emp1 , employee_details emp2 where"
					+ "  emp1.id=emp2.EmployeeID or gender='F' group by gender" ;
			prepstatement=connection.prepareStatement(query1);
			ResultSet result1 = prepstatement.executeQuery();
			result1.next();
			String gender=result1.getString(2);
			String sum1 = result1.getString(1);
			System.out.println(gender +" Gender having Sum of BasicPay of Employees: "+sum1);			
			
			
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
