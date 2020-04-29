package com.flaviodiminuto.corre.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PresenterRegisterUser {

	@JsonProperty("login")
	private String login;
	@JsonProperty("password")
	private String password;

}
