package sqlancer.presto.ast;

import sqlancer.base.common.ast.newast.NewAliasNode;

public class PrestoAlias extends NewAliasNode<PrestoExpression> implements PrestoExpression {
    public PrestoAlias(PrestoExpression expr, String alias) {
        super(expr, alias);
    }
}
