/**
 * 
 */
package com.karthik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karthik.exception.TopicDoesNotExistException;
import com.karthik.model.Topic;
import com.karthik.repository.TopicRepository;

/**
 * @author karthikchejerla
 *
 */
@Service
public class TopicService {

	@Autowired
	private TopicRepository topicRepository;

	public List<Topic> getAllTopics() {
		List<Topic> topics = new ArrayList<Topic>();
		topicRepository.findAll().forEach(topics::add);
		return topics;
	}

	public Topic getTopic(long topicId) throws TopicDoesNotExistException {
		Optional<Topic> topic = topicRepository.findById(topicId);
		try{
			return topic.get();
		} catch (NoSuchElementException ex){
			throw new TopicDoesNotExistException(topicId);
		}
	}

	public Topic addTopic(Topic topic) {
		Topic savedTopic = topicRepository.save(topic);
		return savedTopic;
	}

	public void deleteTopic(Long topicId) {
		topicRepository.deleteById(topicId);
	}

	public Topic updateTopic(Topic topic, long topicId) throws TopicDoesNotExistException {
		topic.setId(topicId);
		Topic updatedTopic = topicRepository.save(topic);
		return updatedTopic;
	}
	
	/**
	 * Check if a topic exists with an Id matching topicId
	 * @param topicId - the input topicId
	 * @return
	 * @throws TopicDoesNotExistException 
	 */
	public boolean exists(long topicId) throws TopicDoesNotExistException {
		boolean exists = false;
		Topic topic = this.getTopic(topicId);
		if (null != topic) {
			exists = true;
		}
		return exists;
	}
	
	/**
	 * Determine if the passed topic object is valid or not
	 * @param topic
	 * @return
	 */
	public boolean isValid(Topic topic){
		boolean isValid = false;
		if (topic.getName() != null && topic.getDescription()!= null){
			isValid = true;
		}
		return isValid;
	}
}
