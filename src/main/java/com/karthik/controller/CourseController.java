package com.karthik.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.karthik.model.Course;
import com.karthik.model.Topic;
import com.karthik.service.CourseService;
import com.karthik.service.TopicService;

@RestController
public class CourseController {

	@Autowired
	private TopicService topicService;
	@Autowired
	private CourseService courseService;

	@RequestMapping(value = "/topics/{id}/courses", method = RequestMethod.GET)
	public List<Course> getAllCoursesForTopic(@PathVariable long id) {
		return courseService.getAllCourses(id);
	}

	@RequestMapping(value = "/topics/{topicId}/courses/{id}", method = RequestMethod.GET)
	public ResponseEntity<Course> getCourseById(@PathVariable long topicId, @PathVariable long id) {
		Course course = courseService.getCourse(topicId, id);
		if (null == course) {
			return new ResponseEntity<Course>(HttpStatus.OK);
		}
		return new ResponseEntity<Course>(course, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/topics/{topicId}/courses/{id}", method = RequestMethod.PUT)
	public void updateCourseForTopic(@PathVariable long topicId, @PathVariable long id, @RequestBody Course course) {
		if (topicService.exists(topicId) && courseService.exists(topicId, id)) {
			course.setTopic(topicService.getTopic(topicId));
			courseService.updateCourse(course);
		}
	}
	
	@RequestMapping(value = "/topics/{topicId}/courses/{id}", method = RequestMethod.DELETE)
	public void deleteCourseForTopic(@PathVariable long id, @PathVariable long topicId) {
		if (topicService.exists(topicId) && courseService.exists(topicId, id)) {
			courseService.deleteCourse(topicId, id);
		}
	}
	
	@RequestMapping(value = "/topics/{id}/courses", method = RequestMethod.POST)
	public void addCourseForTopic(@RequestBody Course course, @PathVariable long id) {
		Topic topic = topicService.getTopic(id);
		Course existingCourse = courseService.getCourse(id, course.getId());
		if (topic != null && existingCourse == null) {
			course.setTopic(topic);
			courseService.addCourse(course);
		}
	}
}
