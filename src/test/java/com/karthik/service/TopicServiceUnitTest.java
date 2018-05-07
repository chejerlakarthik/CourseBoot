/**
 * 
 */
package com.karthik.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.karthik.exception.TopicDoesNotExistException;
import com.karthik.model.Topic;
import com.karthik.repository.TopicRepository;

/**
 * @author karthikchejerla
 *
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class TopicServiceUnitTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
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
	
	@Test
	public void testGetOneTopic() throws TopicDoesNotExistException{
		Topic topic = new Topic(1, "Test Name", "Test Description");
		
		when(topicRepository.findById(any(Long.class))).thenReturn(Optional.of(topic));
		
		Topic retrievedTopic = topicService.getTopic(100L);
		
		assertNotNull(retrievedTopic);
		assertEquals(retrievedTopic.getId(), 1);
		assertEquals(retrievedTopic.getName(), "Test Name");
		assertEquals(retrievedTopic.getDescription(), "Test Description");
	}
	
	/**
	 * Testing exception using 'expected' attribute
	 * @throws TopicDoesNotExistException
	 */
	@Test(expected=TopicDoesNotExistException.class)
	public void testTopicDoesNotExistExceptionIsThrown() throws TopicDoesNotExistException {
		when(topicRepository.findById(100L)).thenReturn(Optional.empty());
		topicService.getTopic(100L);
	}
	
	/**
	 * Testing exception with ExpectedException
	 * @throws TopicDoesNotExistException
	 */
	@Test
	public void testTopicDoesNotExistExceptionIsThrownDuplicate() throws TopicDoesNotExistException {
		when(topicRepository.findById(100L)).thenReturn(Optional.empty());
		
		thrown.expect(TopicDoesNotExistException.class);
		thrown.expectMessage("Topic '100' does not exist");
		
		topicService.getTopic(100);
	}
	
	@Test
	public void testUpdateTopic() throws TopicDoesNotExistException {
		Topic topic = new Topic(1, "Updated Name", "Updated Description");
		when(topicRepository.save(any(Topic.class))).thenReturn(topic);
		
		topicService.updateTopic(new Topic(), 1);	
	}
	
	@Test
	public void testIsValid() {
		Topic topic1 = new Topic();
		topic1.setName(null);
		
		Topic topic2 = new Topic();
		topic2.setName("Topic2");
		topic2.setDescription("Topic2 description");
		
		Topic topic3 = new Topic();
		topic3.setName("Topic3");
		topic3.setDescription(null);
		
		assertThat(topicService.isValid(topic1)==false);
		assertThat(topicService.isValid(topic2)==true);
		assertThat(topicService.isValid(topic3)==false);
	}
	
	@Test
	public void testExistsPositiveScenario() throws TopicDoesNotExistException {
		Topic topic = new Topic(1, "Mock Topic", "Mock Description");
		when(topicRepository.findById(any(Long.class))).thenReturn(Optional.of(topic));
		
		assertThat(topicService.exists(99)==true);
	}
	
	@Test
	public void testExistsNegativeScenario() throws TopicDoesNotExistException {
		when(topicRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		
		thrown.expect(TopicDoesNotExistException.class);
		thrown.expectMessage("Topic '99' does not exist");
		
		assertThat(topicService.exists(99)==false);
	}
}
