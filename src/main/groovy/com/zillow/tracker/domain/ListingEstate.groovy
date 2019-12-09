package com.zillow.tracker.domain

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table('property_listing')
class ListingEstate {
    @PrimaryKey
    String listingId

    @Column
    String address

    @Column
    Integer daysListed

    @Column
    Date created

    @Column
    String content

    @Column
    String rentalPrice

    @Column
    Integer version
}
