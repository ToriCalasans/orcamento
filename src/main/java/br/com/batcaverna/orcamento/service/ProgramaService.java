package br.com.batcaverna.orcamento.service;

import br.com.batcaverna.orcamento.model.ProgramaModel;
import br.com.batcaverna.orcamento.repository.ProgramaRepository;
import br.com.batcaverna.orcamento.rest.dto.ProgramaDto;
import br.com.batcaverna.orcamento.rest.form.ProgramaForm;
import br.com.batcaverna.orcamento.rest.form.ProgramaUpdateForm;
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
public class ProgramaService {
    @Autowired
    ProgramaRepository programaRepository;

    public ProgramaDto findById(int idPrograma) {
        try {
            ProgramaModel programaModel = programaRepository.findById(idPrograma).get();
            return convertProgramaModelToProgramaDto(programaModel);
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + idPrograma + ", Tipo: " + ProgramaModel.class.getName());
        }
    }

    public List<ProgramaDto> findAll(){
        List<ProgramaModel> programaList = programaRepository.findAll();
        return convertListToDto(programaList);
    }

    public ProgramaDto insert(ProgramaForm programaForm) {
        try {
            ProgramaModel programaNovo = convertProgramaFormToProgramaModel(programaForm);
            Optional<ProgramaModel> byCodigo = programaRepository.findByCodigo(programaNovo.getCodigo());
            if (byCodigo.isPresent()) {
                throw new IllegalStateException("Codigo já registrado.");
            }
            programaNovo = programaRepository.save(programaNovo);
            return convertProgramaModelToProgramaDto(programaNovo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Programa não foi(foram) preenchido(s).");
        }
    }

    public ProgramaDto update(ProgramaUpdateForm programaUpdateForm, int idPrograma) {
        try {
            Optional<ProgramaModel> programaExistente = programaRepository.findById(idPrograma);
            if (programaExistente.isPresent()) {
                ProgramaModel programaAtualizado = programaExistente.get();
                programaAtualizado.setNome(programaUpdateForm.getNome());
                programaAtualizado.setCodigo(programaUpdateForm.getCodigo());
                programaAtualizado.setDataAlteracao(programaUpdateForm.getDataAlteracao());


                programaRepository.save(programaAtualizado);
                return convertProgramaModelToProgramaDtoUpdate(programaAtualizado);
            }else{
                throw new DataIntegrityException("A ID do Programa não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Programa não foi(foram) preenchido(s).");
        }
    }

    public void delete(int idPrograma) {
        try {
            if (programaRepository.existsById (idPrograma)) {
                programaRepository.deleteById (idPrograma);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um Programa!");
        }
    }

    private ProgramaModel convertProgramaFormToProgramaModel(ProgramaForm programaForm) {
        ProgramaModel programaModel = new ProgramaModel();
        programaModel.setNome(programaForm.getNome());
        programaModel.setCodigo(programaForm.getCodigo());
        programaModel.setDataCadastro(programaForm.getDataCadastro());
        programaModel.setDataAlteracao(programaForm.getDataAlteracao());
        return programaModel;
    }

    private ProgramaDto convertProgramaModelToProgramaDto(ProgramaModel programaModel) {
        ProgramaDto programaDto = new ProgramaDto();
        programaDto.setNome(programaModel.getNome());
        programaDto.setCodigo(programaModel.getCodigo());
        programaDto.setDataAlteracao(programaModel.getDataAlteracao());
        programaDto.setDataCadastro(programaModel.getDataCadastro());
        return programaDto;
    }

    private ProgramaDto convertProgramaModelToProgramaDtoUpdate(ProgramaModel programaModel) {
        ProgramaDto programaDto = new ProgramaDto();
        programaDto.setNome(programaModel.getNome());
        programaDto.setCodigo(programaModel.getCodigo());
        programaDto.setDataAlteracao(programaModel.getDataAlteracao());
        return programaDto;
    }

    private List<ProgramaDto> convertListToDto(List<ProgramaModel> list){
        List<ProgramaDto> programaDtoList = new ArrayList<>();
        for (ProgramaModel programaModel : list) {
            ProgramaDto programaDto = this.convertProgramaModelToProgramaDto(programaModel);
            programaDtoList.add(programaDto);
        }
        return programaDtoList;
    }
}
