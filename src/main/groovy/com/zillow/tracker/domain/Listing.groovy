package com.zillow.tracker.domain

import java.time.LocalDateTime

class Listing {
    Address address
    BigDecimal price
    Property property
    Integer daysListed
    LocalDateTime created
    Integer listingId
}
