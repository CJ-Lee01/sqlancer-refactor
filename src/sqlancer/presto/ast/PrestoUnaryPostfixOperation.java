package sqlancer.presto.ast;

import sqlancer.base.Randomly;
import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewUnaryPostfixOperatorNode;
import sqlancer.presto.PrestoSchema;

public class PrestoUnaryPostfixOperation extends NewUnaryPostfixOperatorNode<PrestoExpression>
        implements PrestoExpression {

    public PrestoUnaryPostfixOperation(PrestoExpression expr, PrestoUnaryPostfixOperator op) {
        super(expr, op);
    }

    public PrestoExpression getExpression() {
        return getExpr();
    }

    public enum PrestoUnaryPostfixOperator implements BinaryOperatorNode.Operator {
        IS_NULL("IS NULL") {
            @Override
            public PrestoSchema.PrestoDataType[] getInputDataTypes() {
                return PrestoSchema.PrestoDataType.values();
            }
        },
        IS_NOT_NULL("IS NOT NULL") {
            @Override
            public PrestoSchema.PrestoDataType[] getInputDataTypes() {
                return PrestoSchema.PrestoDataType.values();
            }
        };

        private final String textRepresentations;

        PrestoUnaryPostfixOperator(String text) {
            this.textRepresentations = text;
        }

        public static PrestoUnaryPostfixOperator getRandom() {
            return Randomly.fromOptions(values());
        }

        @Override
        public String getTextRepresentation() {
            return textRepresentations;
        }

        public abstract PrestoSchema.PrestoDataType[] getInputDataTypes();

    }

}
