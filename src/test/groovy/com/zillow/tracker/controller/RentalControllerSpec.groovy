package com.zillow.tracker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.zillow.tracker.domain.Address
import com.zillow.tracker.domain.Listing
import com.zillow.tracker.domain.Property
import org.springframework.beans.factory.annotation.Autowired
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
            Listing listing = new Listing(
                    address: new Address(state: 'MN', city: 'Minneapolis', addressLine1: '123 First Ave South', addressLine2: 'apt 123'),
                    price: 1200.00,
                    property: new Property(bedrooms: 4, bathrooms: 5, sqft: 9876),
                    daysListed: 5
            )
        when:
            Listing createdListing = rentalController.createListing(listingString)
        then:
            1 * objectMapper.readValue(listingString, Listing) >> listing
        then:
            createdListing == listing
    }
}
