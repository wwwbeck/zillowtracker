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

    void persistListing(Listing listing, ListingDao listingDao) {
        listingRepository.createListing(listingDao)
    }

    ListingDao buildListingDao(Listing listing) {
        ListingDao listingDao = new ListingDao(
                listingId: UUID.randomUUID().toString().toUpperCase(),
                address: listing.address.addressLine1,
                daysListed: listing.daysListed,
                created: new Date(),
                content: objectMapper.writeValueAsString(listing),
                rentalPrice: listing.price,
                version: 1
        )
        return listingDao
    }
}
