package net.firsthour.gen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QueryParameter")
public class QueryParameter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	private String type;
	
	public QueryParameter() {}
	
	public QueryParameter(final String name, final String type) {
		this.name = name;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "QueryParameter [id=" + id + ", name=" + name + ", type=" + type + "]";
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
