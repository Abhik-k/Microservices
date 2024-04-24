package com.lcwd.rating.service;

import java.util.List;

import com.lcwd.rating.entities.Rating;

public interface RatingService {
	
	//create
	Rating createRating(Rating rating);
	
	//get all ratings
	List<Rating> getRating();
	
	//get all by userId
	List<Rating> getRatingByUserId(String userId);
	
	//get all by hotelId
	List<Rating> getRatingByHotelId(String hotelId);
}
