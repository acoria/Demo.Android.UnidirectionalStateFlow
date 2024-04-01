package com.example.unidirectionalstateflow;

public final class LCE {
    final boolean loading;
    final boolean success;
    final String errorMessage;

    public LCE(boolean loading, boolean success, String errorMessage) {
        this.loading = loading;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    static LCE idle(){
        return new LCE(false, false, null);
    }
    static LCE loading(){
        return new LCE(true, false, null);
    }
    static LCE success(){
        return new LCE(false, true, null);
    }
    static LCE failure(String errorMessage){
        return new LCE(false, false, errorMessage);
    }
}
