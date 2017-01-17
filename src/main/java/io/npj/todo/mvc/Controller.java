package io.npj.todo.mvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Controller {
	private static Map<Class<? extends Controller>, Controller> controllers = new HashMap<>();

	public static <C extends Controller> Optional<C> get(Class<C> controllerClass) {
		return Optional.ofNullable((C)controllers.get(controllerClass));
	}

	public static void register(Controller controller) {
		controllers.put(controller.getClass(), controller);
	}

	private final String name;

	public Controller(String name) {
		this.name = name;
	}

}
