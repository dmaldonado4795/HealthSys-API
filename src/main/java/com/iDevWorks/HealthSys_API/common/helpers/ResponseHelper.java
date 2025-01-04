package com.iDevWorks.HealthSys_API.common.helpers;

/**
 * Helper class for creating standardized response messages and managing response keys.
 * Provides utility methods for generating error or informational messages,
 * as well as defining constants for response structure.
 */
public final class ResponseHelper {
    /**
     * Key used for storing data in a response.
     */
    public static final String DATA_KEY = "data";
    /**
     * Key used for storing errors in a response.
     */
    public static final String ERROR_KEY = "error";

    /**
     * Generates a message indicating that no registered item was found for the given parameter.
     *
     * @param param the name of the item that is not registered.
     * @return a formatted string message, e.g., "No registered {param} found".
     */
    public static String NoRegisteredItem(String param) {
        return String.format("No registered %s found", param);
    }

    /**
     * Generates a message indicating that the specified item was not found.
     *
     * @param param the name of the item that was not found.
     * @return a formatted string message, e.g., "{param} not found".
     */
    public static String ItemNotFound(String param) {
        return String.format("%s not found", param);
    }
}
