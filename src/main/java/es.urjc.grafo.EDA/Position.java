package es.urjc.grafo.EDA;

public interface Position<E> {
    E getElement() throws IllegalStateException;
}
