package techsupport.service;

import techsupport.enums.NivelTecnico;
import techsupport.enums.StatusOS;
import techsupport.exception.CompetenciaInvalidaException;
import techsupport.exception.TecnicoIndisponivelException;
import techsupport.model.OrdemServico;
import techsupport.model.Tecnico;
import techsupport.strategy.EstrategiaEscalonamento;
import java.util.List;

public class EscalonadorService {
    private EstrategiaEscalonamento estrategia;

    public EscalonadorService(EstrategiaEscalonamento estrategia) {
        this.estrategia = estrategia;
    }

    public void setEstrategia(EstrategiaEscalonamento estrategia) {
        this.estrategia = estrategia;
    }

    public OrdemServico escolherProximaOS(List<OrdemServico> pendentes) {
        return estrategia.escolherProxima(pendentes);
    }

    public void alocarTecnico(OrdemServico os, Tecnico tecnico) throws TecnicoIndisponivelException, CompetenciaInvalidaException {
        if (!tecnico.estaDisponivel()) {
            throw new TecnicoIndisponivelException("Técnico " + tecnico.getNome() + " não está disponível.");
        }

        if (!validarCompetencia(tecnico, os)) {
            throw new CompetenciaInvalidaException("Técnico " + tecnico.getNome() + " (" + tecnico.getNivel() + 
                ") não tem competência para a OS de complexidade " + os.getComplexidade());
        }

        tecnico.ocupar();
        os.setStatus(StatusOS.EM_ATENDIMENTO);
        System.out.println("OS #" + os.getId() + " alocada para técnico " + tecnico.getNome());
    }

    private boolean validarCompetencia(Tecnico tecnico, OrdemServico os) {
        int nivelTecnico = tecnico.getNivel().ordinal();
        // complexidade da OS deve ser compatível com o nível do técnico
        // 0: BAIXA/JUNIOR, 1: MEDIA/PLENO, 2: ALTA/SENIOR (exemplo de lógica)
        // Vamos supor complexidade de 1 a 3
        return (nivelTecnico + 1) >= os.getComplexidade();
    }
}
