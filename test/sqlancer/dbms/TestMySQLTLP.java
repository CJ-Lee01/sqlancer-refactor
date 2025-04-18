package sqlancer.dbms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

import sqlancer.base.Main;

public class TestMySQLTLP {

    @Test
    public void testMySQL() {
        assumeTrue(TestConfig.isEnvironmentTrue(TestConfig.MYSQL_ENV));
        assertEquals(0,
                Main.executeMain(new String[] { "--random-seed", "0", "--timeout-seconds", TestConfig.SECONDS,
                        "--max-expression-depth", "1", "--num-threads", "4", "--num-queries", TestConfig.NUM_QUERIES,
                        "mysql", "--oracle", "TLP_WHERE" }));
    }

}
