package sqlancer.datafusion.ast;

import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewUnaryPrefixOperatorNode;

public class DataFusionUnaryPrefixOperation extends NewUnaryPrefixOperatorNode<DataFusionExpression>
        implements DataFusionExpression {
    public DataFusionUnaryPrefixOperation(DataFusionExpression expr, BinaryOperatorNode.Operator operator) {
        super(expr, operator);
    }
}
