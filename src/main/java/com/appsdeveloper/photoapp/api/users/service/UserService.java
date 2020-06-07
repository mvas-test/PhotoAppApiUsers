package com.appsdeveloper.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appsdeveloper.photoapp.api.users.entities.UserDto;
import com.appsdeveloper.photoapp.api.users.entities.UserEntity;
import com.appsdeveloper.photoapp.api.users.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	UserRepository userRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;


	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public UserDto createUser(UserDto userDetails) {
		userDetails.setUserID(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(new BCryptPasswordEncoder().encode(userDetails.getPassword()));

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

		userRepository.save(userEntity);

		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);

		if (userEntity == null) throw new UsernameNotFoundException("Coulnd't find: " + username);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	public UserDto getUserDetailsByEmail(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null) throw new UsernameNotFoundException("Coulnd't find: " + email);
		
		return new ModelMapper().map(userEntity, UserDto.class);
	}
	
}//class
