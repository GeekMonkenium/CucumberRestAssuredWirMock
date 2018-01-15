package Runner;

import com.cucumber.listener.Reporter;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import java.io.File;

@RunWith(Cucumber.class)
@CucumberOptions(
		glue = {"com.countries"},
		features = {"src/test/resources/com/countries"},
		plugin = {"com.cucumber.listener.ExtentCucumberFormatter:reports/reports.html"}
)
public class CucumberTests {
	@AfterClass
	public static void teardown() {
		Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
		Reporter.setSystemInfo("user", "Jatin Mehta");
		Reporter.setSystemInfo("os", "Microsoft Windows 10 Pro");
		Reporter.setTestRunnerOutput("ALL Countries API Test");
	}
}
