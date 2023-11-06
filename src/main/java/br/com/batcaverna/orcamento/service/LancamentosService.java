package br.com.batcaverna.orcamento.service;

import br.com.batcaverna.orcamento.repository.TipoLancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentosService {
    @Autowired
    private TipoLancamentoRepository lancamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<LancamentosDto> obterTodos() {
        List<Lancamentos> listaLancamento = lancamentoRepository.findLancamentos();
        return listaLancamento.stream()
                .map(lancamento -> modelMapper.map(lancamento, LancamentoDto.class))
                .collect(Collectors.toList());
    }

    public LancamentoDto obterPorId(int id) {
        Lancamento lancamento = lancamentoRepository.findLancamentosPorId(id);
        if (lancamento == null) {
            throw new ObjectNotFoundException("Lançamento não encontrado. Código: " + id + ", Tipo: " + Lancamento.class.getName());
        }
        return modelMapper.map(lancamento, LancamentoDto.class);
    }

    public LancamentoDto salvarLancamento(LancamentoForm lancamentoForm) {
        try {
            Lancamento novoLancamento = modelMapper.map(lancamentoForm, Lancamento.class);
            novoLancamento.setDataCadastro(LocalDateTime.now());

            novoLancamento = lancamentoRepository.save(novoLancamento);
            return modelMapper.map(novoLancamento, LancamentoDto.class);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("FK")) {
                throw new DataIntegrityException("Ocorreu uma violação de chave estrangeira.");
            }
            throw new DataIntegrityException("Falta preenchimento de campo(s) obrigatório(s) do Lançamento.");
        }
    }

    public LancamentoDto atualizarLancamento(LancamentoUpdateForm lancamentoUpdateForm, int id) {
        Optional<Lancamento> lancamentoExistente = lancamentoRepository.findById(id);

        if (lancamentoExistente.isPresent()) {
            Lancamento lancamentoAtualizado = lancamentoExistente.get();
            lancamentoAtualizado = modelMapper.map(lancamentoUpdateForm, Lancamento.class);
            lancamentoAtualizado.setDataAlteracao(LocalDateTime.now());
            lancamentoAtualizado = lancamentoRepository.save(lancamentoAtualizado);
            return modelMapper.map(lancamentoAtualizado, LancamentoDto.class);
        } else {
            throw new ObjectNotFoundException("O Código do Lançamento não existe na base de dados.");
        }
    }

    public void removerLancamento(int id) {
        if (lancamentoRepository.existsById(id)) {
            lancamentoRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("O código do Lançamento não existe na base de dados.");
        }
    }
}
