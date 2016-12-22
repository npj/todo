package io.npj.todo;

import io.npj.todo.lists.ListsController;
import io.npj.todo.mvc.Controller;

import java.util.Optional;

/**
 * Created by npj on 12/13/16.
 */
public class Todo {
	public static void main(String[] args) {
		Loop loop = Loop.getInstance();
		Controller.get(ListsController.class).ifPresent(cont -> {
			loop.onNext(cont::index, Optional.empty());
		});
		loop.run();
	}

	public static void logException(Exception e) {
		System.err.println(e.getMessage());
	}
}
