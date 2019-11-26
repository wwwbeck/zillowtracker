package com.zillow.tracker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.zillow.tracker.dao.ListingDao
import com.zillow.tracker.domain.Listing
import com.zillow.tracker.domain.ListingControls
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
    ListingControls createListing(@RequestBody(required = true) String listingString) {
        Listing listing = objectMapper.readValue(listingString, Listing)
        ListingDao listingDao = listingService.buildListingDao(listing)
        listingService.persistListing(listing, listingDao)
        ListingControls listingControls = listingService.buildControls(listingDao)
        return listingControls
    }
    @RequestMapping(value = '/listing', method = [RequestMethod.GET], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    ListingControls getAll(@RequestBody(required = true) String listingString) {

    }
    @RequestMapping(value = '/listing/{listingId}', method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    ListingDao getListing(@PathVariable(required = true) String listingId) {
        def listing = listingService.getListing(listingId)
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
