package br.com.batcaverna.orcamento.service;

import br.com.batcaverna.orcamento.model.ElementoDespesaModel;
import br.com.batcaverna.orcamento.repository.ElementoDespesaRepository;
import br.com.batcaverna.orcamento.rest.dto.ElementoDespesaDto;
import br.com.batcaverna.orcamento.rest.form.ElementoDespesaForm;
import br.com.batcaverna.orcamento.rest.form.ElementoDespesaUpdateForm;
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
public class ElementoDespesaService {
    @Autowired
    ElementoDespesaRepository elementoDespesaRepository;

    public ElementoDespesaDto findById(int idElementoDespesa) {
        try {
            ElementoDespesaModel elementoDespesaModel = elementoDespesaRepository.findById(idElementoDespesa).get();
            return convertElementoDespesaModelToElementoDespesaDtoUpdate(elementoDespesaModel);
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + idElementoDespesa + ", Tipo: " + ElementoDespesaModel.class.getName());
        }
    }

    public List<ElementoDespesaDto> findAll(){
        List<ElementoDespesaModel> elementoDespesaoList = elementoDespesaRepository.findAll();
        return convertListToDto(elementoDespesaoList);
    }

    public ElementoDespesaDto insert(ElementoDespesaForm elementoDespesaForm) {
        try {
            ElementoDespesaModel elementoDespesaNovo = convertElementoDespesaFormToElementoDespesaModel(elementoDespesaForm);
            Optional<ElementoDespesaModel> byCodigo = elementoDespesaRepository.findByCodigo(elementoDespesaNovo.getCodigo());
            if (byCodigo.isPresent()) {
                throw new IllegalStateException("Codigo já registrado.");
            }
            elementoDespesaNovo = elementoDespesaRepository.save(elementoDespesaNovo);
            return convertElementoDespesaModelToElementoDespesaDto(elementoDespesaNovo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do ElementoDespesa não foi(foram) preenchido(s).");
        }
    }

    public ElementoDespesaDto update(ElementoDespesaUpdateForm elementoDespesaUpdateForm, int idElementoDespesa) {
        try {
            Optional<ElementoDespesaModel> elementoDespesaExistente = elementoDespesaRepository.findById(idElementoDespesa);
            if (elementoDespesaExistente.isPresent()) {
                ElementoDespesaModel elementoDespesaAtualizado = elementoDespesaExistente.get();
                elementoDespesaAtualizado.setNome(elementoDespesaUpdateForm.getNome());
                elementoDespesaAtualizado.setCodigo(elementoDespesaUpdateForm.getCodigo());
                elementoDespesaAtualizado.setDataAlteracao(elementoDespesaUpdateForm.getDataAlteracao());


                elementoDespesaRepository.save(elementoDespesaAtualizado);
                return convertElementoDespesaModelToElementoDespesaDtoUpdate(elementoDespesaAtualizado);
            }else{
                throw new DataIntegrityException("A ID do ElementoDespesa não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do ElementoDespesa não foi(foram) preenchido(s).");
        }
    }

    public void delete(int idElementoDespesa) {
        try {
            if (elementoDespesaRepository.existsById(idElementoDespesa)) {
                elementoDespesaRepository.deleteById(idElementoDespesa);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um ElementoDespesa!");
        }
    }

    private ElementoDespesaModel convertElementoDespesaFormToElementoDespesaModel(ElementoDespesaForm elementoDespesaForm) {
        ElementoDespesaModel elementoDespesaModel = new ElementoDespesaModel();
        elementoDespesaModel.setNome(elementoDespesaForm.getNome());
        elementoDespesaModel.setCodigo(elementoDespesaForm.getCodigo());
        elementoDespesaModel.setDataCadastro(elementoDespesaForm.getDataCadastro());
        elementoDespesaModel.setDataAlteracao(elementoDespesaForm.getDataAlteracao());
        return elementoDespesaModel;
    }

    private ElementoDespesaDto convertElementoDespesaModelToElementoDespesaDto(ElementoDespesaModel elementoDespesaModel) {
        ElementoDespesaDto elementoDespesaDto = new ElementoDespesaDto();
        elementoDespesaDto.setNome(elementoDespesaModel.getNome());
        elementoDespesaDto.setCodigo(elementoDespesaModel.getCodigo());
        elementoDespesaDto.setDataAlteracao(elementoDespesaModel.getDataAlteracao());
        elementoDespesaDto.setDataCadastro(elementoDespesaModel.getDataCadastro());
        return elementoDespesaDto;
    }

    private ElementoDespesaDto convertElementoDespesaModelToElementoDespesaDtoUpdate(ElementoDespesaModel elementoDespesaModel) {
        ElementoDespesaDto elementoDespesaDto = new ElementoDespesaDto();
        elementoDespesaDto.setNome(elementoDespesaModel.getNome());
        elementoDespesaDto.setCodigo(elementoDespesaModel.getCodigo());
        elementoDespesaDto.setDataAlteracao(elementoDespesaModel.getDataAlteracao());
        return elementoDespesaDto;
    }

    private List<ElementoDespesaDto> convertListToDto(List<ElementoDespesaModel> list){
        List<ElementoDespesaDto> elementoDespesaDtoList = new ArrayList<>();
        for (ElementoDespesaModel elementoDespesaModel : list) {
            ElementoDespesaDto elementoDespesaDto = this.convertElementoDespesaModelToElementoDespesaDto(elementoDespesaModel);
            elementoDespesaDtoList.add(elementoDespesaDto);
        }
        return elementoDespesaDtoList;
    }
}
