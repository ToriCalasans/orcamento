package br.com.batcaverna.orcamento.rest.controller;

@RestController
@RequestMapping("/lancamento")
public class LacamentosController {
    @Autowired
    private LancamentoService lancamentoService;

    @GetMapping
    public ResponseEntity<List<LancamentoDto>> findAll() {
        List<LancamentoDto> lancamentoDtoList = lancamentoService.ObterTodos();
        return ResponseEntity.ok().body(lancamentoDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LancamentoDto> find(@PathVariable("id") int id) {
        LancamentoDto lancamentoDto = lancamentoService.ObterPorId(id);
        return ResponseEntity.ok().body(lancamentoDto);
    }

    @PostMapping
    public ResponseEntity<LancamentoDto> insert(@Valid @RequestBody LancamentoForm lancamentoForm, BindingResult br) {
        if (br.hasErrors()) {
            String errorMessage = br.getAllErrors().get(0).getDefaultMessage();
            throw new ConstraintException(errorMessage);
        }
        LancamentoDto lancamentoDto = lancamentoService.SalvarLancamento(lancamentoForm);
        return ResponseEntity.ok().body(lancamentoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LancamentoDto> update(@Valid @RequestBody LancamentoUpdateForm lancamentoUpdateForm,
                                                @PathVariable("id") int id, BindingResult br) {
        if (br.hasErrors()) {
            String errorMessage = br.getAllErrors().get(0).getDefaultMessage();
            throw new ConstraintException(errorMessage);
        }
        LancamentoDto lancamentoDto = lancamentoService.AtualizarLancamento(lancamentoUpdateForm, id);
        return ResponseEntity.ok().body(lancamentoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        lancamentoService.RemoverLancamento(id);
        return ResponseEntity.noContent().build();
    }
}
