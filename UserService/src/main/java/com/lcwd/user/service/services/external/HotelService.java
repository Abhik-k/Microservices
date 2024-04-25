package com.lcwd.user.service.services.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lcwd.user.service.enteties.Hotel;

@FeignClient(name="HOTELSERVICE")
public interface HotelService {
	
	@GetMapping("/hotel/{hotelId}")
	Hotel getHotel(@PathVariable(name = "hotelId") String hotelId);
}
