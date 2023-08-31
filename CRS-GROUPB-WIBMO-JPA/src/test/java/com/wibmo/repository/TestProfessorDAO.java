
package com.wibmo.repository;

 

import static org.junit.jupiter.api.Assertions.*;

 

import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

 

import com.wibmo.entity.Professor;

import com.wibmo.service.AdminServiceInterface;

import com.wibmo.service.StudentServiceInterface;

import com.wibmo.service.Impl.AdminServiceImpl;

 

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.times;

import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.when;

 

import java.util.ArrayList;

import java.util.List;

 

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.mockito.junit.jupiter.MockitoExtension;

 

@ExtendWith(MockitoExtension.class)

class TestProfessorDAO {


     @InjectMocks
     AdminServiceImpl adminService;
     
     @Mock
     ProfessorRepository professorRepository;

 

    @BeforeAll
    static void setUpBeforeClass() throws Exception {

    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {

    }



    @Test
    void findAllProfessors() {

         	List<Professor> list = new ArrayList<Professor>();
            Professor profOne = new Professor();
            Professor profTwo = new Professor();
           
            profOne.setUserId("bob123@gmail.com");
            profOne.setDepartment("DS");
            profOne.setProfessorId("bob123@gmail.com");
            profOne.setDesignation("Asst. Prof");

            profTwo.setUserId("RC");
            profTwo.setProfessorId("RC");
            profTwo.setDepartment("CSE");
            profTwo.setDesignation("Dean");

            list.add(profOne);
            list.add(profTwo);

 

            when(professorRepository.findAll()).thenReturn(list);
            List<Professor> empList = adminService.viewProfessors();

            assertEquals(2, empList.size());


    }

 

}
