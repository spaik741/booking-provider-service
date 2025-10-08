package com.bookingproviderservice.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "schedule")
data class ScheduleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "company_id")
    var companyId: Long? = null,
    @Column(name = "user_id")
    var userId: String? = null,
    @Column(name = "time")
    var dateTime: LocalDateTime? = null,
    @Column(name = "is_free")
    var isFree: Boolean? = null
)

