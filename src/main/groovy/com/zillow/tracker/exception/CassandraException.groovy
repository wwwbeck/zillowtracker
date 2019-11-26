package com.zillow.tracker.exception

import groovy.transform.CompileStatic

@CompileStatic
class CassandraException extends RuntimeException {
    CassandraException(String message, Throwable throwable) {
        super(message, throwable)
    }
}
