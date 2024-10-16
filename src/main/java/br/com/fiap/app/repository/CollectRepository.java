package br.com.fiap.app.repository;

import br.com.fiap.app.model.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectRepository extends JpaRepository<Collect, Long> {

    List<Collect> findByUserId(Long userId);

}
