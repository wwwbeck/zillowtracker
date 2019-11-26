package com.zillow.tracker.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.zillow.tracker.dao.ListingDao
import com.zillow.tracker.domain.Listing
import com.zillow.tracker.domain.ListingControls
import com.zillow.tracker.repository.ListingRepository
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
@Slf4j
class ListingService {
    @Autowired
    ListingRepository listingRepository

    @Autowired
    ObjectMapper objectMapper

    Map<String, String> operationsMap

    @Value('${operation.template.listing}')
    private void setOperationsMap(String operationsMap) {
        this.operationsMap = (new JsonSlurper().parseText(operationsMap)) as Map
    }

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

    void removeListing(String listingId) {
        listingRepository.deleteListing(listingId)
    }

    ListingDao getListing(String listingId) {
        return listingRepository.getListing(listingId)
    }

    ListingControls buildControls(ListingDao listingDao) {
        String[] variables
        this.operationsMap.each {
            variables = StringUtils.substringBetween(it.value, '{', '}')
        }
//        variables.each {
//            TemplateVariable templateVariable = getParamFromObject(listing)
//            templateVariable.find(listingDao)
//        }
        return new ListingControls(listing: listingDao, operations: this.operationsMap)
    }
}
