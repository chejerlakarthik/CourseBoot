/**
 * 
 */
package com.karthik.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.karthik.service.model.Topic;

/**
 * @author karthikchejerla
 *
 */
@Service
public class TopicService {

	List<Topic> topics = new ArrayList<Topic>(Arrays.asList(new Topic("java", "Java Language", "Java 8 Features"),
			new Topic("spring", "Spring Framework", "Spring Boot Fundamentals"),
			new Topic("javascript", "Javascript", "Javascript closures")));

	public List<Topic> getTopics() {
		return topics;
	}

	public Topic getTopic(String topicId) {
		return topics.stream().filter(t -> t.getId().equals(topicId)).findFirst().get();
	}

	public List<Topic> addTopic(Topic topic) {
		topics.add(topic);
		return topics;
	}

	public Topic deleteTopic(String topicId) {
		Topic topicToDelete = getTopic(topicId);
		topics.remove(topicToDelete);
		return topicToDelete;
	}

	public Topic updateTopic(Topic topic, String topicId) {
		for(int i=0; i<topics.size(); i++) {
			Topic currentTopic = topics.get(i);
			if(currentTopic.getId().equals(topicId)) {
				topics.set(i, topic);
			}
		}
		return getTopic(topicId);
	}
}
