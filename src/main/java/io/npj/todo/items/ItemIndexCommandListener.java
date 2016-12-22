package io.npj.todo.items;

import io.npj.todo.console.ConsoleView;
import io.npj.todo.mvc.Controller;
import io.npj.todo.Loop;
import io.npj.todo.lists.ListModel;
import io.npj.todo.lists.ListsController;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by pbrindisi on 12/21/16.
 */
public class ItemIndexCommandListener implements ConsoleView.CommandListener {
	private ListModel list = null;

	public ItemIndexCommandListener(ListModel list) {
		this.list = list;
	}

	public void dispatchCommand(Pair<String, String> command) throws ConsoleView.CommandException {
		Map<String, String> params = new HashMap<>();
		Loop loop = Loop.getInstance();

		switch (command.getLeft()) {
			case "create":
				params.put("list_id", String.valueOf(list.getId()));
				params.put("name", command.getRight());
				Controller.get(ItemsController.class).ifPresent(cont -> loop.onNext(cont::create, Optional.of(params)));
				break;

			case "delete":
				params.put("list_id", String.valueOf(list.getId()));
				params.put("id", command.getRight());
				Controller.get(ItemsController.class).ifPresent(cont -> loop.onNext(cont::delete, Optional.of(params)));
				break;

			case "back":
				Controller.get(ListsController.class).ifPresent(cont -> loop.onNext(cont::index, Optional.empty()));

			case "quit":
				break;

			default:
				Controller.get(ItemsController.class).ifPresent(cont -> loop.onNext(cont::index, Optional.empty()));
				throw new ConsoleView.CommandException(
						new StringBuilder("Unknown command: ")
								.append(command.getLeft())
								.toString()
				);
		}
	}
}
