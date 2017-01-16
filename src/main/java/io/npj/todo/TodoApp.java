package io.npj.todo;

import dagger.Component;
import io.npj.todo.items.ItemsController;
import io.npj.todo.lists.ListsController;
import io.npj.todo.mvc.Controller;

import javax.inject.Singleton;
import java.util.Optional;

/**
 * Created by npj on 12/13/16.
 */
public class TodoApp implements Runnable {
	@Singleton
	@Component(modules = { DBModule.class })
	public interface TodoControllers {
		ListsController listsController();
		ItemsController itemsController();
	}

	public static void logException(Exception e) {
		System.err.println(e.getMessage());
	}

	public void run() {
		TodoControllers todoControllers = DaggerTodoApp_TodoControllers.create();
		Loop loop = Loop.getInstance();

		/* in the future we could use reflection to discover all the controller methods and register them */
		Controller.register(todoControllers.listsController());
		Controller.register(todoControllers.itemsController());

		Controller.get(ListsController.class).ifPresent(cont -> {
			loop.onNext(cont::index, Optional.empty());
		});
		loop.run();
	}

	public static void main(String[] args) {
		new TodoApp().run();
	}
}
