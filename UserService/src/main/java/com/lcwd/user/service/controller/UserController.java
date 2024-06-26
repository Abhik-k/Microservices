package com.lcwd.user.service.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.user.service.enteties.User;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Builder;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	int retryCount=1;
	
	@GetMapping("/{userId}")
	//@CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
	//@Retry(name="ratingHotelService",fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name="userRateLimiter",fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		logger.info("Get Singel User Handler: UserController");
		
		logger.info("Retry Count: {}",retryCount);
		retryCount++;
		
		User user1 = userService.getUser(userId);
		return ResponseEntity.ok(user1);
	}
	
	//creating Fallback Method for CircuitBreaker
	public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
		
		logger.info("Fallback is executed because service is down: ", ex.getMessage());
		
		User user = User.builder()
					.email("dummy@email.com")
					.name("Dummy")
					.about("This user is created Dummy because some service is Down")
					.userId("12345")
					.build();
		
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> list = userService.getAllUser();
		return ResponseEntity.ok(list);
	}
}
