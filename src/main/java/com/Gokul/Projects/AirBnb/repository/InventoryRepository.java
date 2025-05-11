package com.Gokul.Projects.AirBnb.repository;

import com.Gokul.Projects.AirBnb.entity.Hotel;
import com.Gokul.Projects.AirBnb.entity.Inventory;
import com.Gokul.Projects.AirBnb.entity.Room;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    public void deleteByRoom(Room room);

    @Query("""
           SELECT i.hotel
           FROM Inventory i
           WHERE i.city = :city
            AND i.date BETWEEN :startDate AND :endDate
            AND i.close = false
            AND (i.totalCnt - i.bookedCnt - i.reservedCnt) >= :roomCnt
            GROUP BY i.hotel, i.room
            HAVING COUNT(i.date) = :dateCnt
           """)
    public Page<Hotel> searchHotels(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomCnt") Integer roomCnt,
            @Param("dateCnt") Long dateCnt,
            Pageable pageable
            );

    @Query("""
            select i
            from Inventory i
            where i.room.id = :roomId
                AND i.date BETWEEN :startDate AND :endDate
                AND i.close = false
                AND (i.totalCnt - i.bookedCnt -i.reservedCnt) >= :roomCnt
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public List<Inventory> findAndLockAvailableInventory(
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomCnt") Integer roomCnt
    );
}
