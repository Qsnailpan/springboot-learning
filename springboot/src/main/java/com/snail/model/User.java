package com.snail.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author snail
 * @version 1.0.0
 *
 */

@Getter
@Setter
@Entity(name = "user_")
public class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name_" , length = 20)
    private String name;
	
	@Column(name = "age_")
    private Integer age;
	
}
