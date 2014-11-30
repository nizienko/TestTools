package TestTools.vaadin.gui.sqlmanager;

import TestTools.core.MainApp;
import TestTools.database.DaoContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextArea;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by def on 30.11.14.
 */
public class SQLManager extends GridLayout {
    VerticalLayout result;
    private DaoContainer daoContainer;
    TextArea textArea;
    public SQLManager(){
        super(1, 3);
        this.setRowExpandRatio(2, 1);
        this.setSizeFull();
        result = new VerticalLayout();
        Button execute = new Button("Execute");
        this.addComponent(execute);
        result.setSizeFull();
        textArea = new TextArea();
        textArea.setWidth("100%");
        textArea.setValue("select * from sqlite_master");
        textArea.focus();
        this.addComponent(textArea);
        this.addComponent(result);
        daoContainer = (DaoContainer) MainApp.getCtx().getBean("daoContainer");
        execute.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String request = textArea.getValue();
                StringBuffer SQL = new StringBuffer();
                for (String s : request.split("\n")){
                    if (!s.startsWith("//")) {
                        SQL.append(s + "\n");
                    }
                }
                if (SQL.toString().startsWith("select")) {
                    try {
                        setResult((ArrayList<Map<String, Object>>) daoContainer.getJdbcTemplate().queryForList(SQL.toString()));
                    } catch (Exception e) {
                        TextArea resError = new TextArea();
                        resError.setValue(e.getMessage());
                        resError.setWidth("100%");
                        result.removeAllComponents();
                        result.addComponent(resError);
                    }
                }
                else {
                    int res;
                    try {
                        res = daoContainer.getJdbcTemplate().update(SQL.toString());
                        TextArea resError = new TextArea();
                        resError.setValue("Result: " + res);
                        resError.setWidth("100%");
                        result.removeAllComponents();
                        result.addComponent(resError);
                    } catch (Exception e) {
                        TextArea resError = new TextArea();
                        resError.setValue(e.getMessage());
                        resError.setWidth("100%");
                        result.removeAllComponents();
                        result.addComponent(resError);
                    }
                }
            }
        });

    }

    public void setResult(ArrayList<Map<String, Object>> rs){
        if (rs != null) {
            Table tableResult = new Table();
            result.removeAllComponents();
            tableResult.setWidth("100%");
            tableResult.setHeight("100%");
            tableResult.setEditable(false);
            tableResult.setSelectable(false);
            Iterator it = rs.get(0).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();
                tableResult.addContainerProperty(pairs.getKey(), pairs.getValue().getClass(), null);
            }
            int i = 1;
            for (Map<String, Object> s : rs){
                Object[] o = new Object[s.size()];
                int j = 0;
                Iterator it2 = s.entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry pairs = (Map.Entry)it2.next();
                    o[j] = pairs.getValue();
                    j++;
                }
                tableResult.addItem(o, new Integer(i));
                i++;
            }
            result.addComponent(tableResult);
        }
    }

}
