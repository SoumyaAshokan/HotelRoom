package com.example.hotelroom.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelroom.model.entity.Booking;
import com.example.hotelroom.model.entity.Room;
import com.example.hotelroom.model.entity.User;
import com.example.hotelroom.model.vo.BookingVO;
import com.example.hotelroom.model.vo.RoomVO;
import com.example.hotelroom.repository.BookingRepository;
import com.example.hotelroom.repository.RoomRepository;
import com.example.hotelroom.repository.UserRepository;

@Service
public class BookingService {
	@Autowired
	BookingRepository bookingRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoomRepository roomRepo;
	

	
	
	
	//convert BookingVO to Booking entity
		private Booking convertToEntity(BookingVO bookingVO) {
			Booking booking=new Booking();
			booking.setBookingId(bookingVO.getBookingId());
			
			User user=userRepo.findById(bookingVO.getUserId())
							  .orElseThrow(()->new IllegalArgumentException("User not found"));
		
			Room room=roomRepo.findById(bookingVO.getRoomId())
					          .orElseThrow(()->new IllegalArgumentException("Room not found"));

			booking.setUser(user);
			booking.setRoom(room);
			
			booking.setCheckIn(bookingVO.getCheckIn());
			booking.setCheckOut(bookingVO.getCheckOut());
			booking.setBookedOccuppancy(bookingVO.getBookedOccupancy());
			return booking;
		}
		
	//convert Booking Entity to BookingVO
		private BookingVO convertToVO(Booking booking) {
			BookingVO bookingVO=new BookingVO();
			bookingVO.setBookingId(booking.getBookingId());
			bookingVO.setUserId(booking.getUser().getUserId());
			bookingVO.setRoomId(booking.getRoom().getRoomId());
			bookingVO.setCheckIn(booking.getCheckIn());
			bookingVO.setCheckOut(booking.getCheckOut());
			bookingVO.setBookedOccupancy(booking.getBookedOccupancy());
			return bookingVO;
		}

		
			
//	//Check room availability for a selected date and room type
//	public boolean isRoomAvailable(Long roomId, LocalDate startDate, LocalDate endDate) {
//		List<Booking> existingBooking=bookingRepo.findByRoomRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(roomId,startDate,endDate);
//		return existingBooking.isEmpty();
//	}
	
	
}
