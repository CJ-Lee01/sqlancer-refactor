package sqlancer.yugabyte.ycql.ast;

import java.util.List;

import sqlancer.base.common.ast.newast.NewInOperatorNode;

public class YCQLInOperation extends NewInOperatorNode<YCQLExpression> implements YCQLExpression {
    public YCQLInOperation(YCQLExpression left, List<YCQLExpression> right, boolean isNegated) {
        super(left, right, isNegated);
    }
}
