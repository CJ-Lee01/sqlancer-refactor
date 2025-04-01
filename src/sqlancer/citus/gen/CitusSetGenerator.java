package sqlancer.citus.gen;

import sqlancer.base.common.query.ExpectedErrors;
import sqlancer.base.common.query.SQLQueryAdapter;
import sqlancer.postgres.PostgresGlobalState;
import sqlancer.postgres.gen.PostgresSetGenerator;

public final class CitusSetGenerator {

    private CitusSetGenerator() {
    }

    public static SQLQueryAdapter create(PostgresGlobalState globalState) {
        SQLQueryAdapter setQuery = PostgresSetGenerator.create(globalState);
        ExpectedErrors errors = setQuery.getExpectedErrors();
        CitusCommon.addCitusErrors(errors);
        return setQuery;
    }

}
