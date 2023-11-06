package br.com.batcaverna.orcamento.service;

import br.com.batcaverna.orcamento.model.GrupoDespesaModel;
import br.com.batcaverna.orcamento.repository.GrupoDespesaRepository;
import br.com.batcaverna.orcamento.rest.dto.GrupoDespesaDto;
import br.com.batcaverna.orcamento.rest.form.GrupoDespesaForm;
import br.com.batcaverna.orcamento.rest.form.GrupoDespesaUpdateForm;
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
public class GrupoDespesaService {
    @Autowired
    GrupoDespesaRepository grupoDespesaRepository;

    public GrupoDespesaDto findById(int idGrupoDespesa) {
        try {
            GrupoDespesaModel grupoDespesaModel = grupoDespesaRepository.findById(idGrupoDespesa).get();
            return convertGrupoDespesaModelToGrupoDespesaDto(grupoDespesaModel);
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + idGrupoDespesa + ", Tipo: " + GrupoDespesaModel.class.getName());
        }
    }

    public List<GrupoDespesaDto> findAll(){
        List<GrupoDespesaModel> grupoDespesaList = grupoDespesaRepository.findAll();
        return convertListToDto(grupoDespesaList);
    }

    public GrupoDespesaDto insert(GrupoDespesaForm grupoDespesaForm) {
        try {
            GrupoDespesaModel grupoDespesaNovo = convertGrupoDespesaFormToGrupoDespesaModel(grupoDespesaForm);
            Optional<GrupoDespesaModel> byCodigo = grupoDespesaRepository.findByCodigo(grupoDespesaNovo.getCodigo());
            if (byCodigo.isPresent()) {
                throw new IllegalStateException("Codigo já registrado.");
            }
            grupoDespesaNovo = grupoDespesaRepository.save(grupoDespesaNovo);
            return convertGrupoDespesaModelToGrupoDespesaDto(grupoDespesaNovo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do GrupoDespesa não foi(foram) preenchido(s).");
        }
    }

    public GrupoDespesaDto update(GrupoDespesaUpdateForm grupoDespesaUpdateForm, int idGrupoDespesa) {
        try {
            Optional<GrupoDespesaModel> grupoDespesaExistente = grupoDespesaRepository.findById(idGrupoDespesa);
            if (grupoDespesaExistente.isPresent()) {
                GrupoDespesaModel grupoDespesaAtualizado = grupoDespesaExistente.get();
                grupoDespesaAtualizado.setNome(grupoDespesaUpdateForm.getNome());
                grupoDespesaAtualizado.setCodigo(grupoDespesaUpdateForm.getCodigo());
                grupoDespesaAtualizado.setDataAlteracao(grupoDespesaUpdateForm.getDataAlteracao());


                grupoDespesaRepository.save(grupoDespesaAtualizado);
                return convertGrupoDespesaModelToGrupoDespesaDtoUpdate(grupoDespesaAtualizado);
            }else{
                throw new DataIntegrityException("A ID do GrupoDespesa não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do GrupoDespesa não foi(foram) preenchido(s).");
        }
    }

    public void delete(int idGrupoDespesa) {
        try {
            if (grupoDespesaRepository.existsById (idGrupoDespesa)) {
                grupoDespesaRepository.deleteById (idGrupoDespesa);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um GrupoDespesa!");
        }
    }

    private GrupoDespesaModel convertGrupoDespesaFormToGrupoDespesaModel(GrupoDespesaForm grupoDespesaForm) {
        GrupoDespesaModel grupoDespesaModel = new GrupoDespesaModel();
        grupoDespesaModel.setNome(grupoDespesaForm.getNome());
        grupoDespesaModel.setCodigo(grupoDespesaForm.getCodigo());
        grupoDespesaModel.setDataCadastro(grupoDespesaForm.getDataCadastro());
        grupoDespesaModel.setDataAlteracao(grupoDespesaForm.getDataAlteracao());
        return grupoDespesaModel;
    }

    private GrupoDespesaDto convertGrupoDespesaModelToGrupoDespesaDto(GrupoDespesaModel grupoDespesaModel) {
        GrupoDespesaDto grupoDespesaDto = new GrupoDespesaDto();
        grupoDespesaDto.setNome(grupoDespesaModel.getNome());
        grupoDespesaDto.setCodigo(grupoDespesaModel.getCodigo());
        grupoDespesaDto.setDataAlteracao(grupoDespesaModel.getDataAlteracao());
        grupoDespesaDto.setDataCadastro(grupoDespesaModel.getDataCadastro());
        return grupoDespesaDto;
    }

    private GrupoDespesaDto convertGrupoDespesaModelToGrupoDespesaDtoUpdate(GrupoDespesaModel grupoDespesaModel) {
        GrupoDespesaDto grupoDespesaDto = new GrupoDespesaDto();
        grupoDespesaDto.setNome(grupoDespesaModel.getNome());
        grupoDespesaDto.setCodigo(grupoDespesaModel.getCodigo());
        grupoDespesaDto.setDataAlteracao(grupoDespesaModel.getDataAlteracao());
        return grupoDespesaDto;
    }

    private List<GrupoDespesaDto> convertListToDto(List<GrupoDespesaModel> list){
        List<GrupoDespesaDto> grupoDespesaDtoList = new ArrayList<>();
        for (GrupoDespesaModel grupoDespesaModel : list) {
            GrupoDespesaDto grupoDespesaDto = this.convertGrupoDespesaModelToGrupoDespesaDto(grupoDespesaModel);
            grupoDespesaDtoList.add(grupoDespesaDto);
        }
        return grupoDespesaDtoList;
    }
}
