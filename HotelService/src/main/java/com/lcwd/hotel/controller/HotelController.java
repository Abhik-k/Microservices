package com.lcwd.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.service.HotelService;

@RestController
@RequestMapping("/hotel")
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@PostMapping
	public ResponseEntity<Hotel> creteHotel(@RequestBody Hotel hotel){
		hotelService.create(hotel);
		return ResponseEntity.status(HttpStatus.CREATED).body(hotel);
	}
	
	@GetMapping
	public ResponseEntity<List<Hotel>> getHotel(){
		List<Hotel> list=hotelService.getAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Hotel> getById(@PathVariable String id){
		Hotel hotel = hotelService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(hotel);
	}
}
