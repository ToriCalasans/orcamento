package br.com.batcaverna.orcamento.repository;

import br.com.batcaverna.orcamento.model.GrupoDespesaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrupoDespesaRepository extends JpaRepository <GrupoDespesaModel, Integer> {
    Optional<GrupoDespesaModel> findByCodigo(Long codigo);
}
