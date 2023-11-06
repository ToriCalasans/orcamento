package br.com.batcaverna.orcamento.service;

import br.com.batcaverna.orcamento.model.ObjetivoEstrategicoModel;
import br.com.batcaverna.orcamento.repository.ObjetivoEstrategicoRepository;
import br.com.batcaverna.orcamento.rest.dto.ObjetivoEstrategicoDto;
import br.com.batcaverna.orcamento.rest.form.ObjetivoEstrategicoForm;
import br.com.batcaverna.orcamento.rest.form.ObjetivoEstrategicoUpdateForm;
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
public class ObjetivoEstrategicoService {
    @Autowired
    ObjetivoEstrategicoRepository objetivoEstrategicoRepository;

    public ObjetivoEstrategicoDto findById(int idObjetivoEstrategico) {
        try {
            ObjetivoEstrategicoModel objetivoEstrategicoModel = objetivoEstrategicoRepository.findById(idObjetivoEstrategico).get();
            return convertObjetivoEstrategicoModelToObjetivoEstrategicoDto(objetivoEstrategicoModel);
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + idObjetivoEstrategico + ", Tipo: " + ObjetivoEstrategicoModel.class.getName());
        }
    }

    public List<ObjetivoEstrategicoDto> findAll(){
        List<ObjetivoEstrategicoModel> objetivoEstrategicoList = objetivoEstrategicoRepository.findAll();
        return convertListToDto(objetivoEstrategicoList);
    }

    public ObjetivoEstrategicoDto insert(ObjetivoEstrategicoForm objetivoEstrategicoForm) {
        try {
            ObjetivoEstrategicoModel objetivoEstrategicoNovo = convertObjetivoEstrategicoFormToObjetivoEstrategicoModel(objetivoEstrategicoForm);
            objetivoEstrategicoNovo = objetivoEstrategicoRepository.save(objetivoEstrategicoNovo);
            return convertObjetivoEstrategicoModelToObjetivoEstrategicoDto(objetivoEstrategicoNovo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do ObjetivoEstrategico não foi(foram) preenchido(s).");
        }
    }

    public ObjetivoEstrategicoDto update(ObjetivoEstrategicoUpdateForm objetivoEstrategicoUpdateForm, int idObjetivoEstrategico) {
        try {
            Optional<ObjetivoEstrategicoModel> objetivoEstrategicoExistente = objetivoEstrategicoRepository.findById(idObjetivoEstrategico);
            if (objetivoEstrategicoExistente.isPresent()) {
                ObjetivoEstrategicoModel objetivoEstrategicoAtualizado = objetivoEstrategicoExistente.get();
                objetivoEstrategicoAtualizado.setNome(objetivoEstrategicoUpdateForm.getNome());
                objetivoEstrategicoAtualizado.setDataAlteracao(objetivoEstrategicoUpdateForm.getDataAlteracao());


                objetivoEstrategicoRepository.save(objetivoEstrategicoAtualizado);
                return convertObjetivoEstrategicoModelToObjetivoEstrategicoDtoUpdate(objetivoEstrategicoAtualizado);
            }else{
                throw new DataIntegrityException("A ID do ObjetivoEstrategico não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do ObjetivoEstrategico não foi(foram) preenchido(s).");
        }
    }

    public void delete(int idObjetivoEstrategico) {
        try {
            if (objetivoEstrategicoRepository.existsById (idObjetivoEstrategico)) {
                objetivoEstrategicoRepository.deleteById (idObjetivoEstrategico);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um ObjetivoEstrategico!");
        }
    }

    private ObjetivoEstrategicoModel convertObjetivoEstrategicoFormToObjetivoEstrategicoModel(ObjetivoEstrategicoForm objetivoEstrategicoForm) {
        ObjetivoEstrategicoModel objetivoEstrategicoModel = new ObjetivoEstrategicoModel();
        objetivoEstrategicoModel.setNome(objetivoEstrategicoForm.getNome());
        objetivoEstrategicoModel.setDataCadastro(objetivoEstrategicoForm.getDataCadastro());
        objetivoEstrategicoModel.setDataAlteracao(objetivoEstrategicoForm.getDataAlteracao());
        return objetivoEstrategicoModel;
    }

    private ObjetivoEstrategicoDto convertObjetivoEstrategicoModelToObjetivoEstrategicoDto(ObjetivoEstrategicoModel objetivoEstrategicoModel) {
        ObjetivoEstrategicoDto objetivoEstrategicoDto = new ObjetivoEstrategicoDto();
        objetivoEstrategicoDto.setNome(objetivoEstrategicoModel.getNome());
        objetivoEstrategicoDto.setDataAlteracao(objetivoEstrategicoModel.getDataAlteracao());
        objetivoEstrategicoDto.setDataCadastro(objetivoEstrategicoModel.getDataCadastro());
        return objetivoEstrategicoDto;
    }

    private ObjetivoEstrategicoDto convertObjetivoEstrategicoModelToObjetivoEstrategicoDtoUpdate(ObjetivoEstrategicoModel objetivoEstrategicoModel) {
        ObjetivoEstrategicoDto objetivoEstrategicoDto = new ObjetivoEstrategicoDto();
        objetivoEstrategicoDto.setNome(objetivoEstrategicoModel.getNome());
        objetivoEstrategicoDto.setDataAlteracao(objetivoEstrategicoModel.getDataAlteracao());
        return objetivoEstrategicoDto;
    }

    private List<ObjetivoEstrategicoDto> convertListToDto(List<ObjetivoEstrategicoModel> list){
        List<ObjetivoEstrategicoDto> objetivoEstrategicoDtoList = new ArrayList<>();
        for (ObjetivoEstrategicoModel objetivoEstrategicoModel : list) {
            ObjetivoEstrategicoDto objetivoEstrategicoDto = this.convertObjetivoEstrategicoModelToObjetivoEstrategicoDto(objetivoEstrategicoModel);
            objetivoEstrategicoDtoList.add(objetivoEstrategicoDto);
        }
        return objetivoEstrategicoDtoList;
    }
}
