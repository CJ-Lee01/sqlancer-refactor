package sqlancer.base.common.oracle;

import sqlancer.base.IgnoreMeException;
import sqlancer.base.Randomly;
import sqlancer.base.common.ast.newast.Expression;
import sqlancer.base.common.gen.PartitionGenerator;
import sqlancer.base.common.schema.AbstractSchema;
import sqlancer.base.common.schema.AbstractTable;
import sqlancer.base.common.schema.AbstractTableColumn;
import sqlancer.base.common.schema.AbstractTables;

public final class TestOracleUtils {

    private TestOracleUtils() {
    }

    public static final class PredicateVariants<E extends Expression<C>, C extends AbstractTableColumn<?, ?>> {
        public E predicate;
        public E negatedPredicate;
        public E isNullPredicate;

        PredicateVariants(E predicate, E negatedPredicate, E isNullPredicate) {
            this.predicate = predicate;
            this.negatedPredicate = negatedPredicate;
            this.isNullPredicate = isNullPredicate;
        }
    }

    public static <T extends AbstractTable<C, ?, ?>, C extends AbstractTableColumn<?, ?>> AbstractTables<T, C> getRandomTableNonEmptyTables(
            AbstractSchema<?, T> schema) {
        if (schema.getDatabaseTables().isEmpty()) {
            throw new IgnoreMeException();
        }
        return new AbstractTables<>(Randomly.nonEmptySubset(schema.getDatabaseTables()));
    }

    public static <E extends Expression<C>, T extends AbstractTable<C, ?, ?>, C extends AbstractTableColumn<?, ?>> PredicateVariants<E, C> initializeTernaryPredicateVariants(
            PartitionGenerator<E, C> gen, E predicate) {
        if (gen == null) {
            throw new IllegalStateException();
        }
        if (predicate == null) {
            throw new IllegalStateException();
        }
        E negatedPredicate = gen.negatePredicate(predicate);
        if (negatedPredicate == null) {
            throw new IllegalStateException();
        }
        E isNullPredicate = gen.isNull(predicate);
        if (isNullPredicate == null) {
            throw new IllegalStateException();
        }
        return new PredicateVariants<>(predicate, negatedPredicate, isNullPredicate);
    }
}
