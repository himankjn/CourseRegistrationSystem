/**
 * 
 */
package com.wibmo.client;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.wibmo.business.StudentServiceImpl;
import com.wibmo.business.UserServiceImpl;
import com.wibmo.business.StudentServiceInterface;
import com.wibmo.business.UserServiceInterface;
import com.wibmo.exception.StudentNotRegisteredException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.constants.GenderConstant;

/**
 * 
 */
public class CRSApplicationClient {

	static boolean loggedin = false;
	static StudentServiceInterface studentInterface=StudentServiceImpl.getInstance();
	static UserServiceInterface userInterface =UserServiceImpl.getInstance();
	//NotificationInterface notificationInterface=NotificationOperation.getInstance();
	/**
	 * @param args
	 * @throws UserNotFoundException 
	 */
	public static void main(String[] args) throws UserNotFoundException {
		while(true) {
			LocalDateTime date= LocalDateTime.now();
			System.out.println("===================!WELCOME TO CRS APPLICATION!====================");
			System.out.println("=================="+date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))+"=====================");
			
			System.out.println("Enter you choice:");
			System.out.println("1. Student Registration\n2. Login\n3. Update Password\n4. Exit");
			Scanner sc = new Scanner(System.in);
			int choice=sc.nextInt();
			switch(choice) {
			case 1: registerStudent();
					break;
			case 2: loginUser();
					break;
			case 3: updatePassword();
			 		break;
			case 4: System.out.println("Exit!");
					System.exit(choice);
			}
		}
		
		
	}
	public static void loginUser() throws UserNotFoundException
	{
		Scanner in = new Scanner(System.in);

		String userId,password;
		System.out.println("-----------------Login------------------");
		System.out.println("Email:");
		userId = in.next();
		System.out.println("Password:");
		password = in.next();
		loggedin = userInterface.verifyCredentials(userId, password);
		
		//2 cases1
		
		
		//true->role->student->approved
		if(loggedin)
		{
			 
			 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
			 
			 LocalDateTime myDateObj = LocalDateTime.now();
			   
			 String formattedDate = myDateObj.format(myFormatObj);  
			
			 String role = userInterface.getRole(userId);
		
		
			 
			 
			switch(role) {
			case "ADMIN":
				System.out.println(formattedDate + " Login Successful");
				CRSAdminMenu adminMenu = new CRSAdminMenu();
				adminMenu.createMenu();
				break;
			case "PROFESSOR":
				System.out.println(formattedDate + " Login Successful");
				CRSProfessorMenu professorMenu=new CRSProfessorMenu();
				professorMenu.createMenu(userId);
				
				break;
			case "STUDENT":
				
				String studentId = userId;
				boolean isApproved=studentInterface.isApproved(studentId);
				if(isApproved) {
					System.out.println(formattedDate + " Login Successful");
					CRSStudentMenu studentMenu=new CRSStudentMenu();
					studentMenu.create_menu(studentId);
					
				} else {
					System.out.println("Failed to login, you have not been approved by the administration!");
					loggedin=false;
				}
				break;
			}
			
			
		}
		else
		{
			System.out.println("Invalid Credentials!");
		}
		
	}
	
	/**
	 * Method to help Student register themselves, pending admin approval
	 */
	public static void registerStudent()
	{
		Scanner sc=new Scanner(System.in);

		String userId,name,password,address,branchName;
		GenderConstant gender;
		int genderV, batch;
		try
		{
			//input all the student details
			System.out.println("---------------Student Registration-------------");
			System.out.println("Name:");
			name=sc.nextLine();
			System.out.println("Email:");
			userId=sc.next();
			System.out.println("Password:");
			password=sc.next();
			System.out.println("GenderConstant: \t 1: Male \t 2.Female\t 3.Other");
			genderV=sc.nextInt();
			sc.nextLine();
			
			switch(genderV)
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
			
			System.out.println("Branch:");
			branchName=sc.nextLine();
			System.out.println("Batch:");
			batch=sc.nextInt();
			sc.nextLine();
			System.out.println("Address:");
			address=sc.nextLine();
			
			
			String newStudentId = studentInterface.register(name, userId, password, gender, batch, branchName, address);
			
			//notificationInterface.sendNotification(NotificationTypeConstant.REGISTRATION, newStudentId, null,0);
			
		}
		catch(StudentNotRegisteredException ex)
		{
			System.out.println("Something went wrong! "+ex.getStudentName() +" not registered. Please try again");
		}
		//sc.close();
	}
	
	/**
	 * Method to update password of User
	 */
	public static void updatePassword() {
		Scanner in = new Scanner(System.in);
		String userId,newPassword;
		try {
			System.out.println("------------------Update Password--------------------");
			System.out.println("Email");
			userId=in.next();
			System.out.println("New Password:");
			newPassword=in.next();
			boolean isUpdated=userInterface.updatePassword(userId, newPassword);
			if(isUpdated)
				System.out.println("Password updated successfully!");

			else
				System.out.println("Something went wrong, please try again!");
		} catch(Exception ex) {
			System.out.println("Error Occured "+ex.getMessage());
		}
		
	}
}