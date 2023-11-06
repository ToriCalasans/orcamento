package br.com.batcaverna.orcamento.repository;

import br.com.batcaverna.orcamento.model.ModalidadeAplicacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModalidadeAplicacaoRepository extends JpaRepository <ModalidadeAplicacaoModel, Integer> {

    Optional<ModalidadeAplicacaoModel> findByCodigo(Long codigo);
}
