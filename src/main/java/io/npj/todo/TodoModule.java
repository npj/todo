package io.npj.todo;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by pbrindisi on 12/23/16.
 */
@Module
public class TodoModule {
	@Provides @Singleton static Optional<DB> provideDB() {
		try {
			return Optional.of(new DB());
		} catch(SQLException e) {
			return Optional.empty();
		}
	}
}
