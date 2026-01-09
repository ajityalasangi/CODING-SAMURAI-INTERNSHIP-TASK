package ATM.service;

import ATM.model.Account;
import java.util.HashMap;

public class AccountService {
	private static HashMap<Integer,Account> accounts = new HashMap<>();
	
	public static boolean createAccount(Account acc) {
		if(accounts.containsKey(acc.getAccNumber())) {
			return false;
		}
		else {
			accounts.put(acc.getAccNumber(), acc);
			return true;
		}
	}
	
	public static boolean validateLogin(int accNum, int pin) {
		Account acc = accounts.get(accNum);
		if(acc != null && acc.getPin() == pin) {
			return true;
		}
		return false;
	}	
}
