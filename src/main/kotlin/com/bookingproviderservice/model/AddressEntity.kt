package com.bookingproviderservice.model

import jakarta.persistence.*

@Entity
@Table(name = "address")
data class AddressEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "street")
    var street: String? = null,
    @Column(name = "city")
    var city: String? = null,
    @Column(name = "home_number")
    var homeNumber: Int? = null,
    @Column(name = "country")
    var country: String? = null,
    @OneToOne
    @JoinColumn(name = "company_id")
    var company: CompanyEntity? = null
)