package ATM.model;

public class Account {
	private int accNumber;
	private String name;
	private int pin;
	private double balance;
	
	public Account(int accNum, String name, int pin) {
		this.accNumber = accNum;
		this.name = name;
		this.pin = pin;
		this.balance = 0.0;
	}
	
	public int getAccNumber() {
		return accNumber;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPin() {
		return pin;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
}
