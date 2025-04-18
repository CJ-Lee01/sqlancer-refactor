package sqlancer.presto.ast;

import sqlancer.base.Randomly;
import sqlancer.base.common.ast.BinaryOperatorNode;
import sqlancer.base.common.ast.newast.NewUnaryPrefixOperatorNode;
import sqlancer.presto.PrestoSchema;

public class PrestoUnaryPrefixOperation extends NewUnaryPrefixOperatorNode<PrestoExpression>
        implements PrestoExpression {

    public PrestoUnaryPrefixOperation(PrestoExpression expression, BinaryOperatorNode.Operator operation) {
        super(expression, operation);
    }

    public enum PrestoUnaryPrefixOperator implements BinaryOperatorNode.Operator {
        NOT("NOT", PrestoSchema.PrestoDataType.BOOLEAN) {
            @Override
            public PrestoSchema.PrestoDataType getExpressionType() {
                return PrestoSchema.PrestoDataType.BOOLEAN;
            }
        },

        UNARY_PLUS("+", PrestoSchema.PrestoDataType.INT, PrestoSchema.PrestoDataType.FLOAT,
                PrestoSchema.PrestoDataType.DECIMAL) {
            @Override
            public PrestoSchema.PrestoDataType getExpressionType() {
                return PrestoSchema.PrestoDataType.INT;
            }
        },
        UNARY_MINUS("-", PrestoSchema.PrestoDataType.INT, PrestoSchema.PrestoDataType.FLOAT,
                PrestoSchema.PrestoDataType.DECIMAL) {
            @Override
            public PrestoSchema.PrestoDataType getExpressionType() {
                return PrestoSchema.PrestoDataType.INT;
            }
        };

        private final String textRepresentation;
        private final PrestoSchema.PrestoDataType[] dataTypes;

        PrestoUnaryPrefixOperator(String textRepresentation, PrestoSchema.PrestoDataType... dataTypes) {
            this.textRepresentation = textRepresentation;
            this.dataTypes = dataTypes.clone();
        }

        public PrestoSchema.PrestoDataType getRandomInputDataTypes() {
            return Randomly.fromOptions(dataTypes);
        }

        public abstract PrestoSchema.PrestoDataType getExpressionType();

        @Override
        public String getTextRepresentation() {
            return this.textRepresentation;
        }

        public PrestoSchema.PrestoDataType getExpressionType(PrestoSchema.PrestoDataType type) {
            return type;
        }
    }

}
