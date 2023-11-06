package br.com.batcaverna.orcamento.repository;

import br.com.batcaverna.orcamento.model.TipoTransacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoTransacaoRepository extends JpaRepository <TipoTransacaoModel, Integer> {

}
