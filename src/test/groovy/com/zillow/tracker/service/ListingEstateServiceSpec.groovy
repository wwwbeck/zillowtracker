package com.zillow.tracker.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.zillow.tracker.dao.ListingDao
import com.zillow.tracker.domain.Address
import com.zillow.tracker.domain.ListingEstate
import com.zillow.tracker.repository.ListingRepository
import spock.lang.Specification

class ListingEstateServiceSpec extends Specification {
    ObjectMapper objectMapper
    ListingRepository listingRepository
    ListingService listingService

    def setup() {
        objectMapper = Mock(ObjectMapper)
        listingRepository = Mock(ListingRepository)
        listingService = new ListingService(objectMapper: objectMapper, listingRepository: listingRepository)
    }

    def 'persist listing'() {
        given:
            ListingEstate listing = new ListingEstate(price: 123.12, daysListed: 4, address: new Address(addressLine1: '123 First St. S'))
            ListingDao listingDao = new ListingDao(
                    listingId: '',
                    address: listing.address.addressLine1,
                    daysListed: listing.daysListed,
                    created: new Date(),
                    content: objectMapper.writeValueAsString(listing),
                    rentalPrice: listing.price,
                    version: 1
            )
        when:
            ListingEstate createdListing = listingService.persistListing(listing)
        then:
            1 * listingRepository.createListing(listingDao) >> listing
        then:
            createdListing
    }
}
