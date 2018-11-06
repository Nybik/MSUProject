package com;

public class Debugger {
    static private int DEBUG_LEVEL = 100;

    public static  void setDebugLevel(int NEW_DEBUG_LEVEL) {
        DEBUG_LEVEL = NEW_DEBUG_LEVEL;
    }

    static void tryToDebug(String message, int need_level) {
        if (DEBUG_LEVEL >= need_level) {
            System.out.println(message);
        }
    }
}
