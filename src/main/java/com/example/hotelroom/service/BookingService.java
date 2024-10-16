package com.example.hotelroom.service;


import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hotelroom.model.entity.Booking;
import com.example.hotelroom.model.entity.Room;
import com.example.hotelroom.model.entity.User;
import com.example.hotelroom.model.vo.BookResponseVO;
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
	public BookResponseVO checkRoomAvailability(String userName, BookingVO bookingVO) {
		
		User user = userRepo.findByUserName(userName)
		          .orElseThrow(()-> new IllegalArgumentException("Invalid user"));
		
		Map<Long, String> availableRooms = customBookingRepo.checkRoomAvailability(
										   bookingVO.getCheckIn(),
				                           bookingVO.getCheckOut(),
				                           bookingVO.getCategory(),
				                           bookingVO.getBookedOccupancy());
		
		if(availableRooms.isEmpty()) {
			throw new IllegalArgumentException("Rooms are not available for the selected criteria");
		}
		
		Long roomId = availableRooms.keySet().iterator().next();
		String roomNo = availableRooms.get(roomId);
				
		Room room = roomRepo.findById(roomId)
				            .orElse(null);
		if(room == null) {
			throw new IllegalArgumentException("Room not available");
		}
		
		
		long numberOfDays = ChronoUnit.DAYS.between(bookingVO.getCheckIn(), bookingVO.getCheckOut()) + 1;
		double totalRoomRate = room.getRoomRate() * numberOfDays;
		
			Booking booking = convertToEntity(bookingVO,room,user);
	        booking.setBookingNo(generateBookingNo());
	        bookingRepo.save(booking);
	        
	        BookResponseVO responseVO = new BookResponseVO();
	        responseVO.setBookingNo(booking.getBookingNo());
	        responseVO.setRoomNo(roomNo);
	        responseVO.setTotalRoomRate(totalRoomRate);
	        return responseVO;
	        
//	        String bookingNo = booking.getBookingNo();
//	        return "Room reserved successfully.\n "
//	        	   + "Booking number is:" + bookingNo +"\n"
//	        	   + "Room number is: " +roomNo +"\n"
//	        	   + "Total room rate for " +numberOfDays+ " days is: " + totalRoomRate;
	        
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

		
		//Modify an existing reservation by adding more guests
		public String updateBooking(String userName, String bookingNo, int additionalGuest) {
			
			Booking booking = bookingRepo.findByBookingNo(bookingNo)
					            	   .orElse(null);
			if(booking == null) {
				return "Booking not found";
			}
					            	   
			User user = userRepo.findByUserName(userName)
								.orElseThrow(()-> new IllegalArgumentException("Invalid user"));
			if(!booking.getUser().getUserName().equals(userName) && !user.getRole().equalsIgnoreCase("Admin")) {
				return "User is not authorized to modify this booking.";
			}
			
			if(!booking.isStatus()) {
				return "Booking is already canceled";
			}
			
			int newOccupancy = booking.getBookedOccupancy() + additionalGuest;
			Room room=booking.getRoom();
			if(newOccupancy > room.getCapacity()) {
				return "Update guest count exceeds the room capacity.";
			}
			
			booking.setBookedOccuppancy(newOccupancy);
			bookingRepo.save(booking);
			
			return "Booking updated successfully. New guest count is : "+ newOccupancy;
		}
}
