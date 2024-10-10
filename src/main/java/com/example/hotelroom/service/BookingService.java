package com.example.hotelroom.service;


import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.example.hotelroom.model.entity.Booking;
import com.example.hotelroom.model.entity.Room;
import com.example.hotelroom.model.entity.User;
import com.example.hotelroom.model.vo.BookingVO;
import com.example.hotelroom.repository.BookingRepository;
import com.example.hotelroom.repository.CustomBookingRepository;
import com.example.hotelroom.repository.RoomRepository;
import com.example.hotelroom.repository.UserRepository;

@Service
public class BookingService {
	@Autowired
	BookingRepository bookingRepo;
	
	@Autowired
	CustomBookingRepository customBookingRepo;
	
	@Autowired
	RoomRepository roomRepo;
	
	@Autowired
	UserRepository userRepo;
	
	//Check room availability for a selected date and room type and reserve a room
	public String checkRoomAvailability(String userName, Long roomId, BookingVO bookingVO) {
		boolean isAvailable;
		Room room=roomRepo.findById(roomId).orElseThrow(()->new IllegalArgumentException("Room not found"));

		User user= userRepo.findByUserName(userName)
		          .orElseThrow(()->new IllegalArgumentException("User not found"));
		
		List<Booking> bookingList = bookingRepo.findAll();
		if(!CollectionUtils.isEmpty(bookingList)) {
			isAvailable = customBookingRepo.isRoomAvailable(roomId,bookingVO.getCheckIn(),bookingVO.getCheckOut());
		} else {
			isAvailable = true;
		}
		if(isAvailable) {
			Booking booking = convertToEntity(bookingVO, room, user);
	        booking.setBookingNo(generateBookingNo());
	        bookingRepo.save(booking);
	        return booking.getBookingNo();		
		} else { 	
			throw new IllegalArgumentException("Room is not available for the selected dates.");
		}
	}
	
	//Reserve a room for a specific date and room type
//	public String reserveRoom(String userName,BookingVO bookingVO) {
//		Room room=roomRepo.findByRoomNo(bookingVO.getRoomNo())
//						  .orElseThrow(()->new IllegalArgumentException("Room not found"));
//		
//		if(customBookingRepo.isRoomAvailable(room.getRoomId(),bookingVO.getCheckIn(), bookingVO.getCheckOut())) {
//			Booking booking = convertToEntity(bookingVO,room);
//	        booking.setBookingNo(generateBookingNo());
//	        bookingRepo.save(booking);
//	        return booking.getBookingNo();		
//		} else { 	
//			throw new IllegalArgumentException("Room is not available for the selected dates.");
//		}
		
//		User user=userRepo.findByUserName(userName)
//				          .orElseThrow(()->new IllegalArgumentException("User not found"));
//
//        booking.setUser(user); 
//	}


	


		//convert BookingVO to Booking entity
		private Booking convertToEntity(BookingVO bookingVO,Room room, User user) {
			Booking booking=new Booking();	
			booking.setCheckIn(bookingVO.getCheckIn());
			booking.setCheckOut(bookingVO.getCheckOut());
			booking.setBookedOccuppancy(bookingVO.getBookedOccupancy());
			booking.setRoom(room);
			booking.setUser(user);
			booking.setStatus(true);
			return booking;
		}
		
	

	//convert Booking Entity to BookingVO
		private BookingVO convertToVO(Booking booking) {
			BookingVO bookingVO=new BookingVO();
			bookingVO.setBookingNo(booking.getBookingNo());
			bookingVO.setRoomNo(booking.getRoom().getRoomNo());
			bookingVO.setCheckIn(booking.getCheckIn());
			bookingVO.setCheckOut(booking.getCheckOut());
			bookingVO.setCategory(booking.getRoom().getCategory());
			bookingVO.setBookedOccupancy(booking.getBookedOccupancy());
			return bookingVO;
		}	
		
		
		private String generateBookingNo() {
	        Random random = new Random();
	        return String.valueOf(1000 + random.nextInt(9000));
	    }
//		private String generateBookingNo() {
//			return String.valueOf((int)(Math.random()*9000)+1000);
//		}
}
