package com.project.classes;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.util.Log;

public class DataParser extends DefaultHandler {
	ArrayList<String> make = null; // Attributes
	public ArrayList<String> getInformation() {
		DataParser Parser = new DataParser();
		return make;
	}


		@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
			int eventType = -1;
			while (eventType != XmlResourceParser.END_DOCUMENT) {
				if (localName.equals("menu_item"))
					make.add(attributes.getValue("text"));
					
			}
		
				}

		private int processDownloadData(XmlPullParser xmlData) {

			int recordsFound = 0; // Find values in the XML records
			
			int eventType = -1;
			while (eventType != XmlResourceParser.END_DOCUMENT) {
				String tagName = xmlData.getName();
				switch (eventType) { 
				case XmlResourceParser.START_TAG: // Start of a record, so pull values encoded as attributes. 
					if (tagName.equals("menu_item")) { 
						make.add(xmlData.getAttributeValue(null, "text"));
					} break; // Grab data text (very simple processing) // NOTE: This could be full XML data to process. case XmlResourceParser.TEXT: data += xmlData.getText(); break; case XmlPullParser.END_TAG: if (tagName.equals("record")) { recordsFound++; publishProgress(appId, itemId, data, timeStamp); } break; } eventType = xmlData.next();
				} // Handle no data available: Publish an empty event.

				return recordsFound;
			}
			return 0;
		}


	
}
