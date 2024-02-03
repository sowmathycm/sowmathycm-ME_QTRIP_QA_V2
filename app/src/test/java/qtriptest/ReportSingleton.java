package qtriptest;

import qtriptest.tests.testCase_01;
import java.io.File;
import java.sql.Timestamp;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ReportSingleton {

    private static ExtentReports extentreportinstance;

    private ReportSingleton(){

    }

    public static synchronized ExtentReports getInstance(){

       Timestamp timestamp = new Timestamp(System.currentTimeMillis());
       String timestampString = String.valueOf(timestamp.getTime());

        if(extentreportinstance == null){
            extentreportinstance = new ExtentReports(System.getProperty("user.dir")+"/ExtentReport"+timestampString+".html", true);
            extentreportinstance.loadConfig(new File(System.getProperty("user.dir") + "/extent_customization_configs.xml"));
        }

        return extentreportinstance;

        }
       
    
    
    public static synchronized void endTest(ExtentTest test){
        if(extentreportinstance!=null){
            extentreportinstance.endTest(test);
        }
    }

    public static synchronized void flush(){
        if(extentreportinstance!=null){
            extentreportinstance.flush();
        }
    }
}

