package com.zillow.tracker.repository

import com.zillow.tracker.dao.ListingDao
import com.zillow.tracker.domain.Listing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.cassandra.core.CassandraOperations
import org.springframework.stereotype.Component

@Component
class ListingRepository {
    @Autowired
    CassandraOperations cassandraOperations

    void createListing(ListingDao listingDao) {
        try {
            cassandraOperations.insert(listingDao)
        }
        catch (Exception e) {
            "add some loggin"
        }

    }
}
