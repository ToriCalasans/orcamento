package br.com.batcaverna.orcamento.repository;

import br.com.batcaverna.orcamento.model.LacamentosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LacamentosRepository extends JpaRepository <LacamentosModel, Integer> {
}
