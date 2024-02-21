package guru.qa.niffler.jupiter;

import guru.qa.niffler.db.model.CategoryEntity;
import guru.qa.niffler.db.model.SpendingEntity;
import guru.qa.niffler.db.repository.SpendingRepository;
import guru.qa.niffler.model.SpendJson;

import java.io.IOException;

public class DatabaseSpendExtension extends SpendExtension{

	private final SpendingRepository repository = SpendingRepository.getInstance();

	@Override
	SpendJson create(SpendJson spend) throws IOException {
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setUsername(spend.username());
		categoryEntity.setCategory(spend.category());

		SpendingEntity spendingEntity = new SpendingEntity();
		spendingEntity.setSpendDate(spend.spendDate());
		spendingEntity.setCategory(categoryEntity);
		spendingEntity.setAmount(spend.amount());
		spendingEntity.setCurrency(spend.currency());
		spendingEntity.setUsername(spend.username());
		spendingEntity.setDescription(spend.description());

		return repository
				.createSpending(spendingEntity)
				.toJson();
	}
}
