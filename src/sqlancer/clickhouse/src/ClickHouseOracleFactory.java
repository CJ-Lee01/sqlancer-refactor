package src;

import java.sql.SQLException;

import sqlancer.base.OracleFactory;
import src.ClickHouseProvider.ClickHouseGlobalState;
import src.gen.ClickHouseExpressionGenerator;
import src.tlp.ClickHouseTLPAggregateOracle;
import src.tlp.ClickHouseTLPDistinctOracle;
import src.tlp.ClickHouseTLPGroupByOracle;
import src.tlp.ClickHouseTLPHavingOracle;
import sqlancer.base.common.oracle.NoRECOracle;
import sqlancer.base.common.oracle.TLPWhereOracle;
import sqlancer.base.common.oracle.TestOracle;
import sqlancer.base.common.query.ExpectedErrors;

public enum ClickHouseOracleFactory implements OracleFactory<ClickHouseGlobalState> {
    TLPWhere {
        @Override
        public TestOracle<ClickHouseGlobalState> create(ClickHouseGlobalState globalState) throws SQLException {
            ClickHouseExpressionGenerator gen = new ClickHouseExpressionGenerator(globalState);
            ExpectedErrors expectedErrors = ExpectedErrors.newErrors()
                    .with(ClickHouseErrors.getExpectedExpressionErrors()).build();

            return new TLPWhereOracle<>(globalState, gen, expectedErrors);
        }
    },
    TLPDistinct {
        @Override
        public TestOracle<ClickHouseGlobalState> create(ClickHouseGlobalState globalState) throws SQLException {
            return new ClickHouseTLPDistinctOracle(globalState);
        }
    },
    TLPGroupBy {
        @Override
        public TestOracle<ClickHouseGlobalState> create(ClickHouseGlobalState globalState) throws SQLException {
            return new ClickHouseTLPGroupByOracle(globalState);
        }
    },
    TLPAggregate {
        @Override
        public TestOracle<ClickHouseGlobalState> create(ClickHouseGlobalState globalState) throws SQLException {
            return new ClickHouseTLPAggregateOracle(globalState);
        }
    },
    TLPHaving {
        @Override
        public TestOracle<ClickHouseGlobalState> create(ClickHouseGlobalState globalState) throws SQLException {
            return new ClickHouseTLPHavingOracle(globalState);
        }
    },
    NoREC {
        @Override
        public TestOracle<ClickHouseGlobalState> create(ClickHouseGlobalState globalState) throws SQLException {
            ClickHouseExpressionGenerator gen = new ClickHouseExpressionGenerator(globalState);
            ExpectedErrors errors = ExpectedErrors.newErrors().with(ClickHouseErrors.getExpectedExpressionErrors())
                    .with("canceling statement due to statement timeout").build();

            return new NoRECOracle<>(globalState, gen, errors);
        }
    }
}
