package br.com.batcaverna.orcamento.repository;

import br.com.batcaverna.orcamento.model.ElementoDespesaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElementoDespesaRepository extends JpaRepository <ElementoDespesaModel, Integer> {

    Optional<ElementoDespesaModel> findByCodigo(Long codigo);
}
