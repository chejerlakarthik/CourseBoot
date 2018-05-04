/**
 * 
 */
package com.karthik.service.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.karthik.model.Topic;
import com.karthik.service.TopicService;

/**
 * @author karthikchejerla
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.NONE)
public class TopicServiceIntegrationTest {
	
	@Autowired
	private TopicService topicService;
	
	@Test
	public void testAddNewTopic() {
		Topic newTopic = new Topic();
		newTopic.setName("JUnit");
		newTopic.setDescription("JUnit Test Framework");
		
		topicService.addTopic(newTopic);
		
		assertNotNull(newTopic);
		assertNotNull(newTopic.getId());
		assertEquals("JUnit", newTopic.getName());
		assertEquals("JUnit Test Framework", newTopic.getDescription());
	}

}
