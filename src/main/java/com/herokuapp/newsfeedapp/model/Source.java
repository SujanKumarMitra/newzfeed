package com.herokuapp.newsfeedapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class hold information of the source of article class
 * 
 * @since 2020-04-30
 * @author Sujan Kumar Mitra
 * @see Article
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Source {

	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		if (id == null) {
			this.id = "Not Found";
		} else {
			this.id = id;
		}
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		if (name == null) {
			this.name = "Not Found";
		} else {
			this.name = name;
		}
	}

	@Override
	public String toString() {
		return "Source [id=" + id + ", name=" + name + "]";
	}

}