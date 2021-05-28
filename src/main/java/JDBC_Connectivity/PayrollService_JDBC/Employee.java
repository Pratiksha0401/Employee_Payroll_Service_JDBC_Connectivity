package JDBC_Connectivity.PayrollService_JDBC;

import java.sql.Date;

public class Employee {
	public int Id;
	public String firstName;
	public String lastName;
	public float basicPay;
	public String Start_Date;	
	
	public Employee( int id ,String firstName, String lastName, float basicPay) {	
		this.Id=id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.basicPay = basicPay;
	}

	public Employee() {
		
	}


	public String getStart_Date() {
		return Start_Date;
	}
	public void setStart_Date(String start_Date) {
		this.Start_Date = start_Date;
	}
	public float getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(float basicPay) {
		this.basicPay = basicPay;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		this.Id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return "Employee [Id=" + Id + ", firstName=" + firstName + ", lastName=" + lastName + ", basicPay=" + basicPay
				+ ", Start_Date=" + Start_Date + "]";
	}
	
	
	
}
