package net.firsthour.gen.generation;

import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import net.firsthour.gen.model.Service;

public class ServiceMethodGenerator {
	
	private static final String INDENT = "\t";
	private static final String NEWLINE = System.lineSeparator();
	
	private final Service service;
	
	public ServiceMethodGenerator(final Service service) {
		this.service = service;
	}
	
	public String generate() {
		var builder = new StringBuilder()
			.append("@").append(service.getMethod().toUpperCase()).append(NEWLINE)
			.append("@Path(\"").append(service.getPath()).append("\")").append(NEWLINE);
		
		if(service.isIncludeConsumes()) {
			builder.append("@Consumes({MediaType.APPLICATION_JSON})").append(NEWLINE);
		}
		
		if(service.isIncludeProduces()) {
			builder.append("@Produces({MediaType.APPLICATION_JSON})").append(NEWLINE);
		}
		
		builder.append("public ").append(service.getReturnClass()).append(" ").append(calculateMethodName()).append("(");
		
		if(hasPostParameter()) {
			builder.append(service.getPostParameterType()).append(" ").append(service.getPostParameterName());
			
			if(hasQueryParameters()) {
				builder.append(", ");
			}
		}
		
		if(hasQueryParameters()) {
			final String delim = service.getQueryParameters().size() > 2 ? NEWLINE + INDENT : " ";
			builder.append(
				service.getQueryParameters().stream()
					.map(query -> query.getName() + "\") " + query.getType() + " " + query.getName())
					.collect(Collectors.joining(
						"," + delim + "@QueryParam(\"",
						"@QueryParam(\"",
						"")));
		}
		
		builder.append(") {").append(NEWLINE)
			.append(INDENT).append("//TODO").append(NEWLINE)
			.append("}").append(NEWLINE);
		
		return builder.toString();
	}
	
	protected String calculateMethodName() {
		String name = service.getPath();
		
		//remove all characters that aren't letters, numbers, underscores, and slashes
		name = name.replaceAll("[^a-zA-Z0-9\\/\\_]", "");
		
		//test if first character is /, if so drop it
		while("/".equals(name.substring(0, 1))) {
			name = name.substring(1);
		}
		
		//test if last character is /, if so drop it
		while("/".equals(name.substring(name.length() - 1, name.length()))) {
			name = name.substring(0, name.length() - 1);
		}
		
		//if we find a / anywhere else, get rid of it and make the next character upper case
		while(name.contains("/")) {
			int index = name.indexOf("/");
			name = name.substring(0, index) + name.substring(index + 1, index + 2).toUpperCase() + name.substring(index + 2);
		}
		
		return name;
	}
	
	protected boolean hasPostParameter() {
		return "POST".equalsIgnoreCase(service.getMethod())
			&& StringUtils.isNotBlank(service.getPostParameterName())
			&& StringUtils.isNotBlank(service.getPostParameterType());
	}
	
	protected boolean hasQueryParameters() {
		return service.getQueryParameters() != null && !service.getQueryParameters().isEmpty();
	}
}
