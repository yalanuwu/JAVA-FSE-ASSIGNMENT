
public class LoggerSingletonTest {
    
    public static class Logger {
        
        private static Logger instance;

        
        private Logger() {
            // Private constructor to prevent instantiation
        }

        // Public static method to get the instance of the Logger class
        public static Logger getInstance() {
            if (instance == null) {
                synchronized (Logger.class) {
                    if (instance == null) {
                        instance = new Logger();
                    }
                }
            }
            return instance;
        }

        // Method to log a message
        public void log(String message) {
            System.out.println("Log: " + message);
        }
    }

    
    public static void main(String[] args) {
        
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        // Logging messages
        logger1.log("This is the first log message.");
        logger2.log("This is the second log message.");

        // Verifying that only one instance of Logger is created
        if (logger1 == logger2) {
            System.out.println("Both logger1 and logger2 are the same instance.");
        } else {
            System.out.println("logger1 and logger2 are different instances.");
        }
    }
}

