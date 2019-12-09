package com.zillow.tracker.domain

enum TemplateVariable {

    LISTING_ID('listingId'){
        @Override
        Object find(Listing listing) {
            return listing.listingEstate.listingId
        }
    }

    String value

    private final static TemplateVariable[] STATIC_VALUES = TemplateVariable.values()

    static TemplateVariable from(String templateValue) {
        STATIC_VALUES.find {
            it.value == templateValue
        }
    }

    TemplateVariable(String templateValue) {
        this.value = templateValue
    }

    Object find(Object obj) {
        if (obj instanceof Listing) {
            find(obj as Listing)
        }
    }

    Object find(Listing listing) {
        null
    }
}
