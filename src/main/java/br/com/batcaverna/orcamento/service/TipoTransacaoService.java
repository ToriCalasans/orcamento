package br.com.batcaverna.orcamento.service;

import br.com.batcaverna.orcamento.model.TipoTransacaoModel;
import br.com.batcaverna.orcamento.repository.TipoTransacaoRepository;
import br.com.batcaverna.orcamento.rest.dto.TipoTransacaoDto;
import br.com.batcaverna.orcamento.rest.form.TipoTransacaoForm;
import br.com.batcaverna.orcamento.rest.form.TipoTransacaoUpdateForm;
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
public class TipoTransacaoService {
    @Autowired
    TipoTransacaoRepository tipoTransacaoRepository;

    public TipoTransacaoDto findById(int idTipoTransacao) {
        try {
            TipoTransacaoModel tipoTransacaoModel = tipoTransacaoRepository.findById(idTipoTransacao).get();
            return convertTipoTransacaoModelToTipoTransacaoDto(tipoTransacaoModel);
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + idTipoTransacao + ", Tipo: " + TipoTransacaoModel.class.getName());
        }
    }

    public List<TipoTransacaoDto> findAll(){
        List<TipoTransacaoModel> tipoTransacaoList = tipoTransacaoRepository.findAll();
        return convertListToDto(tipoTransacaoList);
    }

    public TipoTransacaoDto insert(TipoTransacaoForm tipoTransacaoForm) {
        try {
            TipoTransacaoModel tipoTransacaoNovo = convertTipoTransacaoFormToTipoTransacaoModel(tipoTransacaoForm);
            tipoTransacaoNovo = tipoTransacaoRepository.save(tipoTransacaoNovo);
            return convertTipoTransacaoModelToTipoTransacaoDto(tipoTransacaoNovo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do TipoTransacao não foi(foram) preenchido(s).");
        }
    }

    public TipoTransacaoDto update(TipoTransacaoUpdateForm tipoTransacaoUpdateForm, int idTipoTransacao) {
        try {
            Optional<TipoTransacaoModel> tipoTransacaoExistente = tipoTransacaoRepository.findById(idTipoTransacao);
            if (tipoTransacaoExistente.isPresent()) {
                TipoTransacaoModel tipoTransacaoAtualizado = tipoTransacaoExistente.get();
                tipoTransacaoAtualizado.setNome(tipoTransacaoUpdateForm.getNome());
                tipoTransacaoAtualizado.setDataAlteracao(tipoTransacaoUpdateForm.getDataAlteracao());


                tipoTransacaoRepository.save(tipoTransacaoAtualizado);
                return convertTipoTransacaoModelToTipoTransacaoDtoUpdate(tipoTransacaoAtualizado);
            }else{
                throw new DataIntegrityException("A ID do TipoTransacao não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do TipoTransacao não foi(foram) preenchido(s).");
        }
    }

    public void delete(int idTipoTransacao) {
        try {
            if (tipoTransacaoRepository.existsById (idTipoTransacao)) {
                tipoTransacaoRepository.deleteById (idTipoTransacao);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um TipoTransacao!");
        }
    }

    private TipoTransacaoModel convertTipoTransacaoFormToTipoTransacaoModel(TipoTransacaoForm tipoTransacaoForm) {
        TipoTransacaoModel tipoTransacaoModel = new TipoTransacaoModel();
        tipoTransacaoModel.setNome(tipoTransacaoForm.getNome());
        tipoTransacaoModel.setDataCadastro(tipoTransacaoForm.getDataCadastro());
        tipoTransacaoModel.setDataAlteracao(tipoTransacaoForm.getDataAlteracao());
        return tipoTransacaoModel;
    }

    private TipoTransacaoDto convertTipoTransacaoModelToTipoTransacaoDto(TipoTransacaoModel tipoTransacaoModel) {
        TipoTransacaoDto tipoTransacaoDto = new TipoTransacaoDto();
        tipoTransacaoDto.setNome(tipoTransacaoModel.getNome());
        tipoTransacaoDto.setDataAlteracao(tipoTransacaoModel.getDataAlteracao());
        tipoTransacaoDto.setDataCadastro(tipoTransacaoModel.getDataCadastro());
        return tipoTransacaoDto;
    }

    private TipoTransacaoDto convertTipoTransacaoModelToTipoTransacaoDtoUpdate(TipoTransacaoModel tipoTransacaoModel) {
        TipoTransacaoDto tipoTransacaoDto = new TipoTransacaoDto();
        tipoTransacaoDto.setNome(tipoTransacaoModel.getNome());
        tipoTransacaoDto.setDataAlteracao(tipoTransacaoModel.getDataAlteracao());
        return tipoTransacaoDto;
    }

    private List<TipoTransacaoDto> convertListToDto(List<TipoTransacaoModel> list){
        List<TipoTransacaoDto> tipoTransacaoDtoList = new ArrayList<>();
        for (TipoTransacaoModel tipoTransacaoModel : list) {
            TipoTransacaoDto tipoTransacaoDto = this.convertTipoTransacaoModelToTipoTransacaoDto(tipoTransacaoModel);
            tipoTransacaoDtoList.add(tipoTransacaoDto);
        }
        return tipoTransacaoDtoList;
    }
}
