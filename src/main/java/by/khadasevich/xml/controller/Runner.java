package by.khadasevich.xml.controller;

/**
 * Run program execution.
 */
public final class Runner {

    private Runner() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * Program runner.
     * @param args arguments from command line.
     */
    public static void main(final String[] args) {
        // execute commands
        Controller controller = new Controller();
        controller.executeCommand();
    }
}
