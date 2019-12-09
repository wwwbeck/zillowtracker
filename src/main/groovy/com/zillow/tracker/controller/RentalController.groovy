package com.zillow.tracker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.zillow.tracker.dao.ListingDao
import com.zillow.tracker.domain.Listing
import com.zillow.tracker.domain.ListingEstate
import com.zillow.tracker.service.ListingService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RequestMapping('/rental')
@RestController
@Slf4j
@CompileStatic
class RentalController {

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    ListingService listingService

    @RequestMapping(value = '/listing', method = [RequestMethod.POST], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    Listing createListing(@RequestBody(required = true) String listingString) {
        ListingEstate listing = objectMapper.readValue(listingString, ListingEstate)
        ListingDao listingDao = listingService.buildListingDao(listing)
        listingService.persistListing(listing, listingDao)
        Listing listingControls = listingService.buildListing(listingDao)
        return listingControls
    }

    @RequestMapping(value = '/listing', method = [RequestMethod.GET], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    List<Listing> getListings() {
        return listingService.getListings()
    }

    @RequestMapping(value = '/listing/{listingId}', method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    ListingDao getListing(@PathVariable(required = true) String listingId) {
        ListingDao listing = listingService.getListing(listingId)
        return listing
    }

    @RequestMapping(value = '/listing/{listingId}', method = [RequestMethod.DELETE], produces = [MediaType.TEXT_PLAIN_VALUE])
    String deleteListing(@PathVariable(required = true) String listingId) {
        try {
            listingService.removeListing(listingId)
            return "Removed listing with listingID ${listingId}"
        }
        catch (Exception e) {
            log.error('Could not remove listing from cassandra. ' + e)
            return "Could not removed the listing with listingID ${listingId}"
        }
    }
}
