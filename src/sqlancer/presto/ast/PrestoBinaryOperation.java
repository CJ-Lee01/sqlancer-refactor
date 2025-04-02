package sqlancer.presto.ast;

import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewBinaryOperatorNode;

public class PrestoBinaryOperation extends NewBinaryOperatorNode<PrestoExpression> implements PrestoExpression {
    public PrestoBinaryOperation(PrestoExpression left, PrestoExpression right, BinaryOperatorNode.Operator op) {
        super(left, right, op);
    }
}
