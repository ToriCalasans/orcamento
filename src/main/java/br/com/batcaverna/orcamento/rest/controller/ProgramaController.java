package br.com.batcaverna.orcamento.rest.controller;

import br.com.batcaverna.orcamento.rest.dto.ProgramaDto;
import br.com.batcaverna.orcamento.rest.form.ProgramaForm;
import br.com.batcaverna.orcamento.rest.form.ProgramaUpdateForm;
import br.com.batcaverna.orcamento.service.ProgramaService;
import br.com.batcaverna.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/programa")
public class ProgramaController {
    @Autowired
    private ProgramaService programaService;

    @GetMapping
    public ResponseEntity<List<ProgramaDto>> findAll() {
        List<ProgramaDto> programaDtoList = programaService.findAll();
        return ResponseEntity.ok().body(programaDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramaDto> find(@PathVariable("id") int id) {
        ProgramaDto programaDto = programaService.findById(id);
        return ResponseEntity.ok().body(programaDto);
    }

    @PostMapping
    public ResponseEntity<ProgramaDto> insert(@Valid @RequestBody ProgramaForm programaForm, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        ProgramaDto programaDto = programaService.insert(programaForm);
        return ResponseEntity.ok().body(programaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramaDto> update(@Valid @RequestBody ProgramaUpdateForm programaUpdateForm
            , @PathVariable("id") int id, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        ProgramaDto programaDto = programaService.update(programaUpdateForm, id);
        return ResponseEntity.ok().body(programaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        programaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
