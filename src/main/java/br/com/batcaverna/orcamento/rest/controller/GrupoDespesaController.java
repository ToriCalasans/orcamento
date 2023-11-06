package br.com.batcaverna.orcamento.rest.controller;

import br.com.batcaverna.orcamento.rest.dto.GrupoDespesaDto;
import br.com.batcaverna.orcamento.rest.form.GrupoDespesaForm;
import br.com.batcaverna.orcamento.rest.form.GrupoDespesaUpdateForm;
import br.com.batcaverna.orcamento.service.GrupoDespesaService;
import br.com.batcaverna.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupoDespesa")
public class GrupoDespesaController {
    @Autowired
    private GrupoDespesaService grupoDespesaService;

    @GetMapping
    public ResponseEntity<List<GrupoDespesaDto>> findAll() {
        List<GrupoDespesaDto> grupoDespesaDtoList = grupoDespesaService.findAll();
        return ResponseEntity.ok().body(grupoDespesaDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoDespesaDto> find(@PathVariable("id") int id) {
        GrupoDespesaDto grupoDespesaDto = grupoDespesaService.findById(id);
        return ResponseEntity.ok().body(grupoDespesaDto);
    }

    @PostMapping
    public ResponseEntity<GrupoDespesaDto> insert(@Valid @RequestBody GrupoDespesaForm grupoDespesaForm, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        GrupoDespesaDto grupoDespesaDto = grupoDespesaService.insert(grupoDespesaForm);
        return ResponseEntity.ok().body(grupoDespesaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoDespesaDto> update(@Valid @RequestBody GrupoDespesaUpdateForm grupoDespesaUpdateForm
            , @PathVariable("id") int id, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        GrupoDespesaDto grupoDespesaDto = grupoDespesaService.update(grupoDespesaUpdateForm, id);
        return ResponseEntity.ok().body(grupoDespesaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        grupoDespesaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
