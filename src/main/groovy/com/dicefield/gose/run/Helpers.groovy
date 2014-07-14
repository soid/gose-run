package com.dicefield.gose.run

/**
 * Various helper functions: static function that you can import in scripts
 * and easily run without creating extra objects.
 */
public class Helpers {

    public static void setRunConfig(Map args) {

    }

    // *** Running Commands *** //

    public static RunResult run(Map args, String ... cmd) {

    }

    public static RunResult run(String ... cmd) {
        run([:], cmd)
    }

    public static void runBlock(Map args, Closure block) {

    }

    public static void runBlock(Closure block) {
        runBlock([:], block)
    }

}
