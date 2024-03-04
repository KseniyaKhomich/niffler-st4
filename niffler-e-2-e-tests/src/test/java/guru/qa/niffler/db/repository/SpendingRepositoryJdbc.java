package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.DataSourceProvider;
import guru.qa.niffler.db.Database;
import guru.qa.niffler.db.model.SpendingEntity;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

public class SpendingRepositoryJdbc implements SpendingRepository {

	private final DataSource spDs = DataSourceProvider.INSTANCE.dataSource(Database.SPEND);


	@Override
	public SpendingEntity createSpending(SpendingEntity spendingEntity) {
		try (Connection conn = spDs.getConnection()) {
			conn.setAutoCommit(false);

			try (PreparedStatement spendPs = conn.prepareStatement("INSERT INTO \"spend\" " +
					"(username, spend_date, currency, amount, description, category_id) " +
					"VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
				 PreparedStatement categoryPs = conn.prepareStatement("INSERT INTO \"category\" " +
						 "(category, username) " +
						 "VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

				categoryPs.setString(1, spendingEntity.getCategory().getCategory());
				categoryPs.setString(2, spendingEntity.getUsername());

				categoryPs.executeUpdate();

				UUID categoryId;
				try (ResultSet keys = categoryPs.getGeneratedKeys()) {
					if (keys.next()) {
						categoryId = UUID.fromString(keys.getString("id"));
					} else {
						throw new IllegalStateException("Can`t find id");
					}
				}

				spendPs.setString(1, spendingEntity.getUsername());
				spendPs.setDate(2, new Date(spendingEntity.getSpendDate().getTime()));
				spendPs.setString(3, spendingEntity.getCurrency().name());
				spendPs.setDouble(4, spendingEntity.getAmount());
				spendPs.setString(5, spendingEntity.getDescription());
				spendPs.setObject(6, categoryId);

				spendPs.executeUpdate();

				UUID spendId;
				try (ResultSet keys = spendPs.getGeneratedKeys()) {
					if (keys.next()) {
						spendId = UUID.fromString(keys.getString("id"));
					} else {
						throw new IllegalStateException("Can`t find id");
					}
				}
				conn.commit();
				spendingEntity.setId(spendId);
			} catch (Exception e) {
				conn.rollback();
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return spendingEntity;
	}
}
