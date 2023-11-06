package br.com.batcaverna.orcamento.service;

import br.com.batcaverna.orcamento.model.FonteRecursoModel;
import br.com.batcaverna.orcamento.repository.FonteRecursoRepository;
import br.com.batcaverna.orcamento.rest.dto.FonteRecursoDto;
import br.com.batcaverna.orcamento.rest.form.FonteRecursoForm;
import br.com.batcaverna.orcamento.rest.form.FonteRecursoUpdateForm;
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
public class FonteRecursoService {
    @Autowired
    FonteRecursoRepository fonteRecursoRepository;
    public FonteRecursoDto findById(int idFonteRecurso) {
        try {
            FonteRecursoModel fonteRecursoModel = fonteRecursoRepository.findById(idFonteRecurso).get();
            return convertFonteRecursoModelToFonteRecursoDto(fonteRecursoModel);
        } catch (NoSuchElementException e) {
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + idFonteRecurso + ", Tipo: " + FonteRecursoModel.class.getName());
        }
    }

    public List<FonteRecursoDto> findAll(){
        List<FonteRecursoModel> fonteRecursoList = fonteRecursoRepository.findAll();
        return convertListToDto(fonteRecursoList);
    }

    public FonteRecursoDto insert(FonteRecursoForm fonteRecursoForm) {
        try {
            FonteRecursoModel fonteRecursoNovo = convertFonteRecursoFormToFonteRecursoModel(fonteRecursoForm);
            Optional<FonteRecursoModel> byCodigo = fonteRecursoRepository.findByCodigo(fonteRecursoNovo.getCodigo());
            if (byCodigo.isPresent()) {
                throw new IllegalStateException("Codigo já registrado.");
            }
            fonteRecursoNovo = fonteRecursoRepository.save(fonteRecursoNovo);
            return convertFonteRecursoModelToFonteRecursoDto(fonteRecursoNovo);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do FonteRecurso não foi(foram) preenchido(s).");
        }
    }

    public FonteRecursoDto update(FonteRecursoUpdateForm fonteRecursoUpdateForm, int idFonteRecurso) {
        try {
            Optional<FonteRecursoModel> fonteRecursoExistente = fonteRecursoRepository.findById(idFonteRecurso);
            if (fonteRecursoExistente.isPresent()) {
                FonteRecursoModel fonteRecursoAtualizado = fonteRecursoExistente.get();
                fonteRecursoAtualizado.setNome(fonteRecursoUpdateForm.getNome());
                fonteRecursoAtualizado.setCodigo(fonteRecursoUpdateForm.getCodigo());
                fonteRecursoAtualizado.setDataAlteracao(fonteRecursoUpdateForm.getDataAlteracao());


                fonteRecursoRepository.save(fonteRecursoAtualizado);
                return convertFonteRecursoModelToFonteRecursoDtoUpdate(fonteRecursoAtualizado);
            }else{
                throw new DataIntegrityException("A ID do FonteRecurso não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do FonteRecurso não foi(foram) preenchido(s).");
        }
    }

    public void delete(int idFonteRecurso) {
        try {
            if (fonteRecursoRepository.existsById (idFonteRecurso)) {
                fonteRecursoRepository.deleteById (idFonteRecurso);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um FonteRecurso!");
        }
    }

    private FonteRecursoModel convertFonteRecursoFormToFonteRecursoModel(FonteRecursoForm fonteRecursoForm) {
        FonteRecursoModel fonteRecursoModel = new FonteRecursoModel();
        fonteRecursoModel.setNome(fonteRecursoForm.getNome());
        fonteRecursoModel.setCodigo(fonteRecursoForm.getCodigo());
        fonteRecursoModel.setDataCadastro(fonteRecursoForm.getDataCadastro());
        fonteRecursoModel.setDataAlteracao(fonteRecursoForm.getDataAlteracao());
        return fonteRecursoModel;
    }

    private FonteRecursoDto convertFonteRecursoModelToFonteRecursoDto(FonteRecursoModel fonteRecursoModel) {
        FonteRecursoDto fonteRecursoDto = new FonteRecursoDto();
        fonteRecursoDto.setNome(fonteRecursoModel.getNome());
        fonteRecursoDto.setCodigo(fonteRecursoModel.getCodigo());
        fonteRecursoDto.setDataAlteracao(fonteRecursoModel.getDataAlteracao());
        fonteRecursoDto.setDataCadastro(fonteRecursoModel.getDataCadastro());
        return fonteRecursoDto;
    }

    private FonteRecursoDto convertFonteRecursoModelToFonteRecursoDtoUpdate(FonteRecursoModel fonteRecursoModel) {
        FonteRecursoDto fonteRecursoDto = new FonteRecursoDto();
        fonteRecursoDto.setNome(fonteRecursoModel.getNome());
        fonteRecursoDto.setCodigo(fonteRecursoModel.getCodigo());
        fonteRecursoDto.setDataAlteracao(fonteRecursoModel.getDataAlteracao());
        return fonteRecursoDto;
    }

    private List<FonteRecursoDto> convertListToDto(List<FonteRecursoModel> list){
        List<FonteRecursoDto> fonteRecursoDtoList = new ArrayList<>();
        for (FonteRecursoModel fonteRecursoModel : list) {
            FonteRecursoDto fonteRecursoDto = this.convertFonteRecursoModelToFonteRecursoDto(fonteRecursoModel);
            fonteRecursoDtoList.add(fonteRecursoDto);
        }
        return fonteRecursoDtoList;
    }
}
