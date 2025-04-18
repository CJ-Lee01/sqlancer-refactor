package sqlancer.cnosdb.oracle;

import sqlancer.base.Main;
import sqlancer.base.MainOptions;
import sqlancer.cnosdb.CnosDBGlobalState;
import sqlancer.cnosdb.client.CnosDBConnection;
import sqlancer.base.common.oracle.TestOracle;

public abstract class CnosDBNoRECBase implements TestOracle<CnosDBGlobalState> {
    protected final CnosDBGlobalState state;
    protected final Main.StateLogger logger;
    protected final MainOptions options;
    protected final CnosDBConnection con;
    protected String optimizedQueryString;
    protected String unoptimizedQueryString;

    public CnosDBNoRECBase(CnosDBGlobalState state) {
        this.state = state;
        this.con = state.getConnection();
        this.logger = state.getLogger();
        this.options = state.getOptions();
    }
}
