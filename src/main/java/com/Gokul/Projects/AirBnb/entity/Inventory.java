package com.Gokul.Projects.AirBnb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "inventory",
        uniqueConstraints = @UniqueConstraint(name = "unique_hotel_room_date"
                , columnNames = {"hotelId", "room_id", "date"}
        )
)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer bookedCnt;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer reservedCnt;

    @Column(nullable = false)
    private Integer totalCnt;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal surgeFactor;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Boolean close;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
