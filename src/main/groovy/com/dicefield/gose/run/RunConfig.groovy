package com.dicefield.gose.run

import java.util.logging.Level
import java.util.logging.Logger

/**
 * Hold running options in this object. This object will:
 *   - Verify the options;
 *   - Set defaults;
 *   - Provide the options for the runners.
 */
class RunConfig {

    protected static Map<String, List> options = [
            // format:
            // optionName: [default value, possible classes]
            showOutput: [false, Boolean.class],
            logCommand: [true, Boolean.class],
            timeout: [0, Number.class],
            logger: [Logger.getLogger("com.dicefield.gose.run"), Logger.class],
            logLevel: [Level.INFO, Level.class]
    ]

    // current settings in the object
    protected Map<String, Object> values = [:]

    /**
     * Pass Map with given options and get a RunConfig object and the options verified.
     *
     * @param args
     * @return
     */
    public static RunConfig getFromMap(Map args) {
        RunConfig res = new RunConfig()
        for (String optionName : args.keySet()) {

            if ( ! options.containsKey(optionName)) {
                throw new IllegalArgumentException("Unknown option: " + optionName)
            }

            Object value = args.get(optionName)

            // verify class
            List<Class> possibleClasses = options.get(optionName)[1..-1]
            Boolean verified = false
            for (Class clz : possibleClasses) {
                if (clz.isInstance(value)) {
                    verified = true
                    break
                }
            }

            if (verified == false) {
                throw new IllegalArgumentException("Invalid type for option '$optionName'."
                        + "\nExpected: " + possibleClasses.join(", ")
                        + "\nActual: " + value.getClass())
            }

            // alrighty, set the option
            res.values[optionName] = value
        }

        // set default options
        for (String optionName : options.keySet()) {
            if ( ! res.values.containsKey(optionName)) {
                res.values[optionName] = options.get(optionName).get(0)
            }
        }

        return res
    }

    public Object getOption(String name) {
        return values.get(name)
    }

    public int getTimeout() {
        // TODO: use constants
        return (int) getOption("timeout")
    }

    public Boolean showOutput() {
        return (Boolean) getOption("showOutput")
    }

    public Boolean logCommand() {
        return (Boolean) getOption("logCommand")
    }

    public Logger getLogger() {
        return getOption("logger")
    }

    public Level logLevel() {
        return getOption("logLevel")
    }

}
