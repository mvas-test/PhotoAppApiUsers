package com.appsdeveloper.photoapp.api.users.entities;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4267608737428986640L;

	private String firstName;

	private String lastName;

	private String password;

	private String email;

	private String userID;
	
	private String encryptedPassword;
	
	private List<AlbumResponseModel> albums;

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

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public List<AlbumResponseModel> getAlbums() {
		return albums;
	}

	public void setAlbums(List<AlbumResponseModel> albums) {
		this.albums = albums;
	}

	@Override
	public String toString() {
		return "UserDto [firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", email="
				+ email + ", userID=" + userID + ", encryptedPassword=" + encryptedPassword + ", albums=" + albums
				+ ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + ", getPassword()="
				+ getPassword() + ", getEmail()=" + getEmail() + ", getUserID()=" + getUserID()
				+ ", getEncryptedPassword()=" + getEncryptedPassword() + ", getAlbums()=" + getAlbums()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
