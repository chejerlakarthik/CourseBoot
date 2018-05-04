package com.karthik.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.karthik.exception.CourseDoesNotExistException;
import com.karthik.exception.ErrorPayload;
import com.karthik.exception.TopicDoesNotExistException;
import com.karthik.model.Course;
import com.karthik.service.CourseService;
import com.karthik.service.TopicService;
import com.karthik.util.ObjectUtil;

@RestController
public class CourseController {

	@Autowired
	private TopicService topicService;
	@Autowired
	private CourseService courseService;

	@RequestMapping(value = "/topics/{id}/courses", method = RequestMethod.GET)
	public ResponseEntity<List<Course>> getAllCoursesForTopic(@PathVariable long id) {
		List<Course> courses = courseService.getAllCourses(id);
		ResponseEntity<List<Course>> response = null;
		if (ObjectUtil.isNotNull(courses)) {
			response = new ResponseEntity<List<Course>>(courses, HttpStatus.OK);
		} else {
			response = new ResponseEntity<List<Course>>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@RequestMapping(value = "/topics/{topicId}/courses/{id}", method = RequestMethod.GET)
	public ResponseEntity<Course> getCourseById(@PathVariable long topicId, @PathVariable long id) throws CourseDoesNotExistException {
		ResponseEntity<Course> response = null;
		Course course = courseService.getCourse(topicId, id);
		if (ObjectUtil.isNotNull(course)) {
			response = new ResponseEntity<Course>(course, HttpStatus.OK);
		} else {
			response = new ResponseEntity<Course>(HttpStatus.OK);
		}
		return response;
	}
	
	@RequestMapping(value = "/topics/{topicId}/courses/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Course> updateCourseForTopic(@PathVariable long topicId, @PathVariable long id,
			@RequestBody Course course) throws TopicDoesNotExistException, CourseDoesNotExistException {
		ResponseEntity<Course> response = null;
		if (topicService.exists(topicId) && courseService.exists(topicId, id)) {
			course.setTopic(topicService.getTopic(topicId));
			courseService.updateCourse(course);
			response = new ResponseEntity<Course>(course, HttpStatus.OK);
		} else {
			response = new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@RequestMapping(value = "/topics/{topicId}/courses/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCourseForTopic(@PathVariable long id, @PathVariable long topicId) throws TopicDoesNotExistException, CourseDoesNotExistException {
		ResponseEntity<String> response = null;
		if (topicService.exists(topicId)) {
			if (courseService.exists(topicId, id)) {
				courseService.deleteCourse(topicId, id);
				response = new ResponseEntity<String>(HttpStatus.OK);
			} else {
				response = new ResponseEntity<String>("Course not found", HttpStatus.NOT_FOUND);
			}
		} else {
			response = new ResponseEntity<String>("Topic not found", HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@RequestMapping(value = "/topics/{id}/courses", method = RequestMethod.POST)
	public ResponseEntity<Course> addCourseForTopic(@RequestBody Course course, @PathVariable long id,
			UriComponentsBuilder uriBuilder) throws TopicDoesNotExistException, CourseDoesNotExistException {
		ResponseEntity<Course> response = null;

		if (topicService.exists(id)) {
			if (!courseService.exists(id, course.getId())) {
				course.setTopic(topicService.getTopic(id));
				if (courseService.isValid(course)) {
					courseService.addCourse(course);
					Map<String, Long> uriVariables = new HashMap<String, Long>();
					uriVariables.put("topicId", id);
					uriVariables.put("courseId", course.getId());
					HttpHeaders headers = new HttpHeaders();
					headers.setLocation(uriBuilder.path("/topics/{topicId}/courses/{courseId}")
							.buildAndExpand(uriVariables).toUri());
					response = new ResponseEntity<Course>(course, headers, HttpStatus.CREATED);
				} else {
					response = new ResponseEntity<Course>(HttpStatus.BAD_REQUEST);
				}

			}
		} else {
			response = new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
		}
		return response;
	}
	
	@ExceptionHandler(CourseDoesNotExistException.class)
	ResponseEntity<ErrorPayload> handleNotFounds(Exception e){
		ErrorPayload payload = new ErrorPayload();
		payload.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
		payload.setErrorMessage(e.getMessage());
		return new ResponseEntity<ErrorPayload>(payload, HttpStatus.NOT_FOUND);
	}
}
