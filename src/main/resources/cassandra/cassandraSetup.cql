CREATE KEYSPACE IF NOT EXISTS zillowtracker WITH REPLICATION = { 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE IF NOT EXISTS zillowtracker.listing (
    listingId       text,
    address         text,
    rentalPrice     text,
    daysListed      int,
    created         timestamp,
    content         text,
    version         int,
PRIMARY KEY (listingId));

CREATE TABLE IF NOT EXISTS zillowtracker.property (
    bedrooms        int,
    bathroom        int,
    sqft            int,
    listingId       text,
PRIMARY KEY (listingId));
