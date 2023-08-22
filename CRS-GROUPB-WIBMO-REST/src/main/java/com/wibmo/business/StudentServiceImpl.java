package com.wibmo.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.wibmo.constants.GenderConstant;
import com.wibmo.constants.RoleConstant;
import com.wibmo.dao.StudentDAOImpl;
import com.wibmo.dao.StudentDAOInterface;
import com.wibmo.exception.StudentNotRegisteredException;

import com.wibmo.bean.Student;

/**
 * 
 * @author Himank
 * Implementations of Student Operations
 *
 */

@Service
public class StudentServiceImpl implements StudentServiceInterface {
	private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);
	private static volatile StudentServiceImpl instance=null;
	
	StudentDAOInterface studentDaoInterface=StudentDAOImpl.getInstance();

	private StudentServiceImpl()
	{
		
	}
	
	public static StudentServiceImpl getInstance()
	{
		if(instance==null)
		{
			// This is a synchronized block, when multiple threads will access this instance
			synchronized(StudentServiceImpl.class){
				instance=new StudentServiceImpl();
			}
		}
		return instance;
	}
	
	/**
	 * Method to register a student, although student can't login until it's approved by admin
	 * @param name
	 * @param userID
	 * @param password
	 * @param gender
	 * @param batch
	 * @param branch
	 * @param address
	 * @param country
	 * @return Student ID
	 * @throws StudentNotRegisteredException
	 */
	public String register(String name,String userId,String password,GenderConstant gender,int batch,String branch,String address) throws StudentNotRegisteredException{
		String studentId;
		Student newStudent=new Student(userId,name,RoleConstant.STUDENT,password,gender,address,branch,userId,batch,false);
		studentId=studentDaoInterface.addStudent(newStudent);
		logger.info("\nYour account has been created and pending for Approval by Admin.\n");
		return studentId;
	}

    /**
     * Method to get Student ID from User ID
     * @param userId
     * @return Student ID
     */
    @Override
    public String getStudentId(String userId) {
        
        return studentDaoInterface.getStudentId(userId);
    }



	
	/**
     * Method to check if student is approved by Admin or not
     * @param studentId
     * @return boolean indicating if student is approved
     */
	@Override
	public boolean isApproved(String studentId) {
		return studentDaoInterface.isApproved(studentId);
	}



}