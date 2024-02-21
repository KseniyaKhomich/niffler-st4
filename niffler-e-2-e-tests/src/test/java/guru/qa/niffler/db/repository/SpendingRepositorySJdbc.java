package guru.qa.niffler.db.repository;

import guru.qa.niffler.db.DataSourceProvider;
import guru.qa.niffler.db.Database;
import guru.qa.niffler.db.model.CategoryEntity;
import guru.qa.niffler.db.model.SpendingEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.UUID;

public class SpendingRepositorySJdbc implements SpendingRepository {

	private final TransactionTemplate spendTxt;

	private final JdbcTemplate spendTemplate;


	public SpendingRepositorySJdbc() {
		JdbcTransactionManager spendTm = new JdbcTransactionManager(
				DataSourceProvider.INSTANCE.dataSource(Database.SPEND)
		);

		this.spendTxt = new TransactionTemplate(spendTm);
		this.spendTemplate = new JdbcTemplate(spendTm.getDataSource());
	}

	@Override
	public SpendingEntity createSpending(SpendingEntity spendingEntity) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		return spendTxt.execute(status -> {
			spendTemplate.update(con -> {
				PreparedStatement ps = con.prepareStatement(
						"INSERT INTO \"category\" " +
								"(category, username) " +
								"VALUES (?, ?)",
						PreparedStatement.RETURN_GENERATED_KEYS
				);

				ps.setString(1, spendingEntity.getCategory().getCategory());
				ps.setString(2, spendingEntity.getUsername());
				return ps;
			}, keyHolder);

			spendingEntity.getCategory().setId((UUID) keyHolder.getKeys().get("id"));

			 spendTemplate.update(con -> {
				 PreparedStatement ps = con.prepareStatement(
						 "INSERT INTO \"spend\" " +
								 "(username, spend_date, currency, amount, description, category_id) " +
								 "VALUES (?, ?, ?, ?, ?, ?)",
						 PreparedStatement.RETURN_GENERATED_KEYS
				 );

				 ps.setString(1, spendingEntity.getUsername());
				 ps.setDate(2, new Date(spendingEntity.getSpendDate().getTime()));
				 ps.setString(3, spendingEntity.getCurrency().name());
				 ps.setDouble(4, spendingEntity.getAmount());
				 ps.setString(5, spendingEntity.getDescription());
				 ps.setObject(6, spendingEntity.getCategory().getId());
				 return ps;
			 }, keyHolder);

			spendingEntity.setId((UUID) keyHolder.getKeys().get("id")); //checkId

			return spendingEntity;
		});
	}
}
