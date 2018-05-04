/**
 * 
 */
package com.karthik.exception;

/**
 * @author H158574
 *
 */
public final class CourseDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8987083706599599956L;
	
	private static final String MESSAGE_FORMAT = "Course '%d' for Topic '%d' does not exist";
	
	public CourseDoesNotExistException(long courseId, long topicId) {
        super(String.format(MESSAGE_FORMAT, courseId, topicId));
    }

}
