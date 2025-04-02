package sqlancer.databend.ast;

import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewBinaryOperatorNode;

public class DatabendBinaryOperation extends NewBinaryOperatorNode<DatabendExpression> implements DatabendExpression {
    public DatabendBinaryOperation(DatabendExpression left, DatabendExpression right,
            BinaryOperatorNode.Operator operator) {
        super(left, right, operator);
    }

}
