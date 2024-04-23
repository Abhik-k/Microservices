package com.lcwd.hotel.service;

import java.util.List;

import com.lcwd.hotel.entities.Hotel;

public interface HotelService {

	//create
	Hotel create(Hotel hotel);
	
	//GetAll
	List<Hotel> getAll();
	
	//getById
	Hotel getById(String id);
}
