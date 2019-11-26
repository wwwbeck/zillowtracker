package com.zillow.tracker.repository

import com.zillow.tracker.dao.ListingDao
import com.zillow.tracker.domain.Listing
import com.zillow.tracker.exception.CassandraException
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.cassandra.core.CassandraOperations
import org.springframework.stereotype.Component

@Component
@Slf4j
class ListingRepository {
    @Autowired
    CassandraOperations cassandraOperations

    void createListing(ListingDao listingDao) {
        try {
            cassandraOperations.insert(listingDao)
        }
        catch (Exception e) {
            log.error('Failed cql insert operation. ' + e)
        }
    }

    void deleteListing(String listingId) {
        try {
            cassandraOperations.deleteById(listingId, ListingDao)
        }
        catch (Exception e) {
            log.error('Failed delete cql operation. ' + e)
        }
    }

    ListingDao getListing(listingId) {
        try {
            return cassandraOperations.selectOneById(listingId, ListingDao)
        }
        catch (Exception e) {
            log.error('Failed select cql operation. ' + e)
            throw new CassandraException('Failed SELECT operation', e)
        }
    }
}
