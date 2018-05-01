/**
 * 
 */
package com.karthik.service;

import java.util.ArrayList;
import java.util.List;
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
		return topic.get();
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
}
