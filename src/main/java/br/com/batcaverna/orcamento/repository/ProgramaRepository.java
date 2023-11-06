package br.com.batcaverna.orcamento.repository;

import br.com.batcaverna.orcamento.model.ProgramaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramaRepository extends JpaRepository <ProgramaModel, Integer> {

    Optional<ProgramaModel> findByCodigo(Long codigo);
}
