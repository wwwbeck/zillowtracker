package com.zillow.tracker.dao

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table

@Table('property_listing')
class ListingDao {

    @PrimaryKey
    String listingId
    @Column
    String address
    @Column
    String daysListed
    @Column
    Date created
    @Column
    String content
    @Column
    String rentalPrice
    @Column
    Integer version
}
