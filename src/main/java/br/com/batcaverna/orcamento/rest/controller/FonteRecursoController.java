package br.com.batcaverna.orcamento.rest.controller;

import br.com.batcaverna.orcamento.rest.dto.FonteRecursoDto;
import br.com.batcaverna.orcamento.rest.form.FonteRecursoForm;
import br.com.batcaverna.orcamento.rest.form.FonteRecursoUpdateForm;
import br.com.batcaverna.orcamento.service.FonteRecursoService;
import br.com.batcaverna.orcamento.service.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fonteRecurso")
public class FonteRecursoController {
    @Autowired
    private FonteRecursoService fonteRecursoService;

    @GetMapping
    public ResponseEntity<List<FonteRecursoDto>> findAll() {
        List<FonteRecursoDto> fonteRecursoDtoList = fonteRecursoService.findAll();
        return ResponseEntity.ok().body(fonteRecursoDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FonteRecursoDto> find(@PathVariable("id") int id) {
        FonteRecursoDto fonteRecursoDto = fonteRecursoService.findById(id);
        return ResponseEntity.ok().body(fonteRecursoDto);
    }

    @PostMapping
    public ResponseEntity<FonteRecursoDto> insert(@Valid @RequestBody FonteRecursoForm fonteRecursoForm, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        FonteRecursoDto fonteRecursoDto = fonteRecursoService.insert(fonteRecursoForm);
        return ResponseEntity.ok().body(fonteRecursoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FonteRecursoDto> update(@Valid @RequestBody FonteRecursoUpdateForm fonteRecursoUpdateForm
            , @PathVariable("id") int id, BindingResult br) {
        if (br.hasErrors())
            throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());

        FonteRecursoDto fonteRecursoDto = fonteRecursoService.update(fonteRecursoUpdateForm, id);
        return ResponseEntity.ok().body(fonteRecursoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        fonteRecursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
