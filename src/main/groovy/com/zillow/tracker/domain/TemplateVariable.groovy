package com.zillow.tracker.domain

import com.zillow.tracker.dao.ListingDao

enum TemplateVariable {

    LISTING_ID('listingId'){
        Object find(ListingDao listing) {
            return listing.listingId
        }
    }

    String value

    TemplateVariable(String templateValue) {
        this.value = templateValue
    }
}
