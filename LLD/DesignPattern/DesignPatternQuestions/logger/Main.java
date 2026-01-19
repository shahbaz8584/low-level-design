package DesignPattern.DesignPatternQuestions.logger;

public class Main {

    public static void main(String args[]) {
        LogProcessor logProcessor = new InfoLogProcessor(new DebugLogProcessor(new ErrorLogProcessor(null)));
        logProcessor.log(LogProcessor.DEBUG, " Need to Debug this");
        logProcessor.log(LogProcessor.ERROR, " Exception Occured");
        logProcessor.log(LogProcessor.INFO, " Just For Info");
    }
    
}
