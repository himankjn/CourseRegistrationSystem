package com.wibmo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wibmo.constants.RoleConstant;
import com.wibmo.entity.Admin;
import com.wibmo.exception.RoleMismatchException;
import com.wibmo.exception.UserNotFoundException;
import com.wibmo.service.Impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Test
	public void testUpdatePassword() throws UserNotFoundException {
		Admin u1=new Admin();
		u1.setUserId("uId");
		when(userRepository.findByUserId("uId")).thenReturn(Optional.of(u1));
		doNothing().when(userRepository).updatePassword("uId","pass");
		assertTrue(userService.updatePassword("uId", "pass"));
		verify(userRepository,times(1)).updatePassword("uId","pass");
	}
	
	@Test
	public void testUpdatePassword_UserNotFoundException() {
		when(userRepository.findByUserId("uId")).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class,()->
		{
			userService.updatePassword("uId", "pass");
		});
	}
	
	@Test
	public void testVerifyCredentials_True() throws UserNotFoundException {
		Admin u1=new Admin();
		u1.setUserId("uId");
		u1.setPassword("pass");
		when(userRepository.findByUserId("uId")).thenReturn(Optional.of(u1));
		assertTrue(userService.verifyCredentials("uId", "pass"));
	}
	
	@Test
	public void testVerifyCredentials_False() throws UserNotFoundException {
		Admin u1=new Admin();
		u1.setUserId("uId");
		u1.setPassword("pass1");
		when(userRepository.findByUserId("uId")).thenReturn(Optional.of(u1));
		assertFalse(userService.verifyCredentials("uId", "pass"));
	}
	
	@Test
	public void testVerifyUserRole() throws RoleMismatchException, UserNotFoundException {Admin u1=new Admin();
		u1.setUserId("uId");
		u1.setPassword("pass1");
		u1.setRole(RoleConstant.ADMIN);
		when(userRepository.findByUserId("uId")).thenReturn(Optional.of(u1));
		userService.verifyUserRole("uId", "ADMIN");
		verify(userRepository,times(1)).findByUserId("uId");
	}
	
	@Test
	public void testVerifyUserRole_RoleMismatchException() {
		Admin u1=new Admin();
		u1.setUserId("uId");
		u1.setPassword("pass1");
		u1.setRole(RoleConstant.ADMIN);
		when(userRepository.findByUserId("uId")).thenReturn(Optional.of(u1));
		assertThrows(RoleMismatchException.class,()->{
			userService.verifyUserRole("uId", "STUDENT");
		});
	}
	@Test
	public void testVerifyUserRole_UserNotFoundException(){
		Admin u1=new Admin();
		when(userRepository.findByUserId("uId")).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class,()->{
			userService.verifyUserRole("uId", "STUDENT");
		});
	}
}
