package sqlancer.doris.ast;

import sqlancer.base.common.ast.newast.NewPostfixTextNode;

public class DorisPostfixText extends NewPostfixTextNode<DorisExpression> implements DorisExpression {
    public DorisPostfixText(DorisExpression expr, String text) {
        super(expr, text);
    }
}
