package JDBC_Connectivity.PayrollService_JDBC;

public class Employee {
	public int Id;
	public String firstName;
	public String lastName;
	public float basicPay;
	
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
				+ "]";
	}
	
	
}
