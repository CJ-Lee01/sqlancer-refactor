package sqlancer.base.common.visitor;

public interface TernaryOperation<T> {

    T getLeft();

    T getMiddle();

    T getRight();

}
