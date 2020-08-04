package com.appsdeveloper.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.appsdeveloper.photoapp.api.users.entities.AlbumResponseModel;
import com.appsdeveloper.photoapp.api.users.entities.UserDto;
import com.appsdeveloper.photoapp.api.users.entities.UserEntity;
import com.appsdeveloper.photoapp.api.users.repositories.UserRepository;
import com.appsdeveloper.photoapp.api.users.service.clients.AlbumsServiceClient;

@Service
public class UserService implements UserDetailsService {

	UserRepository userRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	//RestTemplate restTemplate;
	Environment environment;
	AlbumsServiceClient albumsServiceClient;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, 
			AlbumsServiceClient albumsServiceClient, Environment environment) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.albumsServiceClient = albumsServiceClient;
		this.environment = environment;
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
	
	public UserDto getUserByUserID(String userID) {
		UserEntity userEntity = userRepository.findByUserID(userID);
		if (userEntity == null) {
			throw new UsernameNotFoundException("USer not Found");
		}
		
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		
		//String albumsUrl = String.format("http://ALBUMS-WS/users/%s/albums", userID);
//		String albumsUrl = String.format(environment.getProperty("albums.url"), userID);
//		ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(albumsUrl, HttpMethod.GET, null,
//																				new ParameterizedTypeReference<List<AlbumResponseModel>>() {
//																					});		
				
//		List<AlbumResponseModel> albumsList = albumsListResponse.getBody();
//		List<AlbumResponseModel> albumsList = null;
////		try {
//			albumsList = albumsServiceClient.getAlbums(userID);
//		} catch (FeignException e) {
//			// TODO Auto-generated catch block
//			logger.error(e.getLocalizedMessage());
//		}
		
		logger.info("Before calling Albums microservice");
		List<AlbumResponseModel> albumsList = albumsServiceClient.getAlbums(userID);
		logger.info("After calling Albums microservice");
		
		userDto.setAlbums(albumsList);
		
		return userDto;
	}
	
}//class
