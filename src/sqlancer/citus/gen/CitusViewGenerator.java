package sqlancer.citus.gen;

import sqlancer.base.common.query.ExpectedErrors;
import sqlancer.base.common.query.SQLQueryAdapter;
import sqlancer.postgres.PostgresGlobalState;
import sqlancer.postgres.gen.PostgresViewGenerator;

public final class CitusViewGenerator {

    private CitusViewGenerator() {
    }

    public static SQLQueryAdapter create(PostgresGlobalState globalState) {
        SQLQueryAdapter viewQuery = PostgresViewGenerator.create(globalState);
        ExpectedErrors errors = viewQuery.getExpectedErrors();
        CitusCommon.addCitusErrors(errors);
        return viewQuery;
    }

}
