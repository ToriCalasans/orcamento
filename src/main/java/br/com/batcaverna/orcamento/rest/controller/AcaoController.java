package br.com.batcaverna.orcamento.rest.controller;

import br.com.batcaverna.orcamento.rest.dto.AcaoDto;
import br.com.batcaverna.orcamento.rest.form.AcaoForm;
import br.com.batcaverna.orcamento.rest.form.AcaoUpdateForm;
import br.com.batcaverna.orcamento.service.AcaoService;
import br.com.batcaverna.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/acao")
public class AcaoController {

    @Autowired
    private AcaoService acaoService;

    @GetMapping
    public ResponseEntity<List<AcaoDto>> findAll() {
        List<AcaoDto> acaoDtoList = acaoService.findAll();
        return ResponseEntity.ok().body(acaoDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcaoDto> find(@PathVariable("id") int id) {
        AcaoDto acaoDto = acaoService.findById(id);
        return ResponseEntity.ok().body(acaoDto);
    }

    @PostMapping
    public ResponseEntity<AcaoDto> insert(@Valid @RequestBody AcaoForm acaoForm, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        AcaoDto acaoDto = acaoService.insert(acaoForm);
        return ResponseEntity.ok().body(acaoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcaoDto> update(@Valid @RequestBody AcaoUpdateForm acaoUpdateForm
            , @PathVariable("id") int id, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        AcaoDto acaoDto = acaoService.update(acaoUpdateForm, id);
        return ResponseEntity.ok().body(acaoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        acaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
