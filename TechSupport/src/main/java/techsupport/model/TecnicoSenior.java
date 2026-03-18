package techsupport.model;

import techsupport.enums.Complexidade;
import techsupport.enums.NivelTecnico;

/**
 * Técnico Sênior: atende qualquer complexidade de OS.
 */
public class TecnicoSenior extends Tecnico {

    public TecnicoSenior(String nome) {
        super(nome, NivelTecnico.SENIOR);
    }

    @Override
    public boolean podeAtender(Complexidade complexidade) {
        return true; // Sênior atende tudo
    }
}
