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
@Table(name = "register_invitation")
@Data
public class RegisterInvitation{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "key")
	private String key;

	@Column(name = "invited_email")
	private String invitedEmail;

	@Column(name = "valid_date")
	private LocalDate valideDate;

	@Column(name = "used")
	private boolean used;
	
	@Column(name = "created_date")
	private LocalDate createdDate;
	
	@Column(name = "created_time")
	private LocalTime createdTime;
	
	@Column(name = "updated_date")
	private LocalDate updatedDate;
	
	@Column(name = "updated_time")
	private LocalTime updatedTime;
}
