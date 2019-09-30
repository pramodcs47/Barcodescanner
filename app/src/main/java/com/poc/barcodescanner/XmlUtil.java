package com.poc.barcodescanner;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlUtil {
    XmlPullParser parser;
    public  XmlUtil() throws ParserConfigurationException, XmlPullParserException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        parser = factory.newPullParser();

    }

    Data parse(String xml) throws XmlPullParserException {
        Data data =  new Data();
        InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));

        parser.setInput(stream,null);
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("PrintLetterBarcodeData")) {
                            // create a new instance of employee
     //                       employee = new Employee();
                            Log.i("xmlutil","inside Print letter barcode");
                            data.uid =parser.getAttributeValue("","uid");
                            data.name =parser.getAttributeValue("","name");
                            data.gender =parser.getAttributeValue("","gender");
                            data.yob =parser.getAttributeValue("","yob");
                            data.address = parser.getAttributeValue("","co");
                            //data.address.concat(" house-");
                            data.address+= parser.getAttributeValue("","house");
                            //data.address.concat(" street-");
                            data.address+= parser.getAttributeValue("","street");
                            //data.address.concat(" vtc-");
                            data.address+= parser.getAttributeValue("","vtc");
                            //data.address.concat(" dist-");
                            data.address+= parser.getAttributeValue("","dist");
                            //data.address.concat(" state-");
                            data.address+= parser.getAttributeValue("","state");
                            //data.address.concat(" pc-");
                            data.address+= parser.getAttributeValue("","pc");
                        }
                        break;

                    case XmlPullParser.TEXT:

       //                 text = parser.getText();
                        break;


                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("PrintedLetterBarcodecodeData")) {
                            // add employee object to list
         //                   employees.add(employee);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        return data;
    }

}




