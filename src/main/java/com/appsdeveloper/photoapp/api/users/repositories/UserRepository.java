package com.appsdeveloper.photoapp.api.users.repositories;

import org.springframework.data.repository.CrudRepository;

import com.appsdeveloper.photoapp.api.users.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
