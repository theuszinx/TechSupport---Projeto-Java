package techsupport.service;

import techsupport.enums.StatusOS;
import techsupport.exception.CompetenciaInvalidaException;
import techsupport.model.OrdemServico;
import techsupport.model.Tecnico;
import techsupport.repository.OrdemServicoRepository;
import techsupport.repository.TecnicoRepository;

import java.util.Comparator;
import java.util.List;

public class EscalonadorService {
    private final TecnicoRepository tecnicoRepo;
    private final OrdemServicoRepository osRepo;

    public EscalonadorService(TecnicoRepository tecnicoRepo, OrdemServicoRepository osRepo) {
        this.tecnicoRepo = tecnicoRepo;
        this.osRepo = osRepo;
    }

    /**
     * Tenta alocar a próxima OS da fila para um técnico disponível e competente,
     * usando a estratégia de escalonamento escolhida pelo usuário.
     *
     * @param estrategia Comparator que define a ordem de prioridade da fila
     */
    public void alocarProximaOS(Comparator<OrdemServico> estrategia) {

        // 1. Verifica se há OS na fila
        if (!osRepo.possuiOrdens()) {
            System.out.println("\n[ESCALONADOR] Não há ordens de serviço pendentes na fila.");
            return;
        }

        // 2. Seleciona a próxima OS com base na estratégia (sem remover ainda)
        OrdemServico os = osRepo.peekProxima(estrategia);
        System.out.println("\n[ESCALONADOR] OS selecionada: " + os);

        // 3. Busca TODOS os técnicos disponíveis
        List<Tecnico> tecnicos = tecnicoRepo.buscarDisponiveis();

        if (tecnicos.isEmpty()) {
            System.out.println("[ESCALONADOR] Nenhum técnico disponível no momento. A OS permanece na fila.");
            return;
        }

        System.out.println("[ESCALONADOR] Técnicos disponíveis encontrados: " + tecnicos.size());

        // 4. Itera por todos os técnicos até encontrar um com competência
        for (Tecnico tecnico : tecnicos) {
            System.out.println("[TENTATIVA] Verificando técnico: " + tecnico);

            try {
                validarCompetencia(tecnico, os);

                // Competência OK — realiza a alocação
                tecnico.ocupar();
                os.setStatus(StatusOS.EM_ATENDIMENTO);
                osRepo.remover(os); // Remove da fila somente após confirmar a alocação

                System.out.println("[SUCESSO] OS #" + os.getId() + " alocada para " + tecnico.getNome()
                        + " [" + tecnico.getNivel() + "]");
                return; // Alocação concluída, encerra

            } catch (CompetenciaInvalidaException e) {
                System.out.println("[INCOMPATÍVEL] " + tecnico.getNome()
                        + " [" + tecnico.getNivel() + "] não possui competência: " + e.getMessage());
                // Continua para o próximo técnico
            }
        }

        // 5. Nenhum técnico foi capaz de atender a OS
        System.out.println("[ESCALONADOR] Nenhum técnico disponível possui competência para a OS #"
                + os.getId() + ". A OS permanece na fila.");
    }

    /**
     * Valida se o técnico pode atender a OS com base em sua competência.
     * cada subclasse de Tecnico define suas regras via podeAtender().
     */
    private void validarCompetencia(Tecnico tecnico, OrdemServico os) {
        if (!tecnico.podeAtender(os.getComplexidade())) {
            throw new CompetenciaInvalidaException(
                tecnico.getNivel() + " não pode atender OS de complexidade " + os.getComplexidade()
            );
        }
    }
}

