package br.com.batcaverna.orcamento.repository;

import br.com.batcaverna.orcamento.model.TipoLancamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoLancamentoRepository extends JpaRepository <TipoLancamentoModel, Integer> {

}