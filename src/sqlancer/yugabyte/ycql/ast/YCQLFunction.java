package sqlancer.yugabyte.ycql.ast;

import java.util.List;

import sqlancer.base.common.ast.newast.NewFunctionNode;

public class YCQLFunction<F> extends NewFunctionNode<YCQLExpression, F> implements YCQLExpression {
    public YCQLFunction(List<YCQLExpression> args, F func) {
        super(args, func);
    }
}
