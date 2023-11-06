package br.com.batcaverna.orcamento.repository;

import br.com.batcaverna.orcamento.model.ObjetivoEstrategicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjetivoEstrategicoRepository extends JpaRepository <ObjetivoEstrategicoModel, Integer> {

}
