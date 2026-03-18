package techsupport.service;

import techsupport.enums.*;
import techsupport.model.*;
import techsupport.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * listagem de dados e geração de relatórios consolidados.
 */
public class RelatorioService {
    private final TecnicoRepository tecnicoRepo;
    private final OrdemServicoRepository osRepo;
    private final List<OrdemServico> historicoOS; // Referência compartilhada ao histórico

    public RelatorioService(TecnicoRepository tecnicoRepo, OrdemServicoRepository osRepo, List<OrdemServico> historicoOS) {
        this.tecnicoRepo = tecnicoRepo;
        this.osRepo = osRepo;
        this.historicoOS = historicoOS;
    }

    // Exibe todos os dados atuais do sistema
    public void listarDados() {
        System.out.println("\n--- TÉCNICOS ---");
        tecnicoRepo.listar().forEach(System.out::println);
        
        System.out.println("\n--- FILA DE ATENDIMENTO (PENDENTES) ---");
        List<OrdemServico> ordens = osRepo.listar();
        if (ordens.isEmpty()) {
            System.out.println("Fila vazia.");
        } else {
            ordens.forEach(System.out::println);
        }

        // OS em atendimento (removidas da fila, mas registradas no histórico)
        List<OrdemServico> emAtendimento = historicoOS.stream()
                .filter(o -> o.getStatus() == StatusOS.EM_ATENDIMENTO)
                .collect(Collectors.toList());
        System.out.println("\n--- OS EM ATENDIMENTO ---");
        if (emAtendimento.isEmpty()) {
            System.out.println("Nenhuma OS em atendimento.");
        } else {
            emAtendimento.forEach(System.out::println);
        }

        // OS concluídas
        List<OrdemServico> concluidas = historicoOS.stream()
                .filter(o -> o.getStatus() == StatusOS.CONCLUIDA)
                .collect(Collectors.toList());
        System.out.println("\n--- OS CONCLUÍDAS ---");
        if (concluidas.isEmpty()) {
            System.out.println("Nenhuma OS concluída.");
        } else {
            concluidas.forEach(System.out::println);
        }
    }

    // Gera relatório consolidado com Stream API
    public void gerarRelatorios() {
        System.out.println("=== RELATÓRIOS DO SISTEMA ===");

        List<Tecnico> tecnicos = tecnicoRepo.listar();
        // Usa o histórico completo para incluir OS em atendimento e concluídas
        List<OrdemServico> ordens = new ArrayList<>(historicoOS);
        if (ordens.isEmpty()) ordens.addAll(osRepo.listar()); // fallback para compatibilidade

        // Guarda para cenário sem dados
        if (tecnicos.isEmpty() && ordens.isEmpty()) {
            System.out.println("Nenhum dado disponível para gerar relatórios.");
            return;
        }

        // ── Ordens de Serviço ──
        System.out.println("\nOrdens de Serviço:");
        System.out.println("  Total: " + ordens.size());

        // Contagem por status via filter + count
        long pendentes     = ordens.stream().filter(o -> o.getStatus() == StatusOS.PENDENTE).count();
        long emAtendimento = ordens.stream().filter(o -> o.getStatus() == StatusOS.EM_ATENDIMENTO).count();
        long concluidas    = ordens.stream().filter(o -> o.getStatus() == StatusOS.CONCLUIDA).count();
        System.out.println("  Pendentes: "      + pendentes);
        System.out.println("  Em atendimento: " + emAtendimento);
        System.out.println("  Concluídas: "     + concluidas);

        // Agrupamento por prioridade via groupingBy + counting
        Map<Prioridade, Long> porPrioridade = ordens.stream()
                .collect(Collectors.groupingBy(OrdemServico::getPrioridade, Collectors.counting()));
        System.out.println("\n  Por prioridade:");
        System.out.println("    Baixa: "   + porPrioridade.getOrDefault(Prioridade.BAIXA,   0L));
        System.out.println("    Média: "   + porPrioridade.getOrDefault(Prioridade.MEDIA,   0L));
        System.out.println("    Alta: "    + porPrioridade.getOrDefault(Prioridade.ALTA,    0L));
        System.out.println("    Crítica: " + porPrioridade.getOrDefault(Prioridade.CRITICA, 0L));

        // Filtro complexo via Stream API
        long criticasPendentes = ordens.stream()
                .filter(o -> o.getStatus() == StatusOS.PENDENTE)
                .filter(o -> o.getPrioridade() == Prioridade.ALTA || o.getPrioridade() == Prioridade.CRITICA)
                .count();
        System.out.println("\n  Atenção: " + criticasPendentes + " OS Pendentes de alta prioridade!");

        // ── Técnicos ──
        System.out.println("\nTécnicos:");
        System.out.println("  Total: " + tecnicos.size());

        // Disponíveis / Ocupados via filter + count
        long disponiveis = tecnicos.stream().filter(Tecnico::estaDisponivel).count();
        long ocupados    = tecnicos.stream().filter(t -> !t.estaDisponivel()).count();
        System.out.println("  Disponíveis: " + disponiveis);
        System.out.println("  Ocupados: "    + ocupados);

        // Agrupamento por nível via groupingBy + counting
        Map<NivelTecnico, Long> porNivel = tecnicos.stream()
                .collect(Collectors.groupingBy(Tecnico::getNivel, Collectors.counting()));
        System.out.println("\n  Por nível:");
        System.out.println("    Junior: " + porNivel.getOrDefault(NivelTecnico.JUNIOR, 0L));
        System.out.println("    Pleno: "  + porNivel.getOrDefault(NivelTecnico.PLENO,  0L));
        System.out.println("    Senior: " + porNivel.getOrDefault(NivelTecnico.SENIOR, 0L));
    }
}
