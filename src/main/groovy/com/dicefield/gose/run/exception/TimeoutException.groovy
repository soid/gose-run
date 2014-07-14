package com.dicefield.gose.run.exception

/**
 * Description
 */
class TimeoutException extends Exception {

    TimeoutException(String message) {
        super(message)
    }

    TimeoutException(String message, Throwable cause) {
        super(message, cause)
    }
}
