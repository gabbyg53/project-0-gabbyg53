package com.revature.prompts;

import java.util.Scanner;
import org.apache.log4j.Logger;
import com.revature.daos.UserDao;
import com.revature.models.User;
import com.revature.util.AuthUtil;

public class LoginPrompt implements Prompt{
	private Logger log = Logger.getRootLogger();
	private Scanner scan = new Scanner(System.in);
	private UserDao userDao = UserDao.currentImplementation;
	private AuthUtil authUtil = AuthUtil.instance;
	
	@Override
	public Prompt run() {
		System.out.println("Enter 1 to login");
		System.out.println("Enter 2 to register");
		String choice = scan.nextLine();
		switch (choice) {
		case "1": {
			log.debug("attempting to login");
			System.out.println("Enter username:");
			String username = scan.nextLine();
			System.out.println("Enter password:");
			String password = scan.nextLine();

			User u = authUtil.login(username, password);
			if (u == null) {
				log.info("failed to login due to credentials");
				System.out.println("Invalid Credentials");
				break;
			} else {
				log.info("successfully logged in");
				return new MainMenuPrompt();
			}
		}
		case "2": {
			System.out.println("Enter new username: ");
			String username = scan.nextLine();
			User u = userDao.findByUsername(username);
			if (u != null) {
				System.out.println("invalid username ");
				break;
			}
			System.out.println("Enter new Password: ");
			String password = scan.nextLine();
			
			System.out.println("Enter your first name: ");
			String fname = scan.nextLine();
			
			System.out.println("Enter your last name: ");
			String lname = scan.nextLine();
			
			System.out.println("what kind of account would you like to set up?:");
			String accType = scan.nextLine();
			
			User newUser = new User(1,"customer" , fname, lname, username, password,accType);
			userDao.save(newUser);
			break;
		}
		default:
			System.out.println("invalid option");
			break;
		}
		return this;
	}
}
