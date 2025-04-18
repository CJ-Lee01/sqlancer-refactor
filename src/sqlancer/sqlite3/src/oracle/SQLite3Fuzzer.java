package sqlancer.sqlite3.oracle;

import sqlancer.base.Randomly;
import sqlancer.base.common.oracle.TestOracle;
import sqlancer.base.common.query.SQLQueryAdapter;
import sqlancer.sqlite3.SQLite3GlobalState;
import sqlancer.sqlite3.SQLite3Visitor;

// tries to trigger a crash
public class SQLite3Fuzzer implements TestOracle<SQLite3GlobalState> {

    private final SQLite3GlobalState globalState;

    public SQLite3Fuzzer(SQLite3GlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void check() throws Exception {
        String s = SQLite3Visitor
                .asString(SQLite3RandomQuerySynthesizer.generate(globalState, Randomly.smallNumber() + 1)) + ";";
        try {
            globalState.executeStatement(new SQLQueryAdapter(s));
            globalState.getManager().incrementSelectQueryCount();
        } catch (Error e) {

        }
    }

}
