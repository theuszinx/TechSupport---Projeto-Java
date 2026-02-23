package techsupport.service;

import techsupport.exception.CompetenciaInvalidaException;
import techsupport.exception.TecnicoIndisponivelException;
import techsupport.model.OrdemServico;
import techsupport.model.Tecnico;
import techsupport.repository.OrdemServicoRepository;
import techsupport.repository.TecnicoRepository;
import techsupport.strategy.EstrategiaFIFO;
import java.util.List;

public class SistemaTechSupport {
    private TecnicoRepository tecnicoRepository;
    private OrdemServicoRepository osRepository;
    private EscalonadorService escalonadorService;

    public SistemaTechSupport() {
        this.tecnicoRepository = new TecnicoRepository();
        this.osRepository = new OrdemServicoRepository();
        this.escalonadorService = new EscalonadorService(new EstrategiaFIFO());
    }

    public void cadastrarTecnico(Tecnico tecnico) {
        tecnicoRepository.adicionar(tecnico);
    }

    public void cadastrarOS(OrdemServico os) {
        osRepository.adicionar(os);
    }

    public void alocarOS() {
        List<OrdemServico> pendentes = osRepository.listarPendentes();
        if (pendentes.isEmpty()) {
            System.out.println("Nenhuma OS pendente.");
            return;
        }

        OrdemServico proximaOS = escalonadorService.escolherProximaOS(pendentes);
        List<Tecnico> disponiveis = tecnicoRepository.buscarDisponiveis();

        if (disponiveis.isEmpty()) {
            System.out.println("Nenhum técnico disponível.");
            return;
        }

        boolean alocada = false;
        for (Tecnico tecnico : disponiveis) {
            try {
                escalonadorService.alocarTecnico(proximaOS, tecnico);
                alocada = true;
                break;
            } catch (TecnicoIndisponivelException | CompetenciaInvalidaException e) {
                // Tenta o próximo técnico
            }
        }

        if (!alocada) {
            System.out.println("Não foi possível alocar a OS #" + proximaOS.getId() + " por falta de técnicos capacitados disponíveis.");
        }
    }

    public void listarDados() {
        System.out.println("\n=== TÉCNICOS ===");
        tecnicoRepository.listar().forEach(System.out::println);
        System.out.println("\n=== ORDENS DE SERVIÇO ===");
        osRepository.listar().forEach(System.out::println);
    }

    public TecnicoRepository getTecnicoRepository() {
        return tecnicoRepository;
    }

    public OrdemServicoRepository getOsRepository() {
        return osRepository;
    }

    public EscalonadorService getEscalonadorService() {
        return escalonadorService;
    }
}
