package com.lcwd.user.service.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lcwd.user.service.enteties.Hotel;
import com.lcwd.user.service.enteties.Rating;
import com.lcwd.user.service.enteties.User;
import com.lcwd.user.service.exception.ResourceNotFoundException;
import com.lcwd.user.service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	//RestTemplate
	@Autowired
	private RestTemplate restTemplate;
	
	//Defining Logger
	private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
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
		//Get user from database with the help of userRepository
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with given Id not found: "+userId));
		
		//	fetch rating of the above user from Rating Service
		// http://localhost:8083/ratings/user/8dc7fe1b-48d1-45af-8cff-61bb9ee162fa
		
		//Rating[] ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/user/"+user.getUserId(), Rating[].class);
		//Making API Dynamic by using MicroService Name
		
		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/user/"+user.getUserId(), Rating[].class);

		logger.info("{} ",ratingsOfUser);
		
		List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
		
		List<Rating> ratingList = ratings.stream().map(rating->{
			
			//api call to Hotel Service to get the hotel
			//http://localhost:8082/hotel
			
			ResponseEntity<Hotel> forEntity=restTemplate.getForEntity("http://HOTELSERVICE/hotel/"+rating.getHotelId(), Hotel.class);
			
			Hotel hotel=forEntity.getBody();
			
			logger.info("Response status Code: {} ",forEntity.getStatusCode());
			
			//set the hotel to rating
			rating.setHotel(hotel);
			
			//return the rating
			return rating; 
			
		}).collect(Collectors.toList());
		
		user.setRatings(ratingList);
		
		return user;
	}
}
