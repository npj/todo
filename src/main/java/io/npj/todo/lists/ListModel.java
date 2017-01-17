package io.npj.todo.lists;

import io.npj.todo.mvc.Model;

public class ListModel extends Model {
	private String name = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
