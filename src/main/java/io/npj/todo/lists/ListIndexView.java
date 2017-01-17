package io.npj.todo.lists;

import io.npj.todo.console.ConsoleView;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class ListIndexView extends ConsoleView {
	private static final String[] commands = new String[]{
			"view   [list id]",
			"create [list name]",
			"delete [list id]",
			"quit"
	};

	private static final String HEADER = "TODO Lists";
	private static final String EMPTY = "[no lists found]";
	private static final String COMMANDS = "Commands:";
	private static final String PROMPT = "> ";
	private static final String COLSEP = "\t|\t";

	@Inject
	public ListIndexView() {
		addCommandListener(new ListIndexCommandListener());
	}

	public void render(List<ListModel> lists) throws IOException {
		console.setHeader(HEADER);

		StringBuilder bodyBuilder = new StringBuilder("\n");

		if (lists.isEmpty()) {
			bodyBuilder.append(EMPTY);
		} else {
			for (ListModel list : lists) {
				bodyBuilder.append(list.getId());
				bodyBuilder.append(COLSEP);
				bodyBuilder.append(list.getName());
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
