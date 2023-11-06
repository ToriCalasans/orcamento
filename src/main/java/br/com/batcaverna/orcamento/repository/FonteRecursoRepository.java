package br.com.batcaverna.orcamento.repository;

import br.com.batcaverna.orcamento.model.FonteRecursoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FonteRecursoRepository extends JpaRepository <FonteRecursoModel, Integer> {
    Optional<FonteRecursoModel> findByCodigo(Long codigo);
}
