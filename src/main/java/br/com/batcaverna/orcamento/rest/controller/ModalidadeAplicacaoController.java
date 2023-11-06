package br.com.batcaverna.orcamento.rest.controller;

import br.com.batcaverna.orcamento.rest.dto.ModalidadeAplicacaoDto;
import br.com.batcaverna.orcamento.rest.form.ModalidadeAplicacaoForm;
import br.com.batcaverna.orcamento.rest.form.ModalidadeAplicacaoUpdateForm;
import br.com.batcaverna.orcamento.service.ModalidadeAplicacaoService;
import br.com.batcaverna.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/modalidadeAplicacao")
public class ModalidadeAplicacaoController {
    @Autowired
    private ModalidadeAplicacaoService modalidadeAplicacaoService;

    @GetMapping
    public ResponseEntity<List<ModalidadeAplicacaoDto>> findAll() {
        List<ModalidadeAplicacaoDto> modalidadeAplicacaoDtoList = modalidadeAplicacaoService.findAll();
        return ResponseEntity.ok().body(modalidadeAplicacaoDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModalidadeAplicacaoDto> find(@PathVariable("id") int id) {
        ModalidadeAplicacaoDto modalidadeAplicacaoDto = modalidadeAplicacaoService.findById(id);
        return ResponseEntity.ok().body(modalidadeAplicacaoDto);
    }

    @PostMapping
    public ResponseEntity<ModalidadeAplicacaoDto> insert(@Valid @RequestBody ModalidadeAplicacaoForm modalidadeAplicacaoForm, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        ModalidadeAplicacaoDto modalidadeAplicacaoDto = modalidadeAplicacaoService.insert(modalidadeAplicacaoForm);
        return ResponseEntity.ok().body(modalidadeAplicacaoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModalidadeAplicacaoDto> update(@Valid @RequestBody ModalidadeAplicacaoUpdateForm modalidadeAplicacaoUpdateForm
            , @PathVariable("id") int id, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        ModalidadeAplicacaoDto modalidadeAplicacaoDto = modalidadeAplicacaoService.update(modalidadeAplicacaoUpdateForm, id);
        return ResponseEntity.ok().body(modalidadeAplicacaoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        modalidadeAplicacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
