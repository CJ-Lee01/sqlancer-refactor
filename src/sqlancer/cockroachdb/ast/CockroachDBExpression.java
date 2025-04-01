package sqlancer.cockroachdb.ast;

import sqlancer.cockroachdb.CockroachDBSchema.CockroachDBColumn;
import sqlancer.base.common.ast.newast.Expression;

public interface CockroachDBExpression extends Expression<CockroachDBColumn> {

}
