/**
 * 
 */
package com.wibmo.client;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.wibmo.business.NotificationServiceImpl;
import com.wibmo.business.NotificationServiceInterface;
import com.wibmo.business.StudentServiceImpl;
import com.wibmo.business.UserServiceImpl;
import com.wibmo.business.StudentServiceInterface;
import com.wibmo.business.UserServiceInterface;
import com.wibmo.exception.PasswordMismatchException;
import com.wibmo.exception.RoleMismatchException;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.validator.StudentValidator;
import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;

/**
 * 
 */
public class CRSApplicationClient {

	//logger injection
	private static final Logger logger = Logger.getLogger(CRSApplicationClient.class);
	
	static boolean loggedin = false;
	static StudentServiceInterface studentInterface=StudentServiceImpl.getInstance();
	static UserServiceInterface userInterface =UserServiceImpl.getInstance();
	NotificationServiceInterface notificationInterface=NotificationServiceImpl.getInstance();

	public static void main(String[] args) throws UserNotFoundException {
		while(true) {
			LocalDateTime date= LocalDateTime.now();
			logger.info("\n===================!WELCOME TO CRS APPLICATION!===================="
					+ "\n======================="+date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))+"========================\n|"
					+ "====================================|\n"
					+ "|         Enter you choice:          |\n|         "
					+ "1. Student Registration    |\n|         "
					+ "2. Login                   |\n|         "
					+ "3. Update Password         |\n|         "
					+ "4. Exit                    |\n|"
					+ "====================================|");
			Scanner sc = new Scanner(System.in);
			int choice=sc.nextInt();
			switch(choice) {
			case 1: registerStudent();
					break;
			case 2: loginUser();
					break;
			case 3: updatePassword();
			 		break;
			case 4: logger.info("Exit!");
					System.exit(choice);
			}
		}
		
		
	}
	public static void loginUser() throws UserNotFoundException
	{
		Scanner in = new Scanner(System.in);

		String userId,password;
		int roleInp;
		logger.info("\n-----------------Login------------------");
		logger.info("Email:");
		userId = in.next();
		logger.info("Password:");
		password = in.next();
		logger.info("Role:\n1.Admin \n2.Professor \n3.Student");
		roleInp = in.nextInt();
		loggedin = userInterface.verifyCredentials(userId, password);
		try {
			userInterface.verifyUserRole(userId,roleInp);
			}
		catch(RoleMismatchException e) {
				logger.info(e.getMessage());
				loggedin=false;
		}
		if(loggedin)
		{
			 
			 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
			 
			 LocalDateTime myDateObj = LocalDateTime.now();
			   
			 String formattedDate = myDateObj.format(myFormatObj);  
			
			 String role = userInterface.getRole(userId);
		
		
			 
			 
			switch(role) {
			case "ADMIN":
				logger.info(formattedDate + " Login Successful");
				CRSAdminMenu adminMenu = new CRSAdminMenu();
				adminMenu.createMenu();
				break;
			case "PROFESSOR":
				logger.info(formattedDate + " Login Successful");
				CRSProfessorMenu professorMenu=new CRSProfessorMenu();
				professorMenu.createMenu(userId);
				
				break;
			case "STUDENT":
				
				String studentId = userId;
				boolean isApproved=studentInterface.isApproved(studentId);
				if(isApproved) {
					logger.info(formattedDate + " Login Successful");
					CRSStudentMenu studentMenu=new CRSStudentMenu();
					studentMenu.create_menu(studentId);
					
				} else {
					logger.info("Failed to login, you have not been approved by the administration!");
					loggedin=false;
				}
				break;
			}
			
			
		}
		else
		{
			logger.info("Invalid Credentials!");
		}
		
	}
	
	/**
	 * Method to help Student register themselves, pending admin approval
	 */
	public static void registerStudent()
	{
		Scanner sc=new Scanner(System.in);

		String userId,name,password,confirmPassword,address,branchName;
		GenderConstant gender;
		int genderInput, batch;
		try
		{
			//input all the student details
			logger.info("---------------Student Registration-------------");
			logger.info("Your Name:");
			name=sc.nextLine();
			logger.info("Your Email:");
			userId=sc.next();
			logger.info("Your Password:");
			password=sc.next();
			logger.info("Confirm Password:");
			confirmPassword=sc.next(); 
			StudentValidator.verifySamePassword(password,confirmPassword);
			logger.info("GenderConstant: \t 1: Male \t 2.Female\t 3.Others");
			genderInput=sc.nextInt();
			sc.nextLine();
			
			switch(genderInput)
			{
			case 1:
				gender=GenderConstant.MALE;
				break;
			case 2:
				gender=GenderConstant.FEMALE;
				break;
				
			case 3:
				gender=GenderConstant.OTHER;
				break;
			default: 
				gender=GenderConstant.OTHER;
			}
			
			logger.info("Branch:");
			branchName=sc.nextLine();
			logger.info("Batch:");
			batch=sc.nextInt();
			sc.nextLine();
			logger.info("Address:");
			address=sc.nextLine();
			
			
			String newStudentId = studentInterface.register(name, userId, password, gender, batch, branchName, address);
			
			//notificationInterface.sendNotification(NotificationTypeConstant.REGISTRATION, newStudentId, null,0);
			
		}
		catch(StudentNotRegisteredException ex)
		{
			logger.info("Something went wrong! "+ex.getStudentName() +" not registered. Please try again");
		}
		catch(PasswordMismatchException e) {
			logger.info(e.getMessage());
		}
		//sc.close();
	}
	
	/**
	 * Method to update password of User
	 */
	public static void updatePassword() {
		Scanner in = new Scanner(System.in);
		String userId,newPassword,confirmPassword;
		try {
			logger.info("------------------Update Password--------------------");
			logger.info("Email");
			userId=in.next();
			logger.info("New Password:");
			newPassword=in.next();
			logger.info("Confirm Password:");
			confirmPassword=in.next(); 
			StudentValidator.verifySamePassword(newPassword,confirmPassword);
			boolean isUpdated=userInterface.updatePassword(userId, newPassword);
			if(isUpdated)
				logger.info("Password updated successfully!");

			else
				logger.info("Something went wrong, please try again!");
		}
		catch(PasswordMismatchException e) {
			logger.info(e.getMessage());
		}
		catch(Exception ex) {
			logger.info("Error Occured "+ex.getMessage());
		}
		
		
		
	}
}