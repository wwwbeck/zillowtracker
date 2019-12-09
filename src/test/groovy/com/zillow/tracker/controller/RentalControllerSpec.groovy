package com.zillow.tracker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.zillow.tracker.domain.Address
import com.zillow.tracker.domain.ListingEstate
import com.zillow.tracker.domain.Property
import spock.lang.Specification

class RentalControllerSpec extends Specification {

    ObjectMapper objectMapper
    RentalController rentalController

    def setup() {
        objectMapper = Mock(ObjectMapper)
        rentalController = new RentalController(
                objectMapper: objectMapper
        )
    }

    def 'getListings'() {
        rentalController.getListing('Minneapolis')
    }

    def 'createListings'() {
        given:
            String listingString = 'some string'
            ListingEstate listing = new ListingEstate(
                    address: new Address(state: 'MN', city: 'Minneapolis', addressLine1: '123 First Ave South', addressLine2: 'apt 123'),
                    price: 1200.00,
                    property: new Property(bedrooms: 4, bathrooms: 5, sqft: 9876),
                    daysListed: 5
            )
        when:
            ListingEstate createdListing = rentalController.createListing(listingString)
        then:
            1 * objectMapper.readValue(listingString, ListingEstate) >> listing
        then:
            createdListing == listing
    }
}
