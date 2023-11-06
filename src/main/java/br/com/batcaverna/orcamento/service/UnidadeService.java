package br.com.batcaverna.orcamento.service;

import br.com.batcaverna.orcamento.model.UnidadeModel;
import br.com.batcaverna.orcamento.repository.UnidadeRepository;
import br.com.batcaverna.orcamento.rest.dto.UnidadeDto;
import br.com.batcaverna.orcamento.rest.form.UnidadeForm;
import br.com.batcaverna.orcamento.rest.form.UnidadeUpdateForm;
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
public class UnidadeService {
    @Autowired
    UnidadeRepository unidadeRepository;

    public UnidadeDto findById(int idUnidade) {
        try {
            UnidadeModel unidadeModel = unidadeRepository.findById(idUnidade).get();
            return convertUnidadeModelToUnidadeDto(unidadeModel);
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + idUnidade + ", Tipo: " + UnidadeModel.class.getName());
        }
    }

    public List<UnidadeDto> findAll(){
        List<UnidadeModel> unidadeList = unidadeRepository.findAll();
        return convertListToDto(unidadeList);
    }

    public UnidadeDto insert(UnidadeForm unidadeForm) {
        try {
            UnidadeModel unidadeNovo = convertUnidadeFormToUnidadeModel(unidadeForm);
            unidadeNovo = unidadeRepository.save(unidadeNovo);
            return convertUnidadeModelToUnidadeDto(unidadeNovo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Unidade não foi(foram) preenchido(s).");
        }
    }

    public UnidadeDto update(UnidadeUpdateForm unidadeUpdateForm, int idUnidade) {
        try {
            Optional<UnidadeModel> unidadeExistente = unidadeRepository.findById(idUnidade);
            if (unidadeExistente.isPresent()) {
                UnidadeModel unidadeAtualizado = unidadeExistente.get();
                unidadeAtualizado.setNome(unidadeUpdateForm.getNome());
                unidadeAtualizado.setDataAlteracao(unidadeUpdateForm.getDataAlteracao());


                unidadeRepository.save(unidadeAtualizado);
                return convertUnidadeModelToUnidadeDtoUpdate(unidadeAtualizado);
            }else{
                throw new DataIntegrityException("A ID do Unidade não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Unidade não foi(foram) preenchido(s).");
        }
    }

    public void delete(int idUnidade) {
        try {
            if (unidadeRepository.existsById (idUnidade)) {
                unidadeRepository.deleteById (idUnidade);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Unidade!");
        }
    }

    private UnidadeModel convertUnidadeFormToUnidadeModel(UnidadeForm unidadeForm) {
        UnidadeModel unidadeModel = new UnidadeModel();
        unidadeModel.setNome(unidadeForm.getNome());
        unidadeModel.setDataCadastro(unidadeForm.getDataCadastro());
        unidadeModel.setDataAlteracao(unidadeForm.getDataAlteracao());
        return unidadeModel;
    }

    private UnidadeDto convertUnidadeModelToUnidadeDto(UnidadeModel unidadeModel) {
        UnidadeDto unidadeDto = new UnidadeDto();
        unidadeDto.setNome(unidadeModel.getNome());
        unidadeDto.setDataAlteracao(unidadeModel.getDataAlteracao());
        unidadeDto.setDataCadastro(unidadeModel.getDataCadastro());
        return unidadeDto;
    }

    private UnidadeDto convertUnidadeModelToUnidadeDtoUpdate(UnidadeModel unidadeModel) {
        UnidadeDto unidadeDto = new UnidadeDto();
        unidadeDto.setNome(unidadeModel.getNome());
        unidadeDto.setDataAlteracao(unidadeModel.getDataAlteracao());
        return unidadeDto;
    }

    private List<UnidadeDto> convertListToDto(List<UnidadeModel> list){
        List<UnidadeDto> unidadeDtoList = new ArrayList<>();
        for (UnidadeModel unidadeModel : list) {
            UnidadeDto unidadeDto = this.convertUnidadeModelToUnidadeDto(unidadeModel);
            unidadeDtoList.add(unidadeDto);
        }
        return unidadeDtoList;
    }
}
