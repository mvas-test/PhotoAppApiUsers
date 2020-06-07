package com.appsdeveloper.photoapp.api.users.controllers;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloper.photoapp.api.users.entities.CreateUserRequestModel;
import com.appsdeveloper.photoapp.api.users.entities.CreateUserResponseModel;
import com.appsdeveloper.photoapp.api.users.entities.UserDto;
import com.appsdeveloper.photoapp.api.users.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/status/check")
	public String status() {
		String currentStatus = "Something went wrong...";
		try {
			currentStatus = "Users Service is Working on server" + env.getProperty("java.rmi.server.hostname") + " with ip/port:" + InetAddress.getLocalHost().getHostAddress() + ":" + env.getProperty("local.server.port");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return currentStatus;
	}

	@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
				 produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		userDto.setUserID(UUID.randomUUID().toString());
		
		userService.createUser(userDto);		
		
		CreateUserResponseModel responseUser = modelMapper.map(userDto, CreateUserResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
	}
}
