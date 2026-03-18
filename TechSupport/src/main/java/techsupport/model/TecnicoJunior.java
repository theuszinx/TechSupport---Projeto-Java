package techsupport.model;

import techsupport.enums.Complexidade;
import techsupport.enums.NivelTecnico;

/**
 * Técnico Júnior: só atende OS de complexidade BAIXA.
 */
public class TecnicoJunior extends Tecnico {

    public TecnicoJunior(String nome) {
        super(nome, NivelTecnico.JUNIOR);
    }

    @Override
    public boolean podeAtender(Complexidade complexidade) {
        return complexidade == Complexidade.BAIXA;
    }
}
