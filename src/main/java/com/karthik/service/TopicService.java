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

	public List<Topic> getTopics() {
		List<Topic> topics = new ArrayList<Topic>();
		topicRepository.findAll().forEach(topics::add);
		return topics;
	}

	public Topic getTopic(long topicId) {
		Optional<Topic> topic = topicRepository.findById(topicId);
		try{
			return topic.get();
		} catch (NoSuchElementException ex){
			return null;
		}
	}

	public List<Topic> addTopic(Topic topic) {
		topicRepository.save(topic);
		return getTopics();
	}

	public void deleteTopic(long topicId) {
		topicRepository.deleteById(topicId);
	}

	public Topic updateTopic(Topic topic, long topicId) {
		topic.setId(topicId);
		topicRepository.save(topic);
		return getTopic(topicId);
	}
	
	/**
	 * Check if a topic exists with an Id matching topicId
	 * @param topicId - the input topicId
	 * @return
	 */
	public boolean exists(long topicId) {
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
