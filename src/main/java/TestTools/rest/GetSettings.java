package TestTools.rest;

import TestTools.database.testsettings.TestSetting;
import org.jdom.Document;
import org.jdom.Element;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by nizienko on 13.11.14.
 */
public class GetSettings extends RestServlet {
    @Override
    public void process(Map<String, String[]> in, PrintWriter out) {
        try {
            out.print(buildXMLAnswer(getXmlSettings(in)));
        } catch (IllegalArgumentException e) {
            out.print(buildXMLAnswer(getErrorMessage("testconfiguration missed")));
        } catch (Exception e) {
            out.print(buildXMLAnswer(getErrorMessage("error")));
        }
    }

    private Document getXmlSettings(Map<String, String[]> in) {
        Element root = new Element("TestTools");
        Element mainNode = new Element("Settings");
        root.addContent(mainNode);
        Document doc = new Document(root);
        List<TestSetting> testSettings = new ArrayList<TestSetting>();
        try {
            String testConfiguration = in.get("testconfiguration")[0];
            String contains = null;
            if (in.containsKey("contains")) {
                contains = in.get("contains")[0];
            }
            testSettings = testManager.getSettings(testConfiguration, contains);
            mainNode.setAttribute("testconfiguration", testConfiguration);
            for (TestSetting testSetting : testSettings) {
                Element node = new Element("Setting");
                node.setAttribute("name", testSetting.getParameterName());
                node.setText(testSetting.getValue());
                mainNode.addContent(node);
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
        return doc;
    }

    private Document getErrorMessage(String error) {
        Element root = new Element("TestTools");
        Element mainNode = new Element("Error");
        root.addContent(mainNode);
        Document doc = new Document(root);
        mainNode.setText(error);
        return doc;
    }
}
