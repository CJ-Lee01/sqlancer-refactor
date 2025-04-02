package sqlancer.base.common.oracle;

import sqlancer.base.Main.StateLogger;
import sqlancer.base.MainOptions;
import sqlancer.base.SQLConnection;
import sqlancer.base.SQLGlobalState;
import sqlancer.base.common.query.ExpectedErrors;

public abstract class NoRECBase<S extends SQLGlobalState<?, ?>> implements TestOracle<S> {

    protected final S state;
    protected final ExpectedErrors errors = new ExpectedErrors();
    protected final StateLogger logger;
    protected final MainOptions options;
    protected final SQLConnection con;
    protected String optimizedQueryString;
    protected String unoptimizedQueryString;

    protected NoRECBase(S state) {
        this.state = state;
        this.con = state.getConnection();
        this.logger = state.getLogger();
        this.options = state.getOptions();
    }

}
