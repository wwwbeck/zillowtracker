package com.zillow.tracker.repository

import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core.querybuilder.Select
import com.zillow.tracker.dao.ListingDao
import com.zillow.tracker.domain.ListingEstate
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


    /**
     * Data Access using CassandraTemplate
     * @param listingDao
     */
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
            throw new CassandraException('Failed SELECT operation', e)
        }
    }

    ListingEstate getListing(listingId) {
        return cassandraOperations.selectOneById(listingId, ListingEstate)
    }

    /**
     * Data Access using QueryBuilder
     * @return
     */
    List<ListingEstate> getListings() {
        Select select = QueryBuilder.select('listingid', 'address', 'content', 'created', 'dayslisted', 'rentalprice', 'version').from('property_listing')
        return cassandraOperations.select(select, ListingEstate)
    }
}
