package br.com.batcaverna.orcamento.service;

import br.com.batcaverna.orcamento.model.SolicitanteModel;
import br.com.batcaverna.orcamento.repository.SolicitanteRepository;
import br.com.batcaverna.orcamento.rest.dto.SolicitanteDto;
import br.com.batcaverna.orcamento.rest.form.SolicitanteForm;
import br.com.batcaverna.orcamento.rest.form.SolicitanteUpdateForm;
import br.com.batcaverna.orcamento.service.exceptions.DataIntegrityException;
import br.com.batcaverna.orcamento.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SolicitanteService {
    @Autowired
    SolicitanteRepository solicitanteRepository;

    public SolicitanteDto findById(int idSolicitante) {
        try {
            SolicitanteModel solicitanteModel = solicitanteRepository.findById(idSolicitante).get();
            return convertSolicitanteModelToSolicitanteDto(solicitanteModel);
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + idSolicitante + ", Tipo: " + SolicitanteModel.class.getName());
        }
    }

    public List<SolicitanteDto> findAll(){
        List<SolicitanteModel> solicitanteList = solicitanteRepository.findAll();
        return convertListToDto(solicitanteList);
    }

    public SolicitanteDto insert(SolicitanteForm solicitanteForm) {
        try {
            SolicitanteModel solicitanteNovo = convertSolicitanteFormToSolicitanteModel(solicitanteForm);
            solicitanteNovo = solicitanteRepository.save(solicitanteNovo);
            return convertSolicitanteModelToSolicitanteDto(solicitanteNovo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Solicitante não foi(foram) preenchido(s).");
        }
    }

    public SolicitanteDto update(SolicitanteUpdateForm solicitanteUpdateForm, int idSolicitante) {
        try {
            Optional<SolicitanteModel> solicitanteExistente = solicitanteRepository.findById(idSolicitante);
            if (solicitanteExistente.isPresent()) {
                SolicitanteModel solicitanteAtualizado = solicitanteExistente.get();
                solicitanteAtualizado.setNome(solicitanteUpdateForm.getNome());
                solicitanteAtualizado.setDataAlteracao(solicitanteUpdateForm.getDataAlteracao());


                solicitanteRepository.save(solicitanteAtualizado);
                return convertSolicitanteModelToSolicitanteDtoUpdate(solicitanteAtualizado);
            }else{
                throw new DataIntegrityException("A ID do Solicitante não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Solicitante não foi(foram) preenchido(s).");
        }
    }

    public void delete(int idSolicitante) {
        try {
            if (solicitanteRepository.existsById (idSolicitante)) {
                solicitanteRepository.deleteById (idSolicitante);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Solicitante!");
        }
    }

    private SolicitanteModel convertSolicitanteFormToSolicitanteModel(SolicitanteForm solicitanteForm) {
        SolicitanteModel solicitanteModel = new SolicitanteModel();
        solicitanteModel.setNome(solicitanteForm.getNome());
        solicitanteModel.setDataCadastro(solicitanteForm.getDataCadastro());
        solicitanteModel.setDataAlteracao(solicitanteForm.getDataAlteracao());
        return solicitanteModel;
    }

    private SolicitanteDto convertSolicitanteModelToSolicitanteDto(SolicitanteModel solicitanteModel) {
        SolicitanteDto solicitanteDto = new SolicitanteDto();
        solicitanteDto.setNome(solicitanteModel.getNome());
        solicitanteDto.setDataAlteracao(solicitanteModel.getDataAlteracao());
        solicitanteDto.setDataCadastro(solicitanteModel.getDataCadastro());
        return solicitanteDto;
    }

    private SolicitanteDto convertSolicitanteModelToSolicitanteDtoUpdate(SolicitanteModel solicitanteModel) {
        SolicitanteDto solicitanteDto = new SolicitanteDto();
        solicitanteDto.setNome(solicitanteModel.getNome());
        solicitanteDto.setDataAlteracao(solicitanteModel.getDataAlteracao());
        return solicitanteDto;
    }

    private List<SolicitanteDto> convertListToDto(List<SolicitanteModel> list){
        List<SolicitanteDto> solicitanteDtoList = new ArrayList<>();
        for (SolicitanteModel solicitanteModel : list) {
            SolicitanteDto solicitanteDto = this.convertSolicitanteModelToSolicitanteDto(solicitanteModel);
            solicitanteDtoList.add(solicitanteDto);
        }
        return solicitanteDtoList;
    }
}
