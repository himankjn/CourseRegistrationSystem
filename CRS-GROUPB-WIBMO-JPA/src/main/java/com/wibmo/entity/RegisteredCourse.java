/**
 * 
 */
package com.wibmo.entity;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.wibmo.constants.GradeConstant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="registeredcourse")
@Getter
@Setter
public class RegisteredCourse implements Serializable
{
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private RegisteredCourseId registeredCourseId;
	@Enumerated(EnumType.STRING)
	@Column(name="grade")
	GradeConstant grade;
	
	
	

}
