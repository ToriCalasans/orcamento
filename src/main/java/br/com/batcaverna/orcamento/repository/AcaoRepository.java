package br.com.batcaverna.orcamento.repository;

import br.com.batcaverna.orcamento.model.AcaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcaoRepository extends JpaRepository<AcaoModel, Integer> {

    Optional<AcaoModel> findByCodigo(Long codigo);
}
