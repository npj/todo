package io.npj.todo.items;

import io.npj.todo.console.ConsoleView;

import java.io.IOException;
import java.util.List;

/**
 * Created by pbrindisi on 12/21/16.
 */
public class ItemIndexView extends ConsoleView {
	private static final String[] commands = new String[]{
			"create [item text]",
			"delete [item id]",
			"back",
			"quit"
	};

	private static final String HEADER = "TODO Lists > %s > Items";
	private static final String EMPTY = "[no items]";
	private static final String COMMANDS = "Commands:";
	private static final String PROMPT = "> ";
	private static final String COLSEP = "\t|\t";

	public void render(String listName, List<ItemModel> items) throws IOException {
		console.setHeader(String.format(HEADER, listName));

		StringBuilder bodyBuilder = new StringBuilder("\n");

		if (items.isEmpty()) {
			bodyBuilder.append(EMPTY);
		} else {
			for (ItemModel item : items) {
				bodyBuilder.append(item.getId());
				bodyBuilder.append(COLSEP);
				bodyBuilder.append(item.getName());
				bodyBuilder.append("\n");
			}
		}
		bodyBuilder.append("\n");

		StringBuilder footerBuilder = new StringBuilder();
		footerBuilder.append(COMMANDS);
		footerBuilder.append("\n");
		footerBuilder.append(String.join("\n", commands));
		footerBuilder.append("\n");

		console.setBody(bodyBuilder.toString());
		console.setFooter(footerBuilder.toString());
		console.write();

		sendCommand(console.readLine(PROMPT));
		console.pageBreak();
	}
}
