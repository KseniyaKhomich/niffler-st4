package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.model.CategoryEntity;
import guru.qa.niffler.db.model.SpendingEntity;

import java.sql.SQLException;

public interface SpendingRepository {

	static SpendingRepository getInstance() {
		String repositoryParameter = System.getProperty("repository");

		if (repositoryParameter != null) {
			return switch (repositoryParameter) {
				case "jdbc" -> new SpendingRepositoryJdbc();
				case "sjdbc" -> new SpendingRepositorySJdbc();
				case "hibernate" -> new SpendingRepositoryHibernate();
				default -> throw new IllegalArgumentException("Incorrect value for Repository Parameter.");
			};

		} else {
			throw new IllegalArgumentException("Repository parameter isn't found.");

		}
	};

	SpendingEntity createSpending(SpendingEntity spendingEntity);
}
