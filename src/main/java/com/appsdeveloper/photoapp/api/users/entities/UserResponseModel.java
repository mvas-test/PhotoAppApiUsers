package com.appsdeveloper.photoapp.api.users.entities;

import java.util.List;

public class UserResponseModel {
	
	private String userID;
	private String firstName;
	private String lastName;
	private String email;
	private List<AlbumResponseModel> albums;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<AlbumResponseModel> getAlbums() {
		return albums;
	}
	public void setAlbums(List<AlbumResponseModel> albums) {
		this.albums = albums;
	}

	

}
