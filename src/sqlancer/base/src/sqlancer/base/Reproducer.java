package sqlancer.base;

public interface Reproducer<G extends GlobalState<?, ?, ?>> {
    boolean bugStillTriggers(G globalState);
}
