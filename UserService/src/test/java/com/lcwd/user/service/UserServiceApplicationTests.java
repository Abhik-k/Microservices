package com.lcwd.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lcwd.user.service.enteties.Rating;
import com.lcwd.user.service.services.external.RatingService;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	RatingService ratingService;
	
//	@Test
//	void createRating() {
//		Rating rating = Rating.builder().rating(14).userId("").hotelId("").feedback("This is created using Feign Client").build();
//		Rating newRating = ratingService.createRating(rating);
//		System.out.println(newRating);
//	}
}
