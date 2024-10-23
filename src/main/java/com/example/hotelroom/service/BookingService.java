package com.example.hotelroom.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
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

	private static final String ROOM_NOT_AVAILABLE = "Room not available";
	private static final String INVALID_USER = "Invalid user";
	private static final String BOOKING_NOT_FOUND = "Booking not found";
	private static final String ALREADY_CANCEL = "Booking is already canceled";
	private static final String USER_AUTHORIZED = "User is not authorized to cancel this booking";

	/**
	 * Check room availability for a selected date and room type and reserve a room
	 * 
	 * @param userName
	 * @param bookingVO
	 * @return
	 */
	public BookResponseVO checkRoomAvailability(String userName, BookingVO bookingVO) {

		User user = userRepo.findByUserName(userName).orElseThrow(() -> new HotelCustomException(INVALID_USER));

		Map<Long, String> availableRooms = customBookingRepo.checkRoomAvailability(bookingVO.getCheckIn(),
				bookingVO.getCheckOut(), bookingVO.getCategory(), bookingVO.getBookedOccupancy());

		if (availableRooms.isEmpty()) {
			throw new HotelCustomException("Rooms are not available for the selected criteria");
		}

		Long roomId = null;
		String roomNo = null;

		for (Map.Entry<Long, String> entry : availableRooms.entrySet()) {
			Room room = roomRepo.findById(entry.getKey()).orElse(null);
			if (room != null) {
				int currentOccupancy = customBookingRepo.getCurrentOccupancy(room.getRoomNo(), bookingVO.getCheckIn(),
						bookingVO.getCheckOut());
				if (currentOccupancy + bookingVO.getBookedOccupancy() <= room.getCapacity()) {
					roomId = entry.getKey();
					roomNo = entry.getValue();
				}
			}
		}

		if (roomId == null || roomNo == null) {
			throw new HotelCustomException(ROOM_NOT_AVAILABLE);
		}

		Room room = roomRepo.findById(roomId).orElse(null);
		if (room == null) {
			throw new HotelCustomException(ROOM_NOT_AVAILABLE);
		}

		long numberOfDays = ChronoUnit.DAYS.between(bookingVO.getCheckIn(), bookingVO.getCheckOut()) + 1;
		double totalRoomRate = room.getRoomRatePerDay() * numberOfDays;

		Booking booking = convertToEntity(bookingVO, room, user);
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
	 * 
	 * @param bookingVO
	 * @param room
	 * @param user
	 * @return
	 */
	private Booking convertToEntity(BookingVO bookingVO, Room room, User user) {
		Booking booking = new Booking();
		booking.setCheckIn(bookingVO.getCheckIn());
		booking.setCheckOut(bookingVO.getCheckOut());
		booking.setBookedOccuppancy(bookingVO.getBookedOccupancy());
		booking.setRoom(room);
		booking.setUser(user);
		booking.setStatus(true);
		return booking;
	}

	private String generateBookingNo() {
		Random random = new Random();
		return String.valueOf(1000 + random.nextInt(9000));
	}

	/**
	 * cancel a reservation
	 * 
	 * @param userName
	 * @param bookingNo
	 * @return
	 */
	public String cancelBooking(String userName, String bookingNo) {
		Booking booking = bookingRepo.findByBookingNo(bookingNo)
				.orElseThrow(() -> new HotelCustomException(BOOKING_NOT_FOUND));

		User user = userRepo.findByUserName(userName).orElseThrow(() -> new HotelCustomException(INVALID_USER));

		if (!booking.getUser().getUserName().equals(userName) && !user.getRole().equalsIgnoreCase("Admin")) {
			throw new HotelCustomException(USER_AUTHORIZED);
		}

		if (!booking.isStatus()) {
			throw new HotelCustomException(ALREADY_CANCEL);
		}

		booking.setStatus(false);
		bookingRepo.save(booking);

		return "Booking canceled successfully";
	}

	/**
	 * Modify an existing reservation by adding more guests
	 * 
	 * @param userName
	 * @param bookingNo
	 * @param additionalGuest
	 * @return
	 */
	public String updateBooking(String userName, String bookingNo, int additionalGuest) {

		Booking booking = bookingRepo.findByBookingNo(bookingNo)
				.orElseThrow(() -> new HotelCustomException(BOOKING_NOT_FOUND));

		User user = userRepo.findByUserName(userName).orElseThrow(() -> new HotelCustomException(INVALID_USER));

		if (!booking.getUser().getUserName().equals(userName) && !user.getRole().equalsIgnoreCase("Admin")) {
			throw new HotelCustomException(USER_AUTHORIZED);
		}

		if (!booking.isStatus()) {
			throw new HotelCustomException(ALREADY_CANCEL);
		}

		int newOccupancy = booking.getBookedOccupancy() + additionalGuest;
		Room room = booking.getRoom();
		if (newOccupancy > room.getCapacity()) {
			throw new HotelCustomException("Update guest count exceeds the room capacity.");
		}

		booking.setBookedOccuppancy(newOccupancy);
		bookingRepo.save(booking);

		return "Booking updated successfully. New guest count is : " + newOccupancy;
	}

	/**
	 * view reservations
	 * 
	 * @param bookingNo
	 * @param status
	 * @param category
	 * @param userName
	 * @return
	 */
	public List<BookingVO> viewAllBookings(BookingVO bookingVO) {

		Boolean status = bookingVO.isStatus();
		String bookingNo = bookingVO.getBookingNo();
		String category = bookingVO.getCategory();
		String userName = bookingVO.getUserName();
		List<Booking> bookings = customBookingRepo.searchBookings(status, bookingNo, category, userName);
		return bookings.stream()
                .map(booking -> new BookingVO(
                     booking.getBookingNo(),
                     booking.getRoom().getRoomNo(),
                     booking.getCheckIn(),
                     booking.getCheckOut(),
                     booking.getRoom().getCategory(),
                     booking.getBookedOccupancy(),
                     booking.getUser().getUserName(),
                     booking.getRoom().getRoomRatePerDay(),
                     booking.isStatus()))
                .collect(Collectors.toList());
	
	}

}
