package com.mb.spring.repository;

import com.mb.spring.models.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcExampleDao implements ExampleDao {

    private JdbcTemplate jdbcTemplate;


    @Inject
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void count() {
        int nbAccounts = jdbcTemplate.queryForInt("select count(*) from account");
        int nbGoodCustomers = jdbcTemplate.queryForInt("select count(*) from account where balance > ?", 10000);

    }

    @Override
    public void deleteAllAccount() {
        jdbcTemplate.execute("delete from account");
    }

    @Override
    public void createAccount(Account account) {
        int created = jdbcTemplate.update(
                "insert into account (user_id, creation_date, balance) " + "values (?, ?, ?)",
                account.getUserId(), account.getCreationDate(), account.getBalance());
    }

    @Override
    public void updateAccount(Account account) {
        List<Account> allsAccount = this.findAllsAccount();
        Account acc;
        if (allsAccount == null || allsAccount.size() == 0) throw new RuntimeException();

        int random_int = (int) (Math.random() * (allsAccount.size() - 0 + 1) + 0);

        int min = 100;
        int max = 108900030;
        int balance = (int) (Math.random() * (max - min + 1) + min);
        acc = allsAccount.get(random_int - 1);
        acc.setBalance(balance);

        jdbcTemplate.update(
                "update account set balance=? where user_id=?",
                acc.getBalance(), acc.getUserId());

    }

    @Override
    public List<Account> findAllsAccount() {

        List<Account> accountList = jdbcTemplate.query(
                "select  user_id,creation_date,balance from account", new ParameterizedRowMapper<Account>() {
                    public Account mapRow(ResultSet rs, int i) throws SQLException {
                        return new Account(rs.getString("user_id"), rs.getDate("creation_date"), rs.getInt("balance"));
                    }
                }
        );
        return accountList;
    }

}
