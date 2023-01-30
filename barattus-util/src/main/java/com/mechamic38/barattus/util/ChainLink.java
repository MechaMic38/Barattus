package com.mechamic38.barattus.util;

/**
 * Fundamental object to create Chain of Responsibilities.
 */
abstract public class ChainLink {

    protected ChainLink next;

    public static ChainLink link(ChainLink first, ChainLink... chains) {
        ChainLink head = first;
        for (ChainLink nextInChain : chains) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }
}
