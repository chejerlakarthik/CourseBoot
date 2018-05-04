/**
 * 
 */
package com.karthik.exception;

/**
 * @author H158574
 *
 */
public final class TopicDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6243418813814723401L;
	
	private static final String MESSAGE_FORMAT = "Topic '%d' does not exist";

    public TopicDoesNotExistException(long topicId) {
        super(String.format(MESSAGE_FORMAT, topicId));
    }

}
