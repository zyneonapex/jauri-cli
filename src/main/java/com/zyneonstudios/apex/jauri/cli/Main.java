package com.zyneonstudios.apex.jauri.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

@Command(name = "jauri", mixinStandardHelpOptions = true, version = "1.0-SNAPSHOT", description = "Jauri build cli",
    subcommands = {
        Main.DevCommand.class,
        Main.BuildCommand.class
    }
)
public class Main implements Callable<Integer> {

    @Override
    public Integer call() {
        CommandLine.usage(this, System.out);
        return 0;
    }

    static void main(String[] args) {
        fixEncoding();

        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    private static void fixEncoding() {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            try {
                new ProcessBuilder("cmd", "/c", "chcp 65001").inheritIO().start().waitFor();
                System.out.println(" ");
            } catch (Exception ignored) {}
        }
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
        System.setProperty("picocli.ansi", "FORCE");
    }


    @Command(name = "dev", description = "Starts the dev mode.")
    public static class DevCommand implements Callable<Integer> {

        @Option(names = {"-p", "--port"}, description = "Port of the frontend-dev server", defaultValue = "5173")
        private int port;

        @Override
        public Integer call() throws Exception {
            System.out.println("» starting dev mode...");
            System.out.println("» waiting for frontend-server on port: " + port);

            //TODO logic
            // 1. frontend pnpm/npm etc.
            // 2. trigger maven, gradle etc.
            // 3. webview

            return 0;
        }
    }

    @Command(name = "build", description = "Builds the application to a jar file and;or native binary")
    public static class BuildCommand implements Callable<Integer> {

        @Option(names = {"--no-minify"}, description = "Disables minification of frontend assets")
        private boolean noMinify;

        @Override
        public Integer call() throws Exception {
            System.out.println("» starting build...");

            //TODO logic
            // 1. frontend build
            // 2. copy assets to src/main/resources
            // 3. jpackage/graal/native

            return 0;
        }
    }
}
