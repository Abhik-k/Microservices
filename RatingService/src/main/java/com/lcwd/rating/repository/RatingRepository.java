package com.lcwd.rating.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;

import com.lcwd.rating.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, String>{
	
	//Custom Finder Methods
	 
	List<Rating> findByUserId(String userId);
	
	List<Rating> findByHotelId(String hotelId);
}
