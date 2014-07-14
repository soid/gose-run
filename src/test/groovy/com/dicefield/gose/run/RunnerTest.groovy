package com.dicefield.gose.run

import com.dicefield.gose.run.exception.TimeoutException
import org.testng.annotations.Test

/**
 * This test is intended for testing different running options in {@link com.dicefield.gose.run.Runner}
 */
class RunnerTest {

    @Test
    public void run_no_options() {
        Runner r = new Runner()
        RunResult res = r.run("ls", "-la", "/")

        assert res.getExitCode() == 0
    }

    @Test
    public void run_showOutput() {
        Runner r = new Runner()
        String text = "test output"
        RunResult res = r.run("echo", text, showOutput: true)

        assert res.getExitCode() == 0
        println text.length()
        println res.getOutput().length()
        assert res.getOutput().contains(text)
    }

    @Test
    public void run_stdErr() {
        Runner r = new Runner()
        RunResult res = r.run("bash", "-c", "echo 123 1>&2", showOutput: true)

        assert res.getExitCode() == 0
    }

    @Test
    public void run_timeout() {
        Runner r = new Runner()

        Boolean caught = false
        try {
            RunResult res = r.run("sleep", "2", timeout: 1)
        } catch (TimeoutException e) {
            caught = true
        }

        assert caught, "Timeout exception is not caught"
    }

    @Test
    public void run_timeout_false() {
        Runner r = new Runner()

        Boolean caught = false
        try {
            RunResult res = r.run("sleep", "2", timeout: 3)
        } catch (TimeoutException e) {
            caught = true
        }

        assert caught==false, "Timeout exception is caught though it shouldn't have caught"
    }

    @Test
    public void run_setRunConfig_localOption() {
        RunConfig runConfig = new RunConfig()
        runConfig.setTimeout(1)

        // timeout should be overwritten by the local option
        Runner r = new Runner()
        r.run("sleep", "2", timeout: 3)
    }

    @Test
    public void run_setRunConfig_objectOption() {
        RunConfig runConfig = new RunConfig()
        runConfig.setTimeout(1)

        // timeout should be overwritten by the local option
        Runner r = new Runner()
        Boolean caught = false
        try {
            RunResult res = r.run("sleep", "2", timeout: 1)
        } catch (TimeoutException e) {
            caught = true
        }

        assert caught, "Timeout exception is not caught"
    }

}
