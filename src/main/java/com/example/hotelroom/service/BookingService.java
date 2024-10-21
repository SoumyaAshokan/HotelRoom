package com.example.hotelroom.service;


import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelroom.exception.HotelCustomException;
import com.example.hotelroom.model.entity.Booking;
import com.example.hotelroom.model.entity.Room;
import com.example.hotelroom.model.entity.User;
import com.example.hotelroom.model.vo.BookResponseVO;
import com.example.hotelroom.model.vo.BookingVO;
import com.example.hotelroom.model.vo.ViewVO;
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
	
	
	/**
	 * Check room availability for a selected date and room type and reserve a room
	 * @param userName
	 * @param bookingVO
	 * @return
	 */
	public BookResponseVO checkRoomAvailability(String userName, BookingVO bookingVO) {
		
		User user = userRepo.findByUserName(userName)
		          .orElseThrow(()-> new HotelCustomException("Invalid user"));
		
		Map<Long, String> availableRooms = customBookingRepo.checkRoomAvailability(
										   bookingVO.getCheckIn(),
				                           bookingVO.getCheckOut(),
				                           bookingVO.getCategory(),
				                           bookingVO.getBookedOccupancy());
		
		if(availableRooms.isEmpty()) {
			throw new HotelCustomException("Rooms are not available for the selected criteria");
		}
		

		Long roomId = null;
		String roomNo = null;
		
		for(Map.Entry<Long,String> entry : availableRooms.entrySet()) {
			Room room = roomRepo.findById(entry.getKey()).orElse(null);
			if(room != null) {
				int currentOccupancy = customBookingRepo.getCurrentOccupancy(room.getRoomNo(),
																			 bookingVO.getCheckIn(),bookingVO.getCheckOut());
				if (currentOccupancy + bookingVO.getBookedOccupancy() <= room.getCapacity()) {
	                roomId = entry.getKey();
	                roomNo = entry.getValue();
	                //break;  
	            } 
			}	
		}
		
		if(roomId == null || roomNo == null) {
//		//if(roomId == null) {
			throw new HotelCustomException("No room available");
		}
		
				
		Room room = roomRepo.findById(roomId)
				            .orElse(null);
		if(room == null) {
			throw new HotelCustomException("Room not available");
		}
		
		
		long numberOfDays = ChronoUnit.DAYS.between(bookingVO.getCheckIn(), bookingVO.getCheckOut()) + 1;
		double totalRoomRate = room.getRoomRatePerDay() * numberOfDays;
		
			Booking booking = convertToEntity(bookingVO,room,user);
	        booking.setBookingNo(generateBookingNo());
	        bookingRepo.save(booking);
	        
	        BookResponseVO responseVO = new BookResponseVO();
	        responseVO.setBookingNo(booking.getBookingNo());
	        responseVO.setRoomNo(roomNo);
	        responseVO.setTotalRoomRate(totalRoomRate);
	        return responseVO;
	        

	        
		} 
	
		/**
		 * convert BookingVO to Booking entity
		 * @param bookingVO
		 * @param room
		 * @param user
		 * @return
		 */
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
		
	

	/**
	 * convert Booking Entity to BookingVO
	 * @param booking
	 * @return
	 */
		private BookingVO convertToVO(Booking booking) {
			BookingVO bookingVO=new BookingVO();
			bookingVO.setBookingNo(booking.getBookingNo());
			bookingVO.setRoomNo(booking.getRoom().getRoomNo());
			bookingVO.setCheckIn(booking.getCheckIn());
			bookingVO.setCheckOut(booking.getCheckOut());
			bookingVO.setCategory(booking.getRoom().getCategory());
			bookingVO.setBookedOccupancy(booking.getBookedOccupancy());
	        bookingVO.setUserName(booking.getUser().getUserName());
	        bookingVO.setRoomRatePerDay(booking.getRoom().getRoomRatePerDay());
	        bookingVO.setStatus(booking.isStatus());


			return bookingVO;
		}
		
		
		
		private String generateBookingNo() {
	        Random random = new Random();
	        return String.valueOf(1000 + random.nextInt(9000));
	    }

		
		/**
		 * cancel a reservation
		 * @param userName
		 * @param bookingNo
		 * @return
		 */
		public String cancelBooking(String userName, String bookingNo) {
			Booking booking=bookingRepo.findByBookingNo(bookingNo)
									   .orElseThrow(()->new HotelCustomException("Booking not found"));
			
			User user= userRepo.findByUserName(userName)
					           .orElseThrow(()->new HotelCustomException("Invalid user"));
					           
			if(!booking.getUser().getUserName().equals(userName) && !user.getRole().equalsIgnoreCase("Admin")) {
				throw new HotelCustomException ("User is not authorized to cancel this booking");
			}
			
			if(!booking.isStatus()) {
				throw new HotelCustomException( "Booking is already canceled");
			}
			
			booking.setStatus(false);
			bookingRepo.save(booking);
			
			return "Booking canceled successfully";
		}

		
		/**
		 * Modify an existing reservation by adding more guests
		 * @param userName
		 * @param bookingNo
		 * @param additionalGuest
		 * @return
		 */
		public String updateBooking(String userName, String bookingNo, int additionalGuest) {
			
			Booking booking = bookingRepo.findByBookingNo(bookingNo)
										 .orElseThrow(()->new HotelCustomException("Booking not found"));
					            	   
			User user = userRepo.findByUserName(userName)
								.orElseThrow(()-> new HotelCustomException("Invalid user"));
			if(!booking.getUser().getUserName().equals(userName) && !user.getRole().equalsIgnoreCase("Admin")) {
				throw new HotelCustomException( "User is not authorized to modify this booking.");
			}
			
			if(!booking.isStatus()) {
				throw new HotelCustomException( "Booking is already canceled");
			}
			
			int newOccupancy = booking.getBookedOccupancy() + additionalGuest;
			Room room=booking.getRoom();
			if(newOccupancy > room.getCapacity()) {
				throw new HotelCustomException( "Update guest count exceeds the room capacity.");
			}
			
			booking.setBookedOccuppancy(newOccupancy);
			bookingRepo.save(booking);
			
			return "Booking updated successfully. New guest count is : "+ newOccupancy;
		}


		/**
		 * view reservations
		 * @param bookingNo
		 * @param status
		 * @param category
		 * @param userName
		 * @return
		 */
		public List<ViewVO> viewAllBookings(ViewVO viewVO) {
			
			Boolean status = viewVO.getStatus();
		    String bookingNo = viewVO.getBookingNo();
		    String category = viewVO.getCategory();
		    String userName = viewVO.getUserName();
			List<Booking> bookings = customBookingRepo.searchBookings(status, bookingNo, category, userName);
			return bookings.stream()
					       .map(this::convertToViewVO)
					       .collect(Collectors.toList());
		}
		
		private ViewVO convertToViewVO(Booking booking) {
			ViewVO viewVO=new ViewVO();
			viewVO.setBookingNo(booking.getBookingNo());
			viewVO.setRoomNo(booking.getRoom().getRoomNo());
			viewVO.setCheckIn(booking.getCheckIn());
			viewVO.setCheckOut(booking.getCheckOut());
			viewVO.setCategory(booking.getRoom().getCategory());
			viewVO.setBookedOccupancy(booking.getBookedOccupancy());
			viewVO.setUserName(booking.getUser().getUserName());
			viewVO.setRoomRatePerDay(booking.getRoom().getRoomRatePerDay());
			viewVO.setStatus(booking.isStatus());


			return viewVO;
		}
}
