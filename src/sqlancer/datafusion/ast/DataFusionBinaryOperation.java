package sqlancer.datafusion.ast;

import sqlancer.base.common.ast.BinaryOperatorNode.Operator;
import sqlancer.base.common.ast.newast.NewBinaryOperatorNode;

public class DataFusionBinaryOperation extends NewBinaryOperatorNode<DataFusionExpression>
        implements DataFusionExpression {
    public DataFusionBinaryOperation(DataFusionExpression left, DataFusionExpression right, Operator op) {
        super(left, right, op);
    }
}
