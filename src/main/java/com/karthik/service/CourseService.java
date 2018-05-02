package com.karthik.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Course getCourse(long topicId, long courseId) {
		List<Course> courses = courseRepository.findByTopicId(topicId);
		try {
			return courses.stream().filter(t -> t.getId() == courseId).findFirst().get();
		}
		catch(NoSuchElementException ex) {
			return null;
		}
	}
	
	public void updateCourse(Course course) {
		courseRepository.save(course);
	}
	
	public void deleteCourse(long topicId, long courseId) {
		Course course = this.getCourse(topicId, courseId);
		if (course != null) {
			courseRepository.delete(course);
		}
	}
	
	public void addCourse(Course course) {
		courseRepository.save(course);
	}
}
