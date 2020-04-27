package com.flaviodiminuto.corre.domain.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
/**
 * 
 * @author flaviodiminuto
 *
 */
@Entity
@Table(name = "users")
@Data
public class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "login")
	private String login;
	
	@Column(name = "\"password\"")
	private String password;
	
	@Column(name = "created_date")
	private LocalDate createdDate;
	
	@Column(name = "created_time")
	private LocalTime createdTime;
	
	@Column(name = "updated_date")
	private LocalDate updatedDate;
	
	@Column(name = "updated_time")
	private LocalTime updatedTime;
}
