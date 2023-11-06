package br.com.batcaverna.orcamento.rest.controller;


import br.com.batcaverna.orcamento.rest.dto.ElementoDespesaDto;
import br.com.batcaverna.orcamento.rest.form.ElementoDespesaForm;
import br.com.batcaverna.orcamento.rest.form.ElementoDespesaUpdateForm;
import br.com.batcaverna.orcamento.service.ElementoDespesaService;
import br.com.batcaverna.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/elementoDespesa")
public class ElementoDespesaController {
    @Autowired
    private ElementoDespesaService elementoDespesaService;

    @GetMapping
    public ResponseEntity<List<ElementoDespesaDto>> findAll() {
        List<ElementoDespesaDto> elementoDespesaDtoList = elementoDespesaService.findAll();
        return ResponseEntity.ok().body(elementoDespesaDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElementoDespesaDto> find(@PathVariable("id") int id) {
        ElementoDespesaDto elementoDespesaDto = elementoDespesaService.findById(id);
        return ResponseEntity.ok().body(elementoDespesaDto);
    }

    @PostMapping
    public ResponseEntity<ElementoDespesaDto> insert(@Valid @RequestBody ElementoDespesaForm elementoDespesaForm, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        ElementoDespesaDto elementoDespesaDto = elementoDespesaService.insert(elementoDespesaForm);
        return ResponseEntity.ok().body(elementoDespesaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ElementoDespesaDto> update(@Valid @RequestBody ElementoDespesaUpdateForm elementoDespesaUpdateForm
            , @PathVariable("id") int id, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        ElementoDespesaDto elementoDespesaDto = elementoDespesaService.update(elementoDespesaUpdateForm, id);
        return ResponseEntity.ok().body(elementoDespesaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        elementoDespesaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
