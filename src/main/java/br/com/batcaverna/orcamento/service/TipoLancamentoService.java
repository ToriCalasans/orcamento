package br.com.batcaverna.orcamento.service;

import br.com.batcaverna.orcamento.model.TipoLancamentoModel;
import br.com.batcaverna.orcamento.repository.TipoLancamentoRepository;
import br.com.batcaverna.orcamento.rest.dto.TipoLancamentoDto;
import br.com.batcaverna.orcamento.rest.form.TipoLancamentoForm;
import br.com.batcaverna.orcamento.rest.form.TipoLancamentoUpdateForm;
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
public class TipoLancamentoService {
    @Autowired
    TipoLancamentoRepository tipoLancamentoRepository;

    public TipoLancamentoDto findById(int idTipoLancamento) {
        try {
            TipoLancamentoModel tipoLancamentoModel = tipoLancamentoRepository.findById(idTipoLancamento).get();
            return convertTipoLancamentoModelToTipoLancamentoDto(tipoLancamentoModel);
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + idTipoLancamento + ", Tipo: " + TipoLancamentoModel.class.getName());
        }
    }

    public List<TipoLancamentoDto> findAll(){
        List<TipoLancamentoModel> tipoLancamentoList = tipoLancamentoRepository.findAll();
        return convertListToDto(tipoLancamentoList);
    }

    public TipoLancamentoDto insert(TipoLancamentoForm tipoLancamentoForm) {
        try {
            TipoLancamentoModel tipoLancamentoNovo = convertTipoLancamentoFormToTipoLancamentoModel(tipoLancamentoForm);
            tipoLancamentoNovo = tipoLancamentoRepository.save(tipoLancamentoNovo);
            return convertTipoLancamentoModelToTipoLancamentoDto(tipoLancamentoNovo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do TipoLancamento não foi(foram) preenchido(s).");
        }
    }

    public TipoLancamentoDto update(TipoLancamentoUpdateForm tipoLancamentoUpdateForm, int idTipoLancamento) {
        try {
            Optional<TipoLancamentoModel> tipoLancamentoExistente = tipoLancamentoRepository.findById(idTipoLancamento);
            if (tipoLancamentoExistente.isPresent()) {
                TipoLancamentoModel tipoLancamentoAtualizado = tipoLancamentoExistente.get();
                tipoLancamentoAtualizado.setNome(tipoLancamentoUpdateForm.getNome());
                tipoLancamentoAtualizado.setDataAlteracao(tipoLancamentoUpdateForm.getDataAlteracao());


                tipoLancamentoRepository.save(tipoLancamentoAtualizado);
                return convertTipoLancamentoModelToTipoLancamentoDtoUpdate(tipoLancamentoAtualizado);
            }else{
                throw new DataIntegrityException("A ID do TipoLancamento não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do TipoLancamento não foi(foram) preenchido(s).");
        }
    }

    public void delete(int idTipoLancamento) {
        try {
            if (tipoLancamentoRepository.existsById (idTipoLancamento)) {
                tipoLancamentoRepository.deleteById (idTipoLancamento);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um TipoLancamento!");
        }
    }

    private TipoLancamentoModel convertTipoLancamentoFormToTipoLancamentoModel(TipoLancamentoForm tipoLancamentoForm) {
        TipoLancamentoModel tipoLancamentoModel = new TipoLancamentoModel();
        tipoLancamentoModel.setNome(tipoLancamentoForm.getNome());
        tipoLancamentoModel.setDataCadastro(tipoLancamentoForm.getDataCadastro());
        tipoLancamentoModel.setDataAlteracao(tipoLancamentoForm.getDataAlteracao());
        return tipoLancamentoModel;
    }

    private TipoLancamentoDto convertTipoLancamentoModelToTipoLancamentoDto(TipoLancamentoModel tipoLancamentoModel) {
        TipoLancamentoDto tipoLancamentoDto = new TipoLancamentoDto();
        tipoLancamentoDto.setNome(tipoLancamentoModel.getNome());
        tipoLancamentoDto.setDataAlteracao(tipoLancamentoModel.getDataAlteracao());
        tipoLancamentoDto.setDataCadastro(tipoLancamentoModel.getDataCadastro());
        return tipoLancamentoDto;
    }

    private TipoLancamentoDto convertTipoLancamentoModelToTipoLancamentoDtoUpdate(TipoLancamentoModel tipoLancamentoModel) {
        TipoLancamentoDto tipoLancamentoDto = new TipoLancamentoDto();
        tipoLancamentoDto.setNome(tipoLancamentoModel.getNome());
        tipoLancamentoDto.setDataAlteracao(tipoLancamentoModel.getDataAlteracao());
        return tipoLancamentoDto;
    }

    private List<TipoLancamentoDto> convertListToDto(List<TipoLancamentoModel> list){
        List<TipoLancamentoDto> tipoLancamentoDtoList = new ArrayList<>();
        for (TipoLancamentoModel tipoLancamentoModel : list) {
            TipoLancamentoDto tipoLancamentoDto = this.convertTipoLancamentoModelToTipoLancamentoDto(tipoLancamentoModel);
            tipoLancamentoDtoList.add(tipoLancamentoDto);
        }
        return tipoLancamentoDtoList;
    }
}
