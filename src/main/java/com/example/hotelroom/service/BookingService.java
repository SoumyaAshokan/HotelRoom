package com.example.hotelroom.service;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
//	public String checkRoomAvailability(String userName, BookingVO bookingVO) {
//		boolean isAvailable;
//		Room room = roomRepo.findByCategoryAndCapacityGreaterThanEqual(bookingVO.getCategory(),bookingVO.getBookedOccupancy()).getFirst();
//		if(ObjectUtils.isEmpty(room)) {
//			return "Room not found";
//		}
//
//		User user= userRepo.findByUserName(userName)
//		          .orElseThrow(()->new IllegalArgumentException("User not found"));
//		Long roomId = room.getRoomId();
//		
//		List<Booking> bookingList = bookingRepo.findAll();
//		if(!CollectionUtils.isEmpty(bookingList)) {
//			isAvailable = customBookingRepo.isRoomAvailable(roomId,bookingVO.getCheckIn(),bookingVO.getCheckOut());
//		} else {
//			isAvailable = true;
//		}
//		if(isAvailable) {
//			Booking booking = convertToEntity(bookingVO,room,user);
//	        booking.setBookingNo(generateBookingNo());
//	        bookingRepo.save(booking);
//	        return booking.getBookingNo();		
//		} else { 	
//			return "Room is not available for the selected dates.";
//		}
//	}
//	

	//Check room availability for a selected date and room type and reserve a room
	public String checkRoomAvailability(String userName, BookingVO bookingVO) {
		int availableCount;
		
		User user = userRepo.findByUserName(userName)
		          .orElse(null);
		if(user == null) {
			return "Invalid user";
		}
		
		Room room = roomRepo.findByCategoryAndCapacityGreaterThanEqual(bookingVO.getCategory(),bookingVO.getBookedOccupancy())
							.stream()
							.findFirst()
							//.orElseThrow(()->new IllegalArgumentException("Room not found"));
							.orElse(null);
		if(room == null) {
			return "Room not available";
		}
		
		
		
		Long roomId = room.getRoomId();
		
		availableCount = customBookingRepo.isRoomAvailable(roomId,bookingVO.getCheckIn(),bookingVO.getCheckOut());

		if(availableCount == 0) {
			Booking booking = convertToEntity(bookingVO,room,user);
	        booking.setBookingNo(generateBookingNo());
	        bookingRepo.save(booking);
	        //return booking.getBookingNo();		
	        return "Room reserved successfully. Booking number is:" + booking.getBookingNo();
		} else { 	
			return "Room is not available for the selected dates.";
		}
	}

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

		
		//cancel a reservation
		public String cancelBooking(String userName, String bookingNo) {
			Booking booking=bookingRepo.findByBookingNo(bookingNo)
									   .orElseThrow(()->new IllegalArgumentException("Booking not found"));
			
			User user= userRepo.findByUserName(userName)
					           .orElseThrow(()->new IllegalArgumentException("Invalid user"));
					           
			if(!booking.getUser().getUserName().equals(userName) && !user.getRole().equalsIgnoreCase("Admin")) {
				return "User is not authorized to cancel this booking";
			}
			
			if(!booking.isStatus()) {
				return "Booking is already canceled";
			}
			
			booking.setStatus(false);
			bookingRepo.save(booking);
			
			return "Booking canceled successfully";
		}

}
