package com.tradeshift.tasks;

public enum TaskStatus {
    COMPLETE,
    INPROGRESS,
    NEW;

    public static boolean contains(String v) {
        for (TaskStatus s : TaskStatus.values()) {
            if (s.name().equals(v))
                return true;
        }
        return false;
    }
}
