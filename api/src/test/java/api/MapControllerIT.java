package api;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import utils.IntegrationTest;

import config.web.WebConfiguration;

/**
 * 
 * @author Aleksey Shishkin
 *
 */
@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class MapControllerIT implements IntegrationTest{
	
	private static final String ALLOW = "Allow";
	private static final String TEST = "test";

	private MockMvc mockMvc;
	
	// According to spring concept we shouldn't test functionality in a ServletContainer as spring doesn't provide such mocks!
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	private byte[] getTestBytes() {
		byte[] bytes = TEST.getBytes();
		byte[] result = new byte[bytes.length * 2];
		int i = 1;
		for (byte b : bytes) {
			result[i] = b;
			i = i + 2;
		}
		return result;
	}

	@Test
	public void headTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.head("http://localhost:11080/map"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void simpleTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.options("http://localhost:11080/map"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.header().string(ALLOW, "GET,HEAD,OPTIONS"));
	}
	
	@Test
	public void getObjectOptionsTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.options("http://localhost:11080/map/object"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.header().string(ALLOW, "GET,OPTIONS"));
	}
	
	@Test
	public void getMaterialOptionsTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.options("http://localhost:11080/map/material"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.header().string(ALLOW, "GET,OPTIONS"));
	}
	
	@Test
	public void getSpriteOptionsTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.options("http://localhost:11080/map/sprite"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.header().string(ALLOW, "GET,OPTIONS"));
	}
	
	@Test
	public void getSupportedObjectTypesOptionsTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.options("http://localhost:11080/map/supported/object/types"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.header().string(ALLOW, "GET,OPTIONS"));
	}
	
	@Test
	public void getSupportedObjectTypesTest() throws Exception {
		String response = "[\"THREE_D_OBJECT\",\"SPRITE\"]";
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:11080/map/supported/object/types"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().bytes(response.getBytes()));
	}
	
	@Test
	public void simpleMapTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:11080/map/10/1/1"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void objectLoadingTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:11080/map/object/test"))
		.andExpect(MockMvcResultMatchers.content().contentType("application/x-tgif"))
		.andExpect(MockMvcResultMatchers.content().bytes(getTestBytes()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void materialLoadingTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:11080/map/material/test"))
		.andExpect(MockMvcResultMatchers.content().contentType("application/x-tgif"))
		.andExpect(MockMvcResultMatchers.content().bytes(getTestBytes()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void spriteLoadingTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:11080/map/sprite/test-sprite-1"))
		.andExpect(MockMvcResultMatchers.content().contentType("application/x-tgif"))
		.andExpect(MockMvcResultMatchers.content().bytes(getTestBytes()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
