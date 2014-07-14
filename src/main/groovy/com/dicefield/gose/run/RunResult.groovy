package com.dicefield.gose.run

/**
 * Results of running commands. You're supposed to get the output, exit code and other information like that
 * from the object.
 */
public class RunResult {

    protected int exitCode
    protected StringBuffer out
    protected StringBuffer err

    public RunResult() {
        out = new StringBuffer()
        err = new StringBuffer()
    }

    public void setExitCode(int value) {
        exitCode = value
    }

    public int getExitCode() {
        return exitCode
    }

    public String getOutput() {
        return out.toString()
    }

    public StringBuffer getStdOut() {
        return out
    }

    public void appendStdOut(Character c) {
        out.append(c)
    }

    public StringBuffer getStdErr() {
        return err
    }

    public void appendStdErr(Character c) {
        err.append(c)
    }

    public List<String> getOutputLines() {
        return out.toString().split("\n")
    }

}
