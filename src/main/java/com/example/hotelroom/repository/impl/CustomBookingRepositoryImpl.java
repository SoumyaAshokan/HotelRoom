package com.example.hotelroom.repository.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.hotelroom.model.entity.Booking;
import com.example.hotelroom.repository.CustomBookingRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class CustomBookingRepositoryImpl implements CustomBookingRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final String CHECK_ROOM_AVAILABILITY_QUERY = "SELECT r.roomId, r.roomNo FROM Room r "
			+ "WHERE r.category = :category " + "AND r.capacity >= :bookedOccupancy " + "AND NOT EXISTS ("
			+ "SELECT 1 FROM Booking b " + "WHERE b.room.roomId = r.roomId " + "AND b.status = true "
			+ "AND ((b.checkIn < :checkOut) AND (b.checkOut > :checkIn))" + ")";

	private static final String GET_CURRENT_OCCUPANCY_QUERY = "SELECT COALESCE(SUM(b.bookedOccupancy), 0) FROM Booking b "
			+ "JOIN Room r ON b.room.roomId = r.roomId " + " WHERE r.roomNo = :roomNo " + "AND b.status = true "
			+ "AND ((b.checkIn < :checkOut AND b.checkOut > :checkIn))";

	private static final String SEARCH_BOOKINGS_QUERY = "SELECT b from Booking b JOIN Room r ON r.roomId = b.room.roomId"
			+ " JOIN User u ON u.userId = b.user.userId WHERE 1=1 ";

	@Override
	public Map<Long, String> checkRoomAvailability(LocalDate checkIn, LocalDate checkOut, String category,
			int bookedOccupancy) {
		String jpql = CHECK_ROOM_AVAILABILITY_QUERY;

		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
		query.setParameter("category", category);
		query.setParameter("bookedOccupancy", bookedOccupancy);
		query.setParameter("checkIn", checkIn);
		query.setParameter("checkOut", checkOut);

		List<Object[]> results = query.getResultList();

		Map<Long, String> roomMap = new HashMap<>();
		for (Object[] result : results) {
			Long id = (Long) result[0];
			String roomNo = (String) result[1];
			roomMap.put(id, roomNo);
		}

		return roomMap;
	}

	@Override
	public List<Booking> searchBookings(Boolean status, String bookingNo, String category, String userName) {

		StringBuilder queryBuilder = new StringBuilder(SEARCH_BOOKINGS_QUERY);

		if (status != null) {
			queryBuilder.append("AND b.status = :status ");
		}

		if (bookingNo != null && !bookingNo.isEmpty()) {
			queryBuilder.append("AND b.bookingNo = :bookingNo ");
		}

		if (category != null && !category.isEmpty()) {
			queryBuilder.append("AND r.category = :category ");
		}

		if (userName != null && !userName.isEmpty()) {
			queryBuilder.append("AND u.userName = :userName ");
		}

		TypedQuery<Booking> query = entityManager.createQuery(queryBuilder.toString(), Booking.class);

		if (status != null) {
			query.setParameter("status", status);
		}

		if (bookingNo != null && !bookingNo.isEmpty()) {
			query.setParameter("bookingNo", bookingNo);
		}

		if (category != null && !category.isEmpty()) {
			query.setParameter("category", category);
		}

		if (userName != null && !userName.isEmpty()) {
			query.setParameter("userName", userName);
		}

		return query.getResultList();
	}

	@Override
	public int getCurrentOccupancy(String roomNo, LocalDate checkIn, LocalDate checkOut) {

		String queryStr = GET_CURRENT_OCCUPANCY_QUERY;

		TypedQuery<Long> query = entityManager.createQuery(queryStr, Long.class);
		query.setParameter("roomNo", roomNo);
		query.setParameter("checkIn", checkIn);
		query.setParameter("checkOut", checkOut);

		Long result = (Long) query.getSingleResult();
		return result != null ? result.intValue() : 0;

	}

}
