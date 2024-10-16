package com.example.hotelroom.repository.impl;

import java.time.LocalDate;
import java.util.List;

import com.example.hotelroom.repository.CustomRoomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class CustomRoomRepositoryImpl implements CustomRoomRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Object[]> searchBooking(LocalDate checkIn, LocalDate checkOut, Integer bookedOccupancy, String category) {
		
		StringBuilder queryBuilder = new StringBuilder("SELECT r.category, COUNT(r.roomId) FROM Room r");
		queryBuilder.append("LEFT JOIN Booking b ON r.id = b.room.id WHERE ");
		
		queryBuilder.append("(b.checkIn NOT BETWEEN :checkIn AND :checkOut) ");
		queryBuilder.append("AND (b.checkOut NOT BETWEEN :checkIn AND :checkOut) ");
		
		if(bookedOccupancy != null) {
			queryBuilder.append("AND r.occupancy >= :bookedOccupancy ");
		}
		
		if(category != null &&  !category.isEmpty()) {
			queryBuilder.append("AND r.category = :category" );
		}
		
		queryBuilder.append("GROUP BY r.category");
		
		TypedQuery<Object[]> query = entityManager.createQuery(queryBuilder.toString(),Object[].class);
		query.setParameter("checkIn", checkIn);
		query.setParameter("checkOut", checkOut);

	}

	

}
