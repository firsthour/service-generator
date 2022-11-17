package net.firsthour.gen.generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.firsthour.gen.model.QueryParameter;
import net.firsthour.gen.model.Service;

public class ServiceMethodGeneratorTest {
	
	private Service service;
	private ServiceMethodGenerator generator;
	
	@Before
	public void before() {
		service = new Service();
		generator = new ServiceMethodGenerator(service);
	}
	
	@Test
	public void calculateMethodName() {
		service.setPath("test");
		assertEquals("test", generator.calculateMethodName());
		
		service.setPath("test/");
		assertEquals("test", generator.calculateMethodName());
		
		service.setPath("/test");
		assertEquals("test", generator.calculateMethodName());
		
		service.setPath("test//////");
		assertEquals("test", generator.calculateMethodName());
		
		service.setPath("test/path");
		assertEquals("testPath", generator.calculateMethodName());
		
		service.setPath("test/path/a/b/c/4////x////");
		assertEquals("testPathABC4X", generator.calculateMethodName());
		
		service.setPath("abc!@#$%^&*()_+~|`[]{}|\\;':\"<>?,./XYZ1234567890");
		assertEquals("abc_XYZ1234567890", generator.calculateMethodName());
	}
	
	@Test
	public void hasFormParameter() {
		service.setMethod("GET");
		assertFalse(generator.hasPostParameter());
		
		service.setMethod("POST");
		assertFalse(generator.hasPostParameter());
		
		service.setPostParameterName("name");
		assertFalse(generator.hasPostParameter());
		
		service.setPostParameterType("type");
		assertTrue(generator.hasPostParameter());
		
		service.setPostParameterName(null);
		assertFalse(generator.hasPostParameter());
	}
	
	@Test
	public void hasQueryParameters() {
		assertFalse(generator.hasQueryParameters());
		
		service.setQueryParameters(new ArrayList<>());
		assertFalse(generator.hasQueryParameters());
		
		service.setQueryParameters(List.of(new QueryParameter("query", "param")));
		assertTrue(generator.hasQueryParameters());
	}
	
	@Test
	public void generate_post() {
		service.setMethod("POST");
		service.setIncludeConsumes(true);
		service.setIncludeProduces(true);
		service.setPath("/path/to/here");
		service.setReturnClass("void");
		service.setPostParameterName("name2");
		service.setPostParameterType("SuperType");
		service.setQueryParameters(List.of(new QueryParameter("checked", "boolean"), new QueryParameter("type", "String")));
		
		assertEquals("Service [method=POST, path=/path/to/here, returnClass=void, includeConsumes=true, includeProduces=true, "
				+ "queryParameters=[QueryParameter [id=0, name=checked, type=boolean], QueryParameter [id=0, name=type, type=String]], "
				+ "postParameterName=name2, postParameterType=SuperType]", service.toString());
		
		assertEquals("@POST\r\n"
				+ "@Path(\"/path/to/here\")\r\n"
				+ "@Consumes({MediaType.APPLICATION_JSON})\r\n"
				+ "@Produces({MediaType.APPLICATION_JSON})\r\n"
				+ "public void pathToHere(SuperType name2, @QueryParam(\"checked\") boolean checked, @QueryParam(\"type\") String type) {\r\n"
				+ "	//TODO\r\n"
				+ "}\r\n"
				+ "",
			generator.generate());
	}
	
	@Test
	public void generate_get() {
		service.setMethod("GET");
		service.setIncludeConsumes(false);
		service.setIncludeProduces(false);
		service.setPath("/path/to/here");
		service.setReturnClass("SuperType");
		service.setPostParameterName("name2");
		service.setPostParameterType("SuperType");
		service.setQueryParameters(List.of(new QueryParameter("checked", "boolean"), new QueryParameter("type", "String")));
		
		assertEquals("@GET\r\n"
				+ "@Path(\"/path/to/here\")\r\n"
				+ "public SuperType pathToHere(@QueryParam(\"checked\") boolean checked, @QueryParam(\"type\") String type) {\r\n"
				+ "	//TODO\r\n"
				+ "}\r\n"
				+ "",
			generator.generate());
	}
}
