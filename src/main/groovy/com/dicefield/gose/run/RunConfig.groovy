package com.dicefield.gose.run

import java.util.logging.Level
import java.util.logging.Logger

/**
 * Hold running options in this object. This object will:
 *   - Verify the options;
 *   - Set defaults;
 *   - Provide the options for the runners.
 */
class RunConfig implements Cloneable {

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
    static public RunConfig getFromMap(Map args) {
        RunConfig res = new RunConfig()
        for (String optionName : args.keySet()) {

            if ( ! options.containsKey(optionName)) {
                throw new IllegalArgumentException("Unknown option: " + optionName)
            }

            Object value = args.get(optionName)

            res.verifyOption(optionName, value)

            // alrighty, set the option
            res.values[optionName] = value
        }

        return res
    }

    /**
     * Merge two RunConfigs. One with higher priority, one with the lower priority.
     *
     * @param higherPriorityConfig
     * @param lowerPriorityConfig
     * @return
     */
    static public RunConfig merge(RunConfig higherPriorityConfig, RunConfig lowerPriorityConfig) {
        RunConfig resRunConfig = lowerPriorityConfig.clone();
        for (String optionName : higherPriorityConfig.values.keySet()) {
            // overwrite
            resRunConfig.values[optionName] = higherPriorityConfig.values[optionName]
        }

        return resRunConfig;
    }

    public Object getOption(String name) {
        if (values.containsKey(name)) {
            return values.get(name)
        } else {
            // use the default
            options.get(name).get(0)
        }
    }

    public void setOption(String name, Object value) {
        // TODO : verify name and type
        if ( ! options.containsKey(name)) {
            throw new IllegalArgumentException("Option '$name' is not available.")
        }
        verifyOption(name, value)
        values[name] = value;
    }

    // getters / setters

    public int getTimeout() {
        // TODO: use constants
        return (int) getOption("timeout")
    }

    public void setTimeout(int value) {
        setOption("timeout", value)
    }

    public Boolean showOutput() {
        return (Boolean) getOption("showOutput")
    }

    public void showOutput(Boolean value) {
        setOption("showOutput", value)
    }

    public Boolean logCommand() {
        return (Boolean) getOption("logCommand")
    }

    public void logCommand(Boolean value) {
        setOption("logCommand", value)
    }

    public Logger getLogger() {
        return getOption("logger")
    }

    public void setLogger(Logger value) {
        setOption("logger", value)
    }

    public Level logLevel() {
        return getOption("logLevel")
    }

    public void logLevel(Level value) {
        setOption("logLevel", value)
    }

    // private / protected

    void verifyOption(String optionName, Object value) {
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
    }

}
