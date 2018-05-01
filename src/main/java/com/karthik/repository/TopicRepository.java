/**
 * 
 */
package com.karthik.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.karthik.model.Topic;

/**
 * @author karthikchejerla
 *
 */
@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {
	
}
