package techsupport.strategy;

import techsupport.model.OrdemServico;

import java.util.Comparator;

public class EstrategiaComplexidade extends EstrategiaEscalonamento {
    public static Comparator<OrdemServico> comparator = (ordem1, ordem2) -> {
        return Integer.compare(
                ordem1.getComplexidade().getValorComplexidade(),
                ordem2.getComplexidade().getValorComplexidade()
        );
    };

    public EstrategiaComplexidade(){
        super(comparator);
    }
}
