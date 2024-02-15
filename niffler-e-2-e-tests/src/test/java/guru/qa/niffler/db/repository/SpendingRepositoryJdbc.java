package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.DataSourceProvider;
import guru.qa.niffler.db.Database;
import guru.qa.niffler.db.model.CategoryEntity;
import guru.qa.niffler.db.model.SpendingEntity;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

public class SpendingRepositoryJdbc implements SpendingRepository {

	private final DataSource spDs = DataSourceProvider.INSTANCE.dataSource(Database.SPEND);


	@Override
	public SpendingEntity createSpending(SpendingEntity spendingEntity) {
		try (Connection conn = spDs.getConnection()) {
			try (PreparedStatement ps = conn.prepareStatement("INSERT INTO \"spend\" " +
					"(username, spend_date, currency, amount, description, category_id) " +
					"VALUES (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

				ps.setString(1, spendingEntity.getUsername());
				ps.setDate(2, (Date) spendingEntity.getSpendDate());
				ps.setString(3, spendingEntity.getCurrency().name());
				ps.setDouble(4, spendingEntity.getAmount());
				ps.setString(5, spendingEntity.getDescription());
				ps.setObject(6, spendingEntity.getCategory().getId());

				ps.executeUpdate();

				UUID spendId;
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						spendId = UUID.fromString(keys.getString("id"));
					} else {
						throw new IllegalStateException("Can`t find id");
					}
				}

				spendingEntity.setId(spendId);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return spendingEntity;
	}

	@Override
	public CategoryEntity createCategory(CategoryEntity categoryEntity) {
		try (Connection conn = spDs.getConnection()) {
			try (PreparedStatement ps = conn.prepareStatement("INSERT INTO \"category\" " +
					"(category, username) " +
					"VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

				ps.setString(1, categoryEntity.getCategory());
				ps.setString(2, categoryEntity.getUsername());

				ps.executeUpdate();

				UUID categoryId;
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						categoryId = UUID.fromString(keys.getString("id"));
					} else {
						throw new IllegalStateException("Can`t find id");
					}
				}

				categoryEntity.setId(categoryId);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return categoryEntity;
	}
}
