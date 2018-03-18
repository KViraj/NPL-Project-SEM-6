package M;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class Money {
	public List<Accounts> userData;

	//constructor to initiate basic Account data
	public Money() {
		userData = new ArrayList<Accounts>();
		userData.add(new Accounts("Viii","15ce1075",500));
		userData.add(new Accounts("Hanyaa","15ce1054",600));
		userData.add(new Accounts("Dk","15ce1041",400));
	}

	//perform login into account
	public String login(String username, String password) {
		for (Accounts i : userData)
			if (i.username.equals(username) && i.password.equals(password))
					return i.username;
		return "";
	}

	//perform transaction and return status
	public int transaction (String sender, String receiver, float amt) {
		Accounts send = this.search(sender);
		Accounts receive = this.search(receiver);
		if (receive==null) {
			return 0; //0 indicates no receiver accont exists
		}
		boolean success = send.debitAmount(amt) && receive.creditAmount(amt);
		if (success)
			return 1;//Successful transaction
		else
			return -1;//Unsuccessful transaction, check balance
	}

	//Show private var balance by calling Accounts default method showBalance()
	public float showBalance(String username) {
		Accounts i = search(username);
		return i.showBalance();
	}

	//Search and return proper Account
	Accounts search (String username) {
		for (Accounts i : userData)
			if (i.username.equals(username))
				return i;
		return null;
	}
}


class Accounts {
	public String username;
	protected String password;
	private float balance;

	//constructor creating new account
	Accounts(String username,String password,float balance) {
		this.username = username;
		this.password = password;
		this.balance = balance;
	}

	//credit mo to balance
	boolean creditAmount(float mo) {
		this.balance+=mo;
		return true;
	}

	//debit mo from balance
	boolean debitAmount(float mo) {
		if (this.balance < mo) {
			return false;
		}
		this.balance-=mo;
		return true;
	}

	//return balance of account
	float showBalance() {
		return this.balance;
	}
}