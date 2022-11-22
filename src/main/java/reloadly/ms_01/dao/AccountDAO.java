package reloadly.ms_01.dao;

import reloadly.ms_01.model.Account;

import java.util.List;

public interface AccountDAO {

    List<Account> all();

    Account find(Long id);

    Account findByName(String name);

    void create(Account ac);

    void update(Account ac);

    void delete(Long id);

    void deleteByName(String name);

}
