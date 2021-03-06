package com.karthik.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karthik.exception.CourseDoesNotExistException;
import com.karthik.model.Course;
import com.karthik.repository.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	public List<Course> getAllCourses(long topicId) {
		List<Course> courses = courseRepository.findByTopicId(topicId);
		return courses;
	}

	public Course getCourse(long topicId, long courseId) throws CourseDoesNotExistException {
		List<Course> courses = courseRepository.findByTopicId(topicId);
		try {
			return courses.stream().filter(t -> t.getId() == courseId).findFirst().get();
		}
		catch(NoSuchElementException ex) {
			throw new CourseDoesNotExistException(courseId, topicId);
		}
	}
	
	public void updateCourse(Course course) {
		courseRepository.save(course);
	}
	
	public void deleteCourse(long topicId, long courseId) throws CourseDoesNotExistException {
		Course course = this.getCourse(topicId, courseId);
		if (course != null) {
			courseRepository.delete(course);
		}
	}
	
	public Course addCourse(Course course) {
		Course savedCourse = courseRepository.save(course);
		return savedCourse;
	}
	
	/**
	 * Check if a course exists with an Id matching courseId for a topic
	 * 
	 * @param topicId
	 *            - the input topicId
	 * @return
	 * @throws CourseDoesNotExistException
	 */
	public boolean exists(long topicId, long courseId) throws CourseDoesNotExistException {
		boolean exists = false;
		if (courseId == 0) {
			exists = false;
		} else {
			Course course = this.getCourse(topicId, courseId);
			if (null != course) {
				exists = true;
			}
		}
		return exists;
	}
	
	/**
	 * Determine if the passed topic object is valid or not
	 * @param topic
	 * @return
	 */
	public boolean isValid(Course course){
		boolean isValid = false;
		if (course.getName() != null && course.getDescription()!= null){
			isValid = true;
		}
		return isValid;
	}
}
