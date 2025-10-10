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
    var homeNumber: String? = null,
    @Column(name = "country")
    var country: String? = null,
    @OneToOne(cascade = [CascadeType.ALL], mappedBy = "address", fetch = FetchType.LAZY)
    var company: CompanyEntity? = null
)