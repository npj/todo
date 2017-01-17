package io.npj.todo;

import io.npj.todo.lists.ListsController;
import io.npj.todo.mvc.Controller;
import java.util.Optional;

public class TodoApp implements Runnable {
	public static void logException(Exception e) {
		System.err.println(e.getMessage());
	}

	public void run() {
		TodoComponent todoComponent = DaggerTodoComponent.create();
		Loop loop = Loop.getInstance();

		/* in the future we could use reflection to discover all the controller methods and register them */
		Controller.register(todoComponent.listsController());
		Controller.register(todoComponent.itemsController());

		Controller.get(ListsController.class).ifPresent(cont -> {
			loop.onNext(cont::index, Optional.empty());
		});
		loop.run();
	}

	public static void main(String[] args) {
		new TodoApp().run();
	}
}
