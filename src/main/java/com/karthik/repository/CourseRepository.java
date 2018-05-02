package com.karthik.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.karthik.model.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
	public List<Course> findByTopicId(long topicId);
}
