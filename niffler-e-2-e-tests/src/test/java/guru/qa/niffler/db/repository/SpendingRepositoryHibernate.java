package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.Database;
import guru.qa.niffler.db.EmfProvider;
import guru.qa.niffler.db.jpa.JpaService;
import guru.qa.niffler.db.model.CategoryEntity;
import guru.qa.niffler.db.model.SpendingEntity;

import static guru.qa.niffler.db.Database.SPEND;

public class SpendingRepositoryHibernate extends JpaService implements SpendingRepository {

	public SpendingRepositoryHibernate() {
		super(SPEND, EmfProvider.INSTANCE.emf(SPEND).createEntityManager());
	}

	@Override
	public SpendingEntity createSpending(SpendingEntity spendingEntity) {
		persist(SPEND, spendingEntity);
		return spendingEntity;
	}
}
