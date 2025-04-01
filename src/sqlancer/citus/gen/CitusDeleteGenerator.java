package sqlancer.citus.gen;

import sqlancer.base.common.query.ExpectedErrors;
import sqlancer.base.common.query.SQLQueryAdapter;
import sqlancer.postgres.PostgresGlobalState;
import sqlancer.postgres.gen.PostgresDeleteGenerator;

public final class CitusDeleteGenerator {

    private CitusDeleteGenerator() {
    }

    public static SQLQueryAdapter create(PostgresGlobalState globalState) {
        SQLQueryAdapter deleteQuery = PostgresDeleteGenerator.create(globalState);
        ExpectedErrors errors = deleteQuery.getExpectedErrors();
        CitusCommon.addCitusErrors(errors);
        return deleteQuery;
    }

}
