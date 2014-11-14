package TestTools.rest;

import TestTools.core.MainApp;
import TestTools.testmanager.TestManager;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by def on 06.11.14.
 */
public abstract class RestServlet extends HttpServlet {
    protected TestManager testManager;

    public abstract void process(Map<String, String[]> in, PrintWriter out);

    @Override
    public void init() {
        testManager = (TestManager) MainApp.getCtx().getBean("testManager");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/*; charset=utf-8");
        try {
            process(request.getParameterMap(), response.getWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String buildJSONAnswer(String status, String message) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status);
            if (message != null) {
                jsonObject.put("message", message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    protected String buildXMLAnswer(Document doc){
        StringWriter result = new StringWriter();
        try {

            XMLOutputter serializer = new XMLOutputter();
            serializer.setFormat(Format.getPrettyFormat());
            serializer.output(doc, result);
        }
        catch (IOException e) {
            System.err.println(e);
        }
        return result.toString();
    }
}
