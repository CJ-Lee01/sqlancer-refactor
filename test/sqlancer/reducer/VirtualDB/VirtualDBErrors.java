package sqlancer.reducer.VirtualDB;

import sqlancer.base.common.query.ExpectedErrors;

public final class VirtualDBErrors {
    public VirtualDBErrors() {
    }

    public static void addErrors(ExpectedErrors errors) {
        errors.add("Default error");
    }
}
