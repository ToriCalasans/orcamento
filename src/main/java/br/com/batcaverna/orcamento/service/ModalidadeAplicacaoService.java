package br.com.batcaverna.orcamento.service;

import br.com.batcaverna.orcamento.model.ModalidadeAplicacaoModel;
import br.com.batcaverna.orcamento.repository.ModalidadeAplicacaoRepository;
import br.com.batcaverna.orcamento.rest.dto.ModalidadeAplicacaoDto;
import br.com.batcaverna.orcamento.rest.form.ModalidadeAplicacaoForm;
import br.com.batcaverna.orcamento.rest.form.ModalidadeAplicacaoUpdateForm;
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
public class ModalidadeAplicacaoService {
    @Autowired
    ModalidadeAplicacaoRepository modalidadeAplicacaoRepository;

    public ModalidadeAplicacaoDto findById(int idModalidadeAplicacao) {
        try {
            ModalidadeAplicacaoModel modalidadeAplicacaoModel = modalidadeAplicacaoRepository.findById(idModalidadeAplicacao).get();
            return convertModalidadeAplicacaoModelToModalidadeAplicacaoDto(modalidadeAplicacaoModel);
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + idModalidadeAplicacao + ", Tipo: " + ModalidadeAplicacaoModel.class.getName());
        }
    }

    public List<ModalidadeAplicacaoDto> findAll(){
        List<ModalidadeAplicacaoModel> modalidadeAplicacaoList = modalidadeAplicacaoRepository.findAll();
        return convertListToDto(modalidadeAplicacaoList);
    }

    public ModalidadeAplicacaoDto insert(ModalidadeAplicacaoForm modalidadeAplicacaoForm) {
        try {
            ModalidadeAplicacaoModel modalidadeAplicacaoNovo = convertModalidadeAplicacaoFormToModalidadeAplicacaoModel(modalidadeAplicacaoForm);
            Optional<ModalidadeAplicacaoModel> byCodigo = modalidadeAplicacaoRepository.findByCodigo(modalidadeAplicacaoNovo.getCodigo());
            if (byCodigo.isPresent()) {
                throw new IllegalStateException("Codigo já registrado.");
            }
            modalidadeAplicacaoNovo = modalidadeAplicacaoRepository.save(modalidadeAplicacaoNovo);
            return convertModalidadeAplicacaoModelToModalidadeAplicacaoDto(modalidadeAplicacaoNovo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do ModalidadeAplicacao não foi(foram) preenchido(s).");
        }
    }

    public ModalidadeAplicacaoDto update(ModalidadeAplicacaoUpdateForm modalidadeAplicacaoUpdateForm, int idModalidadeAplicacao) {
        try {
            Optional<ModalidadeAplicacaoModel> modalidadeAplicacaoExistente = modalidadeAplicacaoRepository.findById(idModalidadeAplicacao);
            if (modalidadeAplicacaoExistente.isPresent()) {
                ModalidadeAplicacaoModel modalidadeAplicacaoAtualizado = modalidadeAplicacaoExistente.get();
                modalidadeAplicacaoAtualizado.setNome(modalidadeAplicacaoUpdateForm.getNome());
                modalidadeAplicacaoAtualizado.setCodigo(modalidadeAplicacaoUpdateForm.getCodigo());
                modalidadeAplicacaoAtualizado.setDataAlteracao(modalidadeAplicacaoUpdateForm.getDataAlteracao());


                modalidadeAplicacaoRepository.save(modalidadeAplicacaoAtualizado);
                return convertModalidadeAplicacaoModelToModalidadeAplicacaoDtoUpdate(modalidadeAplicacaoAtualizado);
            }else{
                throw new DataIntegrityException("A ID do ModalidadeAplicacao não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do ModalidadeAplicacao não foi(foram) preenchido(s).");
        }
    }

    public void delete(int idModalidadeAplicacao) {
        try {
            if (modalidadeAplicacaoRepository.existsById (idModalidadeAplicacao)) {
                modalidadeAplicacaoRepository.deleteById (idModalidadeAplicacao);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um ModalidadeAplicacao!");
        }
    }

    private ModalidadeAplicacaoModel convertModalidadeAplicacaoFormToModalidadeAplicacaoModel(ModalidadeAplicacaoForm modalidadeAplicacaoForm) {
        ModalidadeAplicacaoModel modalidadeAplicacaoModel = new ModalidadeAplicacaoModel();
        modalidadeAplicacaoModel.setNome(modalidadeAplicacaoForm.getNome());
        modalidadeAplicacaoModel.setCodigo(modalidadeAplicacaoForm.getCodigo());
        modalidadeAplicacaoModel.setDataCadastro(modalidadeAplicacaoForm.getDataCadastro());
        modalidadeAplicacaoModel.setDataAlteracao(modalidadeAplicacaoForm.getDataAlteracao());
        return modalidadeAplicacaoModel;
    }

    private ModalidadeAplicacaoDto convertModalidadeAplicacaoModelToModalidadeAplicacaoDto(ModalidadeAplicacaoModel modalidadeAplicacaoModel) {
        ModalidadeAplicacaoDto modalidadeAplicacaoDto = new ModalidadeAplicacaoDto();
        modalidadeAplicacaoDto.setNome(modalidadeAplicacaoModel.getNome());
        modalidadeAplicacaoDto.setCodigo(modalidadeAplicacaoModel.getCodigo());
        modalidadeAplicacaoDto.setDataAlteracao(modalidadeAplicacaoModel.getDataAlteracao());
        modalidadeAplicacaoDto.setDataCadastro(modalidadeAplicacaoModel.getDataCadastro());
        return modalidadeAplicacaoDto;
    }

    private ModalidadeAplicacaoDto convertModalidadeAplicacaoModelToModalidadeAplicacaoDtoUpdate(ModalidadeAplicacaoModel modalidadeAplicacaoModel) {
        ModalidadeAplicacaoDto modalidadeAplicacaoDto = new ModalidadeAplicacaoDto();
        modalidadeAplicacaoDto.setNome(modalidadeAplicacaoModel.getNome());
        modalidadeAplicacaoDto.setCodigo(modalidadeAplicacaoModel.getCodigo());
        modalidadeAplicacaoDto.setDataAlteracao(modalidadeAplicacaoModel.getDataAlteracao());
        return modalidadeAplicacaoDto;
    }

    private List<ModalidadeAplicacaoDto> convertListToDto(List<ModalidadeAplicacaoModel> list){
        List<ModalidadeAplicacaoDto> modalidadeAplicacaoDtoList = new ArrayList<>();
        for (ModalidadeAplicacaoModel modalidadeAplicacaoModel : list) {
            ModalidadeAplicacaoDto modalidadeAplicacaoDto = this.convertModalidadeAplicacaoModelToModalidadeAplicacaoDto(modalidadeAplicacaoModel);
            modalidadeAplicacaoDtoList.add(modalidadeAplicacaoDto);
        }
        return modalidadeAplicacaoDtoList;
    }
}
