package io.npj.todo;

import dagger.Module;
import dagger.Provides;
import io.npj.todo.DB;

import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Optional;

@Module
public class DBModule {
	@Provides @Singleton static Optional<DB> provideDB() {
		try {
			return Optional.of(DB.getInstance());
		} catch(SQLException | DB.DataFileException e) {
			return Optional.empty();
		}
	}
}
