package br.com.fiap.app.config;

import br.com.fiap.app.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestDatabaseConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void setUp() {
        insertTestUsers();
    }

    private void insertTestUsers() {
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword("{noop}admin");
        adminUser.setEmail("admin@example.com");
        adminUser.setRole("ADMIN");

        User regularUser = new User();
        regularUser.setUsername("user");
        regularUser.setPassword("{noop}user");
        regularUser.setEmail("user@example.com");
        regularUser.setRole("USER");

        entityManager.persist(adminUser);
        entityManager.persist(regularUser);
    }

}
