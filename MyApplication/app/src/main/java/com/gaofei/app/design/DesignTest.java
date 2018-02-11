package com.gaofei.app.design;

import com.gaofei.app.design.bridge.ExportFile;
import com.gaofei.app.design.bridge.MySqlDataSource;
import com.gaofei.app.design.bridge.OracleDataSource;
import com.gaofei.app.design.bridge.TextExportFile;
import com.gaofei.app.design.bridge.XMLExportFile;
import com.gaofei.app.design.decorator.ConcreteComponent;
import com.gaofei.app.design.decorator.Decorator1;
import com.gaofei.app.design.decorator.Decorator2;
import com.gaofei.app.design.proxy.ProxyImpl;

/**
 * Created by gaofei on 11/02/2018.
 */

public class DesignTest {
    public static void main(String[] args) {
        decorator();
        proxy();
        bridge();
    }

    private static void decorator() {
        Component component = new Decorator1(new Decorator2(new ConcreteComponent()));
        component.sample();
    }

    private static void proxy() {
        Component proxy = new ProxyImpl();
        proxy.sample();

    }

    private static void bridge() {
        ExportFile mySqlText = new TextExportFile();
        mySqlText.setDataSource(new MySqlDataSource());
        mySqlText.exportFile();
        ExportFile oracleXML = new XMLExportFile();
        oracleXML.setDataSource(new OracleDataSource());
        oracleXML.exportFile();
    }
}
