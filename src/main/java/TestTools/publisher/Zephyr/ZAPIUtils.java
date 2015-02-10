package TestTools.publisher.Zephyr;

import TestTools.httpnotifier.CloseableHttpClientFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by nizienko on 05.12.14.
 */
public class ZAPIUtils {
    public static final Logger LOG = Logger.getLogger(ZAPIUtils.class);
    private CloseableHttpClient httpClient = CloseableHttpClientFactory.getInstance().createHttpClient();
    String zephyr;
    String authorizationPair;
    String jira;

    public ZAPIUtils(String url, String authorizationPair) {
        this.jira = url + "api/2/";
        this.zephyr = url + "zapi/latest/";
        this.authorizationPair = authorizationPair;
    }

    public ArrayList<HashMap<String, String>> getVersions(String project) {
        try {
            String result = makeGet(zephyr + "util/versionBoard-list?projectId=" + project);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(result);
            ArrayList<HashMap<String, String>> rs = (ArrayList<HashMap<String, String>>) jsonObject.get("versionOptions"); // unreleasedVersions
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public ArrayList<HashMap<String, String>> getJiraVersions(String projectKey) {
        try {
            ArrayList<HashMap<String, String>> resArrayList = new ArrayList<HashMap<String, String>>();
            String result = makeGet(jira + "project/" + projectKey + "/versions");
            JSONParser parser = new JSONParser();
            ArrayList<HashMap<String, String>> rs = (ArrayList<HashMap<String, String>>) parser.parse(result);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String createVersion(String name, String projectKey) {
        String request = "{\n" +
                "    \"description\": \"\",\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"archived\": false,\n" +
                "    \"released\": false,\n" +
                "    \"project\": \"" + projectKey + "\"\n" +
                "}";
        String result = makePost(jira + "version", request);
        return result;
    }

    public ArrayList<HashMap<String, String>> getCycles(String project, String version) {
        try {
            ArrayList<HashMap<String, String>> resArrayList = new ArrayList<HashMap<String, String>>();
            String result = makeGet(zephyr + "cycle?projectId=" + project + "&versionId=" + version + "&offset=0");
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(result);
            Set keys = jsonObject.keySet();
            Iterator a = keys.iterator();
            while (a.hasNext()) {
                String key = (String) a.next();
                Integer id = null;
                try {
                    id = Integer.parseInt(key);
                } catch (NumberFormatException e) {
                }

                if (id != null) {
                    JSONObject array = (JSONObject) jsonObject.get(key);
                    HashMap<String, String> cycle = new HashMap<String, String>();
                    cycle.put("id", key);
                    cycle.put("name", (String) array.get("name"));
                    resArrayList.add(cycle);
                }
            }
            return resArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String createCycle(String project, String version, String name, String versionName) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        Date now = new Date();
        Date tommorow = new Date(now.getTime() + (1000 * 60 * 60 * 24));

        String request = "{\n" +
                "    \"clonedCycleId\": \"\",\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"build\": \"" + versionName + "." + name + "\",\n" +
                "    \"environment\": \"\",\n" +
                "    \"description\": \"Created " + dateFormat.format(now).toString() + "\",\n" +
                "    \"startDate\": \"" + dateFormat.format(now).toString() + "\",\n" +
                "    \"endDate\": \"" + dateFormat.format(tommorow).toString() + "\",\n" +
                "    \"projectId\": \"" + project + "\",\n" +
                "    \"versionId\": \"" + version + "\"\n" +
                "}";
        LOG.info(request);
        String result = makePost(zephyr + "cycle", request);
        return result;
    }

    public void addTestsToCycle(String test, String versionId, String cycleId, String projectId) {
        if (!"".equals(test)) {
//            String issueList = tests.substring(0, tests.toString().length() - 2);
            System.out.println("adding issue list to cycleId '" + cycleId + "': " + test);
            String request = "{\n" +
                    "    \"issues\": [\n" +
                    "        \"" + test + "\"\n" +
                    "    ],\n" +
                    "    \"versionId\": \"" + versionId + "\",\n" +
                    "    \"cycleId\": \"" + cycleId + "\",\n" +
                    "    \"projectId\": \"" + projectId + "\",\n" +
                    "    \"method\": \"1\"\n" +
                    "}";
            String result = makePost(zephyr + "execution/addTestsToCycle", request);
        }
    }


    public ArrayList<HashMap<String, String>> getExecutions(String cycleName, String version) {
        try {
            ArrayList<HashMap<String, String>> resArrayList = new ArrayList<HashMap<String, String>>();
            String zqlQuery = "cycleName = '" + cycleName + "' and fixVersion = '" + version + "'";
            // Наверное баг в ZQL заменим Ad hoc на Adhoc
            String result = makeGet(zephyr + "zql/executeSearch?zqlQuery=" + URLEncoder.encode(zqlQuery
                    .replace("Ad hoc", "Adhoc"), "UTF-8") + "&maxRecords=10000");
//            System.out.println(zqlQuery);
//            System.out.println(result);
            if (result.contains("executions")) {
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(result);
                ArrayList<JSONObject> jsonArray = (ArrayList<JSONObject>) jsonObject.get("executions");
                for (JSONObject execution : jsonArray) {
                    JSONObject status = (JSONObject) execution.get("status");
                    HashMap<String, String> executionHashMap = new HashMap<String, String>();
                    executionHashMap.put("id", execution.get("id").toString());
                    executionHashMap.put("cycleId", execution.get("cycleId").toString());
                    executionHashMap.put("cycleName", execution.get("cycleName").toString());
                    executionHashMap.put("issueKey", execution.get("issueKey").toString());
                    executionHashMap.put("issueSummary", execution.get("issueSummary").toString());
                    executionHashMap.put("priority", execution.get("priority").toString());
//                    executionHashMap.put("component", execution.get("component").toString());
                    executionHashMap.put("versionId", execution.get("versionId").toString());
//                    executionHashMap.put("version", execution.get("version").toString());
                    executionHashMap.put("statusId", status.get("id").toString());
                    executionHashMap.put("statusName", status.get("name").toString());
                    executionHashMap.put("description", status.get("description").toString());
                    resArrayList.add(executionHashMap);
                }
            }
            return resArrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void quickExecute(String id, String status) {
        String request = "{\n" +
                "\"status\": " + status + "\n" +
                "}";
        makePost(zephyr + "execution/" + id + "/quickExecute", request);
    }

    public void execute(String id, Integer status, String comment) {
        String request = "{\n" +
                "\"status\": " + status + ",\n" +
                "\"comment\": \"" + comment + "\"\n" +
                "}";
        LOG.info(request);
        String result = makePut(zephyr + "execution/" + id + "/execute", request);
    }


    public String makeGet(String cmd) {
        HttpGet httpget = new HttpGet(cmd);
        httpget.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpget.setHeader("Authorization", "Basic " + authorizationPair);
        LOG.info("GET: " + cmd);
        try {
            HttpResponse response = httpClient.execute(httpget);
            try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer responseBody = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                responseBody.append(line + "\r\n");
                LOG.info("   res:" + line);
            }
            return responseBody.toString();
            }
            finally {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            httpget.releaseConnection();
        }

    }

    public String makePost(String cmd, String request) {
        HttpPost httpPost = new HttpPost(cmd);
        httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpPost.setHeader("Authorization", "Basic " + authorizationPair);
        try {
            StringEntity reqEntity = new StringEntity(request, "UTF-8");
            httpPost.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPost);
            try{
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer responseBody = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    LOG.info("   post: " + line);
                    responseBody.append(line + "\r\n");
                }
                return responseBody.toString();
            }
            finally {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            httpPost.releaseConnection();
        }
    }

    public String makePut(String cmd, String request) {
        HttpPut httpPut = new HttpPut(cmd);
        httpPut.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpPut.setHeader("Authorization", "Basic " + authorizationPair);
        try {
            StringEntity reqEntity = new StringEntity(request, "UTF-8");
            httpPut.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPut);
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer responseBody = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    LOG.info("  put: " + line);
                    responseBody.append(line + "\r\n");
                }
                return responseBody.toString();
            }
            finally {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            httpPut.releaseConnection();
        }
    }

}
