package edu.project3;

@SuppressWarnings("HideUtilityClassConstructor")
public class Main {
    // for create a jar file
    public static void main(String[] args) {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.analyze(args);
    }
}
