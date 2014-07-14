package com.dicefield.gose.run

import com.dicefield.gose.run.exception.TimeoutException

/**
 * Running commands.
 */
class Runner {

    protected static final int SLEEP_TIME_MS = 100

    /**
     * Run a command. See possible arguments in {@link com.dicefield.gose.run.RunConfig}
     *
     * @param args
     * @param cmd
     * @return
     */
    public RunResult run(Map args, String ... cmd) {
        RunConfig cfg = RunConfig.getFromMap(args)

        if (cfg.logCommand()) {
            Date d = new Date()

            String msg = d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + " Executing: " + cmd[0]
            if (cmd.size() > 1) {
                msg += " '" + cmd[1..-1].join("' '") + "'"
            }

            cfg.getLogger().log(cfg.logLevel(), msg)
        }


        RunResult runResult = new RunResult()
        def process = cmd.execute()
        def out_reader = process.in.newReader()
        def err_reader = process.err.newReader()

        if (cfg.showOutput()) {
            cfg.getLogger().log(cfg.logLevel(), "Output:")
        }

        int timeout_counter = 0

        while ( true ) {
            String line = ""
            while ( out_reader.ready() ) {
                Character c = out_reader.read()
                runResult.appendStdOut(c)

                if (cfg.showOutput()) {
                    if (c == "\n") {
                        cfg.getLogger().log(cfg.logLevel(), "        " + line)
                        line = ""
                    } else {
                        line = line + c
                    }
                }
            }
            while ( err_reader.ready() ) {
                Character c = err_reader.read()
                runResult.appendStdErr(c)
            }

            try {
                runResult.setExitCode(process.exitValue())
                return runResult
            } catch ( IllegalThreadStateException ex ) {
                // TODO: this is ugly, isn't it?
            }

            Thread.sleep(SLEEP_TIME_MS)
            timeout_counter++

            if (cfg.getTimeout() != 0 && (timeout_counter*SLEEP_TIME_MS /1000) > cfg.getTimeout()) {
                throw new TimeoutException("Command execution timeout (" + (String)cfg.getTimeout() + " sec)")
            }
        }
    }

    public RunResult run(String ... cmd) {
        run([:], cmd)
    }

}
