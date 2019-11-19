package com.zillow.tracker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.zillow.tracker.dao.ListingDao
import com.zillow.tracker.domain.Listing
import com.zillow.tracker.service.ListingService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping('/rental')
@RestController
@CompileStatic
class RentalController {

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    ListingService listingService

    @RequestMapping(value = '/cities/{city}', method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    Listing getListing(@PathVariable(required = true) String city) {
        return new Listing()
    }

    @RequestMapping(value = '/createListing', method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    Listing createListing(@RequestBody(required = true) String listingString) {
        Listing listing = objectMapper.readValue(listingString, Listing)
        ListingDao listingDao = listingService.buildListingDao(listing)
        listingService.persistListing(listing, listingDao)
        return listing
    }
}
