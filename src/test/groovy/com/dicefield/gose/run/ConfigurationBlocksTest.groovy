package com.dicefield.gose.run

import org.testng.annotations.Test

import static com.dicefield.gose.run.Helpers.runBlock;

/**
 * Test running options in configuration blocks like:
 * <code>
 *  runConfig (showOutput: true) {
 *      run("echo", "abc")
 *      run("echo", "123")
 *  }
 * </code>
 *
 * What is an equivalent to:
 * <code>
 *  run("echo", "abc", showOutput: true)
 *  run("echo", "abc", showOutput: true)
 * </code>
 */
class ConfigurationBlocksTest {

    @Test
    void showOutput() {
        // TODO
        runBlock(showOutput: true) {
            run "echo 1; sleep 1; echo 2"
        }

        //assert false
    }

}
