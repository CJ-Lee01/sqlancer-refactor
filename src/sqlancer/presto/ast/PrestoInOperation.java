package sqlancer.presto.ast;

import java.util.List;

import sqlancer.base.common.ast.newast.NewInOperatorNode;

public class PrestoInOperation extends NewInOperatorNode<PrestoExpression> implements PrestoExpression {
    public PrestoInOperation(PrestoExpression left, List<PrestoExpression> right, boolean isNegated) {
        super(left, right, isNegated);
    }
}
