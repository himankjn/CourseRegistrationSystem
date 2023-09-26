package com.wibmo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wibmo.entity.Professor;

@Repository
public interface ProfessorRepository extends CrudRepository<Professor,String>{

}
