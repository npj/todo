package io.npj.todo.lists;

import io.npj.todo.console.ConsoleView;
import io.npj.todo.mvc.Controller;
import io.npj.todo.items.ItemsController;
import io.npj.todo.Loop;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ListIndexCommandListener implements ConsoleView.CommandListener {
	public void dispatchCommand(Pair<String, String> command) throws ConsoleView.CommandException {
		Map<String, String> params = new HashMap<>();
		Loop loop = Loop.getInstance();

		switch (command.getLeft()) {
			case "view":
				params.put("list_id", command.getRight());
				Controller.get(ItemsController.class).ifPresent(cont -> loop.onNext(cont::index, Optional.of(params)));
				break;

			case "create":
				params.put("name", command.getRight());
				Controller.get(ListsController.class).ifPresent(cont -> loop.onNext(cont::create, Optional.of(params)));
				break;

			case "delete":
				params.put("id", command.getRight());
				Controller.get(ListsController.class).ifPresent(cont -> loop.onNext(cont::delete, Optional.of(params)));
				break;

			case "quit":
				break;

			default:
				Controller.get(ListsController.class).ifPresent(cont -> loop.onNext(cont::index, Optional.empty()));
				throw new ConsoleView.CommandException(
						new StringBuilder("Unknown command: ")
							.append(command.getLeft())
							.toString()
				);
		}
	}
}
