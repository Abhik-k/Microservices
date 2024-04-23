package com.lcwd.user.service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcwd.user.service.enteties.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User saveUser(User user) {
		 String randomUserId=UUID.randomUUID().toString();
		 user.setUserId(randomUserId);
		userRepository.save(user);
		return null;
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with given Id not found: "+userId));
	}

}
