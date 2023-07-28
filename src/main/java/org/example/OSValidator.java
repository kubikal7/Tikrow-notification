package org.example;

public class OSValidator {
    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static String selectDriver() {

        System.out.println(OS);

        if (OS.contains("win")) {
            return "src/main/resources/chromedriver.exe";
        } else if (OS.contains("mac")) {
            return "src/main/resources/chromedriver.exe";
        } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
            return "src/main/resources/chromedriver";
        }
        return "error";
    }
}
