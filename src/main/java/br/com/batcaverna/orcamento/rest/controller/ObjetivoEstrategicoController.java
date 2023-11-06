package br.com.batcaverna.orcamento.rest.controller;

import br.com.batcaverna.orcamento.rest.dto.ObjetivoEstrategicoDto;
import br.com.batcaverna.orcamento.rest.form.ObjetivoEstrategicoForm;
import br.com.batcaverna.orcamento.rest.form.ObjetivoEstrategicoUpdateForm;
import br.com.batcaverna.orcamento.service.ObjetivoEstrategicoService;
import br.com.batcaverna.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/objetivoEstrategico")
public class ObjetivoEstrategicoController {

    @Autowired
    private ObjetivoEstrategicoService objetivoEstrategicoService;

    @GetMapping
    public ResponseEntity<List<ObjetivoEstrategicoDto>> findAll() {
        List<ObjetivoEstrategicoDto> objetivoEstrategicoDtoList = objetivoEstrategicoService.findAll();
        return ResponseEntity.ok().body(objetivoEstrategicoDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjetivoEstrategicoDto> find(@PathVariable("id") int id) {
        ObjetivoEstrategicoDto objetivoEstrategicoDto = objetivoEstrategicoService.findById(id);
        return ResponseEntity.ok().body(objetivoEstrategicoDto);
    }

    @PostMapping
    public ResponseEntity<ObjetivoEstrategicoDto> insert(@Valid @RequestBody ObjetivoEstrategicoForm objetivoEstrategicoForm, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        ObjetivoEstrategicoDto objetivoEstrategicoDto = objetivoEstrategicoService.insert(objetivoEstrategicoForm);
        return ResponseEntity.ok().body(objetivoEstrategicoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjetivoEstrategicoDto> update(@Valid @RequestBody ObjetivoEstrategicoUpdateForm objetivoEstrategicoUpdateForm
            , @PathVariable("id") int id, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        ObjetivoEstrategicoDto objetivoEstrategicoDto = objetivoEstrategicoService.update(objetivoEstrategicoUpdateForm, id);
        return ResponseEntity.ok().body(objetivoEstrategicoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        objetivoEstrategicoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
