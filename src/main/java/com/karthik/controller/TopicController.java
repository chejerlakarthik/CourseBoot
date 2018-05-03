package com.karthik.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.karthik.model.Topic;
import com.karthik.service.TopicService;
import com.karthik.util.ObjectUtil;

@RestController
public class TopicController {
	
	@Autowired
	private TopicService topicService;

	@RequestMapping(value = "/topics", method = RequestMethod.GET)
	public ResponseEntity<List<Topic>> getTopics() {
		ResponseEntity<List<Topic>> response = null;
		List<Topic> topics = topicService.getTopics();
		if (ObjectUtil.isNotNull(topics)) {
			response = new ResponseEntity<List<Topic>>(topics, HttpStatus.OK);
		} else {
			response = new ResponseEntity<List<Topic>>(new ArrayList<Topic>(), HttpStatus.OK);
		}
		return response;
	}

	@RequestMapping(value = "/topics/{id}", method = RequestMethod.GET)
	public ResponseEntity<Topic> getTopic(@PathVariable long id) {
		ResponseEntity<Topic> response = null;
		Topic topic = topicService.getTopic(id);
		if (ObjectUtil.isNotNull(topic)) {
			response = new ResponseEntity<Topic>(topic, HttpStatus.OK);
		} else {
			response = new ResponseEntity<Topic>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@RequestMapping(value = "/topics", method = RequestMethod.POST)
	public ResponseEntity<List<Topic>> addTopic(@RequestBody Topic topic) {
		ResponseEntity<List<Topic>> response = null;
		List<Topic> topics = topicService.addTopic(topic);
		if (ObjectUtil.isNotNull(topics)) {
			response = new ResponseEntity<List<Topic>>(topics, HttpStatus.OK);
		} else {
			response = new ResponseEntity<List<Topic>>(HttpStatus.OK);
		}
		return response;
	}

	@RequestMapping(value = "/topics/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteTopic(@PathVariable long id) {
		ResponseEntity<Void> response = null;
		if (topicService.exists(id)) {
			topicService.deleteTopic(id);
			response = new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			response = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@RequestMapping(value = "/topics/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Topic> updateTopic(@RequestBody Topic topic, @PathVariable long id) {
		ResponseEntity<Topic> response = null;
		if (topicService.exists(id)) {
			Topic updatedTopic = topicService.updateTopic(topic, id);
			if (ObjectUtil.isNotNull(updatedTopic)) {
				response = new ResponseEntity<Topic>(updatedTopic, HttpStatus.OK);
			} else {
				response = new ResponseEntity<Topic>(HttpStatus.BAD_REQUEST);
			}
		} else {
			response = new ResponseEntity<Topic>(HttpStatus.NOT_FOUND);
		}
		return response;
	}

}
