package com.mechamic38.barattus.core.user.validation;

abstract public class LoginChainLink {

    protected LoginChainLink next;

    public static LoginChainLink link(LoginChainLink first, LoginChainLink... chains) {
        LoginChainLink head = first;
        for (LoginChainLink nextInChain : chains) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract String check(String username, String password);

    public String checkNext(String username, String password) {
        if (next == null) {
            return "";
        }
        return next.check(username, password);
    }
}
