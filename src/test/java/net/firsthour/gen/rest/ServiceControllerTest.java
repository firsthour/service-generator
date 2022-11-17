package net.firsthour.gen.rest;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import net.firsthour.gen.repo.ServiceRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class ServiceControllerTest {
	
	@MockBean
	private ServiceRepository serviceRepository;
	
	@Autowired
	ServiceController serviceController;

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void save_okay() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/services/save")
			.content("{\"returnClass\":\"void\",\"includeConsumes\":true,\"includeProduces\":true,\"queryParameters\":[],\"method\":\"GET\",\"path\":\"get\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void save_missingReturnClass() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/services/save")
			.content("{\"includeConsumes\":true,\"includeProduces\":true,\"queryParameters\":[],\"method\":\"GET\",\"path\":\"get\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.returnClass", Is.is("returnClass is required")));
	}
	
	@Test
	public void generate_okay() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/services/generate")
			.content("{\"returnClass\":\"void\",\"includeConsumes\":true,\"includeProduces\":true,\"queryParameters\":[],\"method\":\"GET\",\"path\":\"get\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void generate_missingReturnClass() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/services/generate")
			.content("{\"includeConsumes\":true,\"includeProduces\":true,\"queryParameters\":[],\"method\":\"GET\",\"path\":\"get\"}")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.returnClass", Is.is("returnClass is required")));
	}
	
	@Test
	public void getServices() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/services")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
