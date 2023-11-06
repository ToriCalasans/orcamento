package br.com.batcaverna.orcamento.service;

import br.com.batcaverna.orcamento.model.AcaoModel;
import br.com.batcaverna.orcamento.repository.AcaoRepository;
import br.com.batcaverna.orcamento.rest.dto.AcaoDto;
import br.com.batcaverna.orcamento.rest.form.AcaoForm;
import br.com.batcaverna.orcamento.rest.form.AcaoUpdateForm;
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
public class AcaoService {

    @Autowired
    AcaoRepository acaoRepository;

    public AcaoDto findById(int idAcao) {
        try {
            AcaoModel acaoModel = acaoRepository.findById(idAcao).get();
            return convertAcaoModelToAcaoDto(acaoModel);
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + idAcao + ", Tipo: " + AcaoModel.class.getName());
        }
    }

    public List<AcaoDto> findAll(){
        List<AcaoModel> acaoList = acaoRepository.findAll();
        return convertListToDto(acaoList);
    }

    public AcaoDto insert(AcaoForm acaoForm) {
        try {
            AcaoModel acaoNovo = convertAcaoFormToAcaoModel(acaoForm);
            Optional<AcaoModel> byCodigo = acaoRepository.findByCodigo(acaoNovo.getCodigo());
            if (byCodigo.isPresent()) {
                throw new IllegalStateException("Codigo já registrado.");
            }
            acaoNovo = acaoRepository.save(acaoNovo);
            return convertAcaoModelToAcaoDto(acaoNovo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Acao não foi(foram) preenchido(s).");
        }
    }

    public AcaoDto update(AcaoUpdateForm acaoUpdateForm, int idAcao) {
        try {
            Optional<AcaoModel> acaoExistente = acaoRepository.findById(idAcao);
            if (acaoExistente.isPresent()) {
                AcaoModel acaoAtualizado = acaoExistente.get();
                acaoAtualizado.setNome(acaoUpdateForm.getNome());
                acaoAtualizado.setCodigo(acaoUpdateForm.getCodigo());
                acaoAtualizado.setDataAlteracao(acaoUpdateForm.getDataAlteracao());


                acaoRepository.save(acaoAtualizado);
                return convertAcaoModelToAcaoDtoUpdate(acaoAtualizado);
            }else{
                throw new DataIntegrityException("A ID do Acao não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Acao não foi(foram) preenchido(s).");
        }
    }

    public void delete(int idAcao) {
        try {
            if (acaoRepository.existsById (idAcao)) {
                acaoRepository.deleteById (idAcao);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Acao!");
        }
    }

    private AcaoModel convertAcaoFormToAcaoModel(AcaoForm acaoForm) {
        AcaoModel acaoModel = new AcaoModel();
        acaoModel.setNome(acaoForm.getNome());
        acaoModel.setCodigo(acaoForm.getCodigo());
        acaoModel.setDataCadastro(acaoForm.getDataCadastro());
        acaoModel.setDataAlteracao(acaoForm.getDataAlteracao());
        return acaoModel;
    }

    private AcaoDto convertAcaoModelToAcaoDto(AcaoModel acaoModel) {
        AcaoDto acaoDto = new AcaoDto();
        acaoDto.setNome(acaoModel.getNome());
        acaoDto.setCodigo(acaoModel.getCodigo());
        acaoDto.setDataAlteracao(acaoModel.getDataAlteracao());
        acaoDto.setDataCadastro(acaoModel.getDataCadastro());
        return acaoDto;
    }

    private AcaoDto convertAcaoModelToAcaoDtoUpdate(AcaoModel acaoModel) {
        AcaoDto acaoDto = new AcaoDto();
        acaoDto.setNome(acaoModel.getNome());
        acaoDto.setCodigo(acaoModel.getCodigo());
        acaoDto.setDataAlteracao(acaoModel.getDataAlteracao());
        return acaoDto;
    }

    private List<AcaoDto> convertListToDto(List<AcaoModel> list){
        List<AcaoDto> acaoDtoList = new ArrayList<>();
        for (AcaoModel acaoModel : list) {
            AcaoDto acaoDto = this.convertAcaoModelToAcaoDto(acaoModel);
            acaoDtoList.add(acaoDto);
        }
        return acaoDtoList;
    }
}
