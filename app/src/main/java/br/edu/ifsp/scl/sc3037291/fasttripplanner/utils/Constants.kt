package br.edu.ifsp.scl.sc3037291.fasttripplanner.utils

/**
 * Object containing keys used to pass data between Activities via Intent.
 * Centralizing these keys improves maintainability.
 */
object IntentKeys {
    /** The trip destination name (String) */
    const val DESTINATION = "EXTRA_DESTINATION"
    
    /** Number of days for the trip (Int) */
    const val DAYS = "EXTRA_DAYS"
    
    /** Daily budget amount (Double/Float) */
    const val DAILY_BUDGET = "EXTRA_DAILY_BUDGET"
    
    /** Type of hosting/accommodation (String) */
    const val HOSTING_TYPE = "EXTRA_HOSTING_TYPE"
    
    /** Whether transport is included (Boolean) */
    const val HAS_TRANSPORT = "EXTRA_HAS_TRANSPORT"
    
    /** Whether food/meals are included (Boolean) */
    const val HAS_FOOD = "EXTRA_HAS_FOOD"
    
    /** Whether tours are included (Boolean) */
    const val HAS_TOURS = "EXTRA_HAS_TOURS"
}
