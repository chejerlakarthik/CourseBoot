/**
 * 
 */
package com.karthik.service.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.karthik.model.Topic;
import com.karthik.repository.TopicRepository;
import com.karthik.service.TopicService;

/**
 * @author karthikchejerla
 *
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class TopicServiceUnitTest {
	
	@Mock
	private TopicRepository topicRepository;
	
	@InjectMocks
	private TopicService topicService;
	
	List<Topic> topics = new ArrayList<Topic>();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		// Prepare a list of topics
		this.topics  = Arrays.asList(new Topic(10, "JUnit", "JUnit Test Framework"),
								new Topic(11, "Mockito", "Mockito Mocking Framework"));
	}
	
	@Test
	public void testAddNewTopic() {
		// Create a mocked object for Mockito to return.
		Topic topic = new Topic();
		topic.setId(30);
		topic.setName("JUnit");
		topic.setDescription("JUnit Test Framework");
		
		// For any save operation on topicRepository object, return "topic" object
		when(topicRepository.save(any(Topic.class))).thenReturn(topic);
		
		// Invoke the method with an empty object. It should return the mocked object always.
		Topic savedTopic = topicService.addTopic(new Topic());
		
		// Verify that mocked object is being returned.
		assertNotNull(savedTopic);
		assertNotNull(savedTopic.getId());
		assertTrue(savedTopic.getId()>0);
		assertEquals(savedTopic.getName(), "JUnit");
		assertEquals(savedTopic.getDescription(), "JUnit Test Framework");
	}

	@Test
	public void testGetAllTopics() {
		//topics list from setup method will be used
		
		// Always return this list of topics for all get all topics method call.
		when(topicRepository.findAll()).thenReturn(this.topics);
		
		// Invoke the method
		List<Topic> retrievedTopics = topicService.getAllTopics();
		
		// Verify the mocked response.
		assertNotNull(retrievedTopics);
		assertEquals(retrievedTopics.size(), topics.size());
		assertEquals(retrievedTopics.get(0).getId(), 10);
		assertEquals(retrievedTopics.get(1).getId(), 11);
	}
	
	public void testGetOneTopic() {
		
	}
}
