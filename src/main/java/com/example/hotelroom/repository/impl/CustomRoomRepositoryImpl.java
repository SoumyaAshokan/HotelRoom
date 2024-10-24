package com.example.hotelroom.repository.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.hotelroom.repository.CustomRoomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class CustomRoomRepositoryImpl implements CustomRoomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final String SEARCH_BOOK_QUERY = "SELECT r.category, COUNT(r.roomId) FROM Room r "
			+ "LEFT JOIN Booking b ON r.roomId = b.room.roomId AND  "
			+ "((b.checkIn BETWEEN :checkIn AND :checkOut) OR (b.checkOut BETWEEN :checkIn AND :checkOut)) "
			+ "WHERE ( b.bookingId IS NULL OR b.status = false)";

	@Override
	public List<Object[]> searchBooking(LocalDate checkIn, LocalDate checkOut, Integer capacity, String category) {

		StringBuilder queryBuilder = new StringBuilder(SEARCH_BOOK_QUERY);

		if (capacity != null) {
			queryBuilder.append("AND r.capacity >= :capacity ");
		}

		if (category != null && !category.isEmpty()) {
			queryBuilder.append("AND r.category = :category ");
		}

		queryBuilder.append(" GROUP BY r.category");

		TypedQuery<Object[]> query = entityManager.createQuery(queryBuilder.toString(), Object[].class);
		query.setParameter("checkIn", checkIn);
		query.setParameter("checkOut", checkOut);

		if (capacity != null) {
			query.setParameter("capacity", capacity);
		}

		if (category != null && !category.isEmpty()) {
			query.setParameter("category", category);
		}

		return query.getResultList();
	}

}
