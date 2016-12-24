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
	Provider<ListIndexView> mockListIndexViewProvider;
	ListsController listsController;

	public void setUp() throws Exception {
		super.setUp();
		mockListService = mock(ListService.class);
		mockListIndexView = mock(ListIndexView.class);
		mockListIndexViewProvider = () -> mockListIndexView;
		listsController = new ListsController(mockListService, mockListIndexViewProvider);
	}

	/**
	 * Testing this method is a bit more complicated than testing the services.
	 * Specifically, the index method has what is known as a "hard dependency,"
	 * because it cannot be changed from the outside. This is ListIndexView.
	 *
	 * Ideally this test should not need access to the database, and should simply
	 * verify that we fetch all lists and render the view with the results. In the
	 * v1.0 code, this is impossible since we cannot replace ListIndexView with a
	 * stub/spy to verify method calls and parameters.
	 *
	 * There are two options:
	 * 1) Pass the ListIndexView instance as a parameter to the controller constructor, or
	 * 2) Use another object to build the instance for us (a factory) and pass that into the constructor
	 *
	 * Let's assume that we know nothing about how ListIndexView works, and that there's a good reason
	 * why the original implementors chose to instantiate a new ListIndexView every time the index
	 * method is called. Maybe there is some internal state that the ListIndexView holds onto that would
	 * make it unpredictable to call render multiple times. This means that #2 is the better option,
	 * as we still create a new instance of ListIndexView on every invocation of index.
	 *
	 * Instead of creating a ViewFactory, we can use dependency injection and define a "provider"
	 * for ListIndexView and inject it into ListsController. Then we can instantiate the controller
	 * under test by passing a ListIndexView provider that returns mock ListIndexView instances.
	 *
	 * Steps:
	 * 1) Add a field of type Provide<ListIndexView> to ListsController.
	 * 2) Add an @Inject annotation to the ListIndexView constructor.
	 * 3) Profit.
	 */
	public void testIndex() throws Exception {
		List<ListModel> mockLists = new ArrayList<>();
		when(mockListService.fetchAll()).thenReturn(mockLists);
		listsController.index(Optional.empty());
		verify(mockListIndexView).render(mockLists);
	}

	public void testCreate() throws Exception {

	}

	public void testDelete() throws Exception {

	}

}