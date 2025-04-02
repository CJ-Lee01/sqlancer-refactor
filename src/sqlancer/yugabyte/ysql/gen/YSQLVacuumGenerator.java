package sqlancer.yugabyte.ysql.gen;

import sqlancer.base.common.query.ExpectedErrors;
import sqlancer.base.common.query.SQLQueryAdapter;
import sqlancer.yugabyte.ysql.YSQLGlobalState;

public final class YSQLVacuumGenerator {

    private YSQLVacuumGenerator() {
    }

    public static SQLQueryAdapter create(YSQLGlobalState globalState) {
        return new SQLQueryAdapter("VACUUM", ExpectedErrors.from("VACUUM cannot run inside a transaction block"));
    }

}
