package sqlancer.dbms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;

import sqlancer.base.Main;
import sqlancer.base.Randomly;

public class TestDatabendNoREC {

    @Test
    public void testDatabendNoREC() {
        assumeTrue(TestConfig.isEnvironmentTrue(TestConfig.DATABEND_ENV));
        assertEquals(0,
                Main.executeMain("--random-seed", "0", "--timeout-seconds", TestConfig.SECONDS, "--num-threads", "4",
                        "--num-queries", TestConfig.NUM_QUERIES, "--database-prefix", "databend",
                        "--random-string-generation", String.valueOf(Randomly.StringGenerationStrategy.ALPHANUMERIC),
                        "--host", "127.0.0.1", "--port", "3307", "databend", "--oracle", "NOREC"));
    }

}
