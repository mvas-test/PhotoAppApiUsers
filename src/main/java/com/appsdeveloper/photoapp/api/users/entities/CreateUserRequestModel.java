package com.appsdeveloper.photoapp.api.users.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class CreateUserRequestModel {
	
	@NotNull(message="First Name cannot be null")
	@Size(min=2)
	private String firstName;
	
	@NotNull(message="Last Name cannot be null")
	@Size(min=3)
	private String lastName;
	
	@NotNull(message="Password cannot be null")
	@Size(min=8, max=16)
	private String password;
	
	@NotNull(message="Email cannot be null")
	@Email
	private String email;
	
	public CreateUserRequestModel() {
		super();
	}

	public CreateUserRequestModel(String firstName, String lastName, String password, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "CreateUSerRequestModel [firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email + "]";
	}
	
}
