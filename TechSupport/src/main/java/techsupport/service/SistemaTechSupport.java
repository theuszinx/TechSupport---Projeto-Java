package techsupport.service;

import techsupport.enums.*;
import techsupport.model.*;
import techsupport.repository.*;
import techsupport.util.ConsoleUtils;
import techsupport.util.GeradorDadosTeste;
import techsupport.strategy.Estrategias;
import techsupport.strategy.GerenciadorEstrategias;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Coordena os demais services e
 * mantém funcionalidades centrais: escalonamento, simulação e dados de teste.
 */
public class SistemaTechSupport {
    private final TecnicoRepository tecnicoRepo;
    private final EscalonadorService escalonador;
    private final GerenciadorEstrategias gerenciadorEstrategias;
    private final CadastroService cadastroService;
    private final RelatorioService relatorioService;
    // Histórico completo de todas as OS já criadas (compartilhado entre services)
    private final List<OrdemServico> historicoOS = new ArrayList<>();

    public SistemaTechSupport(TecnicoRepository tecnicoRepo, OrdemServicoRepository osRepo,
                               EscalonadorService escalonador, GerenciadorEstrategias gerenciadorEstrategias) {
        this.tecnicoRepo = tecnicoRepo;
        this.escalonador = escalonador;
        this.gerenciadorEstrategias = gerenciadorEstrategias;
        // Injeta a mesma referência de historicoOS nos sub-services
        this.cadastroService = new CadastroService(tecnicoRepo, osRepo, historicoOS);
        this.relatorioService = new RelatorioService(tecnicoRepo, osRepo, historicoOS);
    }

    // ── Delegações para CadastroService ──
    public void cadastrarTecnico() {
        cadastroService.cadastrarTecnico();
    }

    public void cadastrarOS() {
        cadastroService.cadastrarOS();
    }

    // ── Delegações para RelatorioService ──
    public void listarDados() {
        relatorioService.listarDados();
    }

    public void gerarRelatorios() {
        relatorioService.gerarRelatorios();
    }

    // ── Escalonamento (responsabilidade própria) ──
    public void escalonarOS() {
        System.out.println("\n=== ESTRATÉGIA DE ESCALONAMENTO ===");
        System.out.println("1 - FIFO (ordem de chegada)");
        System.out.println("2 - Maior prioridade");
        System.out.println("3 - Menor tempo estimado (SJF)");

        int opcao;
        while (true) {
            opcao = ConsoleUtils.lerInt("Escolha a estratégia (1, 2 ou 3): ");
            if (opcao >= 1 && opcao <= 3) break;
            System.out.println("Opção inválida! Digite 1, 2 ou 3.");
        }

        Comparator<OrdemServico> comparator = switch (opcao) {
            case 1 -> {
                System.out.println("[ESTRATÉGIA] FIFO (ordem de chegada) selecionado.");
                yield Comparator.comparingInt(OrdemServico::getId);
            }
            case 2 -> {
                System.out.println("[ESTRATÉGIA] Maior prioridade selecionada.");
                yield Estrategias.PRIORIDADE;
            }
            default -> {
                System.out.println("[ESTRATÉGIA] Menor tempo estimado (SJF) selecionado.");
                yield Estrategias.MENOR_TEMPO;
            }
        };

        escalonador.alocarProximaOS(comparator);
    }

    // ── Simulação de passagem de tempo ──
    public void simularPassagemTempo() {
        System.out.println("=== SIMULAÇÃO DE TEMPO ===");

        List<Tecnico> tecnicos = tecnicoRepo.listar();
        List<OrdemServico> todasOS = new ArrayList<>(historicoOS);

        if (tecnicos.isEmpty() && todasOS.isEmpty()) {
            System.out.println("Nenhum dado disponível para simulação.");
            return;
        }

        // 1. Concluir OS em atendimento
        long osConcluidas = todasOS.stream()
                .filter(o -> o.getStatus() == StatusOS.EM_ATENDIMENTO)
                .peek(o -> o.setStatus(StatusOS.CONCLUIDA))
                .count();

        if (osConcluidas == 0) {
            System.out.println("Nenhuma OS em atendimento para concluir.");
        }

        // 2. Liberar técnicos ocupados
        long tecnicosLiberados = tecnicos.stream()
                .filter(t -> !t.estaDisponivel())
                .peek(Tecnico::liberar)
                .count();

        if (tecnicosLiberados == 0) {
            System.out.println("Nenhum técnico ocupado no momento.");
        }

        // 3. Exibir resumo
        if (osConcluidas > 0 || tecnicosLiberados > 0) {
            System.out.println();
            if (osConcluidas    > 0) System.out.println("OS concluídas: "      + osConcluidas);
            if (tecnicosLiberados > 0) System.out.println("Técnicos liberados: " + tecnicosLiberados);
            System.out.println("\nSimulação realizada com sucesso!");
        }
    }

    // ── Geração de dados de teste ──
    public void gerarDadosTeste() {
        try {
            List<OrdemServico> novas = GeradorDadosTeste.gerarOrdens(gerenciadorEstrategias, 10);
            historicoOS.addAll(novas);
            System.out.println("10 ordens de teste geradas com sucesso.");
        } catch (techsupport.exception.CapacidadeFilaExcedidaException e) {
            System.err.println("Erro ao gerar dados: " + e.getMessage());
        }

        // Gera técnicos de teste usando o utilitário (já cria subclasses corretas)
        List<Tecnico> tecnicosTeste = new ArrayList<>();
        GeradorDadosTeste.gerarTecnicos(tecnicosTeste, 5);
        tecnicosTeste.forEach(tecnicoRepo::adicionar);
        System.out.println("Dados de teste gerados com sucesso!");
    }
}