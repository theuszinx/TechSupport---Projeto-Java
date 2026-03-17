package techsupport.strategy;

public class EstrategiaFIFO extends EstrategiaEscalonamento {
    public EstrategiaFIFO(){
        /*
        Nenhum comparator passado como argumento, assim
        será criada uma Queue ao invés de uma PriorityQueue
        Uma Queue já é FIFO por padrão
         */
        super();
    }
}
