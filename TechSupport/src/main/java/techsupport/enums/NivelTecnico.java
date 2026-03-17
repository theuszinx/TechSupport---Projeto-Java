package techsupport.enums;

import java.util.Random;

// Competência
public enum NivelTecnico {
    JUNIOR, PLENO, SENIOR;

    private static final Random RANDOM = new Random();

    public static NivelTecnico nivelRandom(){
        NivelTecnico[] niveis = values();

        return niveis[RANDOM.nextInt(niveis.length)];
    }

}