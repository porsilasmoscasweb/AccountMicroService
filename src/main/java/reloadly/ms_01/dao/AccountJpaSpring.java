package reloadly.ms_01.dao;

import reloadly.ms_01.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AccountJpaSpring extends JpaRepository<Account, Long> {
    Account findByName(String name);

    @Transactional
    @Modifying
    @Query("Delete from Account a Where a.name=?1")
    void deleteByName(String name);
}
