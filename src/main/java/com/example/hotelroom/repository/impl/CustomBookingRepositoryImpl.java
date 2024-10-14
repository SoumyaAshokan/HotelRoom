package com.example.hotelroom.repository.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.hotelroom.repository.CustomBookingRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class CustomBookingRepositoryImpl implements CustomBookingRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Map<Long,String> checkRoomAvailability(LocalDate checkIn, LocalDate checkOut, String category, int bookedOccupancy) {
		String jpql = "SELECT r.roomId,r.roomNo FROM Room r " +
	                  "LEFT JOIN Booking b ON r.roomId=b.room.roomId " +
				      "WHERE r.category = :category " + 
	                  "AND r.capacity >=  :bookedOccupancy " +
	                 // "AND (:checkIn < b.checkOut  AND :checkOut > b.checkIn)"
				      "AND (b.checkOut IS NULL OR :checkIn >= b.checkOut OR :checkOut <= b.checkIn)" +
					  "AND (b.status IS NULL OR b.status = false)";
		
		TypedQuery<Object[]> query = entityManager.createQuery(jpql,Object[].class);
		query.setParameter("category", category);
		query.setParameter("bookedOccupancy", bookedOccupancy);
		query.setParameter("checkIn", checkIn);
		query.setParameter("checkOut", checkOut);
		
		List<Object[]> results=query.getResultList();
		
		Map<Long,String> roomMap=new HashMap<>();
		for(Object[] result:results) {
			Long id=(Long) result[0];
			String roomNo=(String) result[1];
			roomMap.put(id,roomNo);
		}
		
		return roomMap;
	}

}
