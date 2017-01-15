package io.npj.todo.lists;

import junit.framework.TestCase;

import javax.inject.Provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by pbrindisi on 12/23/16.
 */
public class ListsControllerTest extends TestCase {
	ListService mockListService;
	ListIndexView mockListIndexView;
	ListViewFactory mockListViewFactory;
	ListsController listsController;

	public void setUp() throws Exception {
		super.setUp();
		mockListService = mock(ListService.class);
		mockListIndexView = mock(ListIndexView.class);
		mockListViewFactory = mock(ListViewFactory.class);
		listsController = new ListsController(mockListService, mockListViewFactory);
	}

	public void testIndex() throws Exception {
		List<ListModel> mockLists = new ArrayList<>();
		when(mockListService.fetchAll()).thenReturn(mockLists);
		when(mockListViewFactory.createIndexView()).thenReturn(mockListIndexView);
		listsController.index(Optional.empty());
		verify(mockListIndexView).render(mockLists);
	}
}