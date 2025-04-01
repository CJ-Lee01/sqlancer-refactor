package sqlancer.datafusion.ast;

import java.util.List;

import sqlancer.base.common.ast.newast.NewFunctionNode;

public class DataFusionFunction<F> extends NewFunctionNode<DataFusionExpression, F> implements DataFusionExpression {
    public DataFusionFunction(List<DataFusionExpression> args, F func) {
        super(args, func);
    }
}
