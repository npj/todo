package io.npj.todo.lists;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListsControllerTest extends TestCase {
	ListStore mockListStore;
	ListIndexView mockListIndexView;
	ListViewFactory mockListViewFactory;
	ListsController listsController;

	public void setUp() throws Exception {
		super.setUp();
		mockListStore = mock(ListStore.class);
		mockListIndexView = mock(ListIndexView.class);
		mockListViewFactory = mock(ListViewFactory.class);
		listsController = new ListsController(mockListStore, mockListViewFactory);
	}

	public void testIndex() throws Exception {
		List<ListModel> mockLists = new ArrayList<>();
		when(mockListStore.fetchAll()).thenReturn(mockLists);
		when(mockListViewFactory.createIndexView()).thenReturn(mockListIndexView);
		listsController.index(Optional.empty());
		verify(mockListIndexView).render(mockLists);
	}
}