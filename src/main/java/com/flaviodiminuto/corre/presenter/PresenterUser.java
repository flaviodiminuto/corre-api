package com.flaviodiminuto.corre.presenter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName(value = "user")
public class PresenterUser extends PresenterRegisterUser{

	@JsonProperty("id")
	private Long id;
	
	@Override
	public boolean equals(Object o) {
		if(o.getClass().isInstance(this)) {
			PresenterUser presenterUser = (PresenterUser) o;
			return presenterUser.getId().equals(this.getId())
					&& presenterUser.getLogin().equals(this.getLogin())
					&& presenterUser.getPassword().equals(getPassword());
		}
		return false;
	}
}
