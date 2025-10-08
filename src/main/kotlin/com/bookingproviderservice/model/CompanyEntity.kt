package com.bookingproviderservice.model

import jakarta.persistence.*

@Entity
@Table(name = "company")
data class CompanyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "name")
    var name: String? = null,
    @Column(name = "email")
    var email: String? = null,
    @Column(name = "website")
    var website: String? = null,
    @Column(name = "phone")
    var phone: String? = null,
    @OneToOne(cascade = [CascadeType.ALL], mappedBy = "address", fetch = FetchType.EAGER)
    var address: AddressEntity? = null
)
