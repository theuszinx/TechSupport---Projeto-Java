package techsupport.model;

import techsupport.enums.Complexidade;
import techsupport.enums.NivelTecnico;

/**
 * Técnico Pleno: atende OS de complexidade BAIXA, MEDIA e ALTA.
 */
public class TecnicoPleno extends Tecnico {

    public TecnicoPleno(String nome) {
        super(nome, NivelTecnico.PLENO);
    }

    @Override
    public boolean podeAtender(Complexidade complexidade) {
        return true; // Atende qualquer complexidade existente
    }
}
