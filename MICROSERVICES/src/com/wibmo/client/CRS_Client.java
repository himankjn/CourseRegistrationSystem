/**
 * 
 */
package com.wibmo.client;
import java.time.LocalDateTime;
import java.util.Scanner;


/**
 * 
 */
public class CRS_Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LocalDateTime date= LocalDateTime.now();
		System.out.println("===================Weclome To CRS Application!====================");
		System.out.println("=============="+date.toLocalTime() + " " +date.toLocalDate()+"===============");
		
		System.out.println("Enter you choice:");
		System.out.println("1. Student Registration\n2. Login\n3. Update Password\n4. Exit");
		Scanner sc = new Scanner(System.in);
		int choice=sc.nextInt();
		switch(choice) {
		case 1: System.out.println("Student Registration Menu !");
				break;
		case 2: System.out.println("Login Success Assumed\nProf. Login Menu:");
				System.out.println("Enter your professor Id:");
				String userId = sc.next();
				ProfessorClient professorMenu=new ProfessorClient();
				professorMenu.createMenu(userId);
				break;
		case 3: System.out.println("Update Passowd Menu");
		 		break;
		case 4: System.out.println("Exit!");
				System.exit(choice);
		}
	}
}