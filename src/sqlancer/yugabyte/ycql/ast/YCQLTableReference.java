package sqlancer.yugabyte.ycql.ast;

import sqlancer.base.common.ast.newast.TableReferenceNode;
import sqlancer.yugabyte.ycql.YCQLSchema;

public class YCQLTableReference extends TableReferenceNode<YCQLExpression, YCQLSchema.YCQLTable>
        implements YCQLExpression {
    public YCQLTableReference(YCQLSchema.YCQLTable table) {
        super(table);
    }
}
