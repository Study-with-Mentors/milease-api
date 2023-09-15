package vn.id.milease.mileaseapi.model.exception;

public enum ActionConflict {
    CREATE("creating"),
    READ("reading"),
    UPDATE("updating"),
    DELETE("deleting");

    private String name;
    ActionConflict(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
