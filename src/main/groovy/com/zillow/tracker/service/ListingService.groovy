package com.zillow.tracker.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.zillow.tracker.dao.ListingDao
import com.zillow.tracker.domain.Listing
import com.zillow.tracker.repository.ListingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ListingService {
    @Autowired
    ListingRepository listingRepository

    @Autowired
    ObjectMapper objectMapper

    Listing processListing(Listing listing) {
        ListingDao listingDao = new ListingDao(
                listingId: '',
                address: listing.address.addressLine1,
                daysListed: listing.daysListed,
                created: new Date(),
                content: objectMapper.writeValueAsString(listing),
                rentalPrice: listing.price,
                version: 1
        )
        listingRepository.createListing(listingDao)
    }
}
