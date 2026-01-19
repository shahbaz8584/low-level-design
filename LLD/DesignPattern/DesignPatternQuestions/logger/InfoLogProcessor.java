package DesignPattern.DesignPatternQuestions.logger;

public class InfoLogProcessor extends LogProcessor{

    InfoLogProcessor(LogProcessor nexLogProcessor) {
        super(nexLogProcessor);
    }

    @Override
    public void log(int logLevel, String message) {
        if(logLevel == INFO) {
            System.out.println("Info :" + message);
        }
        else {
            super.log(logLevel, message);
        }
    }
    
}
