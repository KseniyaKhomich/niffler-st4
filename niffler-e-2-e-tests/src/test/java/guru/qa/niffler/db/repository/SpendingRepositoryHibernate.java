package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.Database;
import guru.qa.niffler.db.EmfProvider;
import guru.qa.niffler.db.jpa.JpaService;
import guru.qa.niffler.db.model.CategoryEntity;
import guru.qa.niffler.db.model.SpendingEntity;
import jakarta.persistence.EntityManager;

public class SpendingRepositoryHibernate extends JpaService implements SpendingRepository {

	public SpendingRepositoryHibernate() {
		super(Database.SPEND, EmfProvider.INSTANCE.emf(Database.SPEND).createEntityManager());
	}

	@Override
	public SpendingEntity createSpending(SpendingEntity spendingEntity) {
		return null;
	}

	@Override
	public CategoryEntity createCategory(CategoryEntity categoryEntity) {
		return null;
	}
}
