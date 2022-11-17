package net.firsthour.gen.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Service")
public class Service {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank(message = "method is required")
	private String method;
	
	@NotBlank(message = "path is required")
	private String path;
	
	@NotBlank(message = "returnClass is required")
	private String returnClass;
	
	private boolean includeConsumes;
	private boolean includeProduces;
	private String postParameterName;
	private String postParameterType;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<QueryParameter> queryParameters;
	
	@Override
	public String toString() {
		return "Service [method=" + method + ", path=" + path + ", returnClass=" + returnClass + ", includeConsumes="
				+ includeConsumes + ", includeProduces=" + includeProduces + ", queryParameters=" + queryParameters
				+ ", postParameterName=" + postParameterName + ", postParameterType=" + postParameterType + "]";
	}
	
	public long getId() {
		return id;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getReturnClass() {
		return returnClass;
	}
	
	public void setReturnClass(String returnClass) {
		this.returnClass = returnClass;
	}
	
	public boolean isIncludeConsumes() {
		return includeConsumes;
	}
	
	public void setIncludeConsumes(boolean includeConsumes) {
		this.includeConsumes = includeConsumes;
	}
	
	public boolean isIncludeProduces() {
		return includeProduces;
	}
	
	public void setIncludeProduces(boolean includeProduces) {
		this.includeProduces = includeProduces;
	}
	
	public List<QueryParameter> getQueryParameters() {
		return queryParameters;
	}
	
	public void setQueryParameters(List<QueryParameter> queryParameters) {
		this.queryParameters = queryParameters;
	}
	
	public String getPostParameterName() {
		return postParameterName;
	}
	
	public void setPostParameterName(String postParameterName) {
		this.postParameterName = postParameterName;
	}
	
	public String getPostParameterType() {
		return postParameterType;
	}
	
	public void setPostParameterType(String postParameterType) {
		this.postParameterType = postParameterType;
	}
}
