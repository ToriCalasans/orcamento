package br.com.batcaverna.orcamento.repository;

import br.com.batcaverna.orcamento.model.UnidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeRepository extends JpaRepository <UnidadeModel, Integer> {

}
