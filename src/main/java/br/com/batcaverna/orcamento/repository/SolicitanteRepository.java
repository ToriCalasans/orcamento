package br.com.batcaverna.orcamento.repository;

import br.com.batcaverna.orcamento.model.SolicitanteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitanteRepository extends JpaRepository <SolicitanteModel, Integer> {

}