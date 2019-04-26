package com.example.laskin;

import android.util.Xml;
import com.example.laskin.entity.Currency;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {

    private static final String TAG = "XmlParser";
    private static final String namespace = null;
    private String timeStamp = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List currencyList = new ArrayList();

        while (parser.next() != parser.END_DOCUMENT) {
            if (parser.getEventType() == parser.START_TAG) {
                if (parser.getName().equals("Cube")) {
                    if (timeStamp == null) {
                        timeStamp = parser.getAttributeValue(namespace, "time");
                    } else {
                        currencyList.add(readCurrency(parser));
                    }
                }
            }
        }
        return currencyList;
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Currency readCurrency(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, namespace, "Cube");
        String currencyName;
        Double currencyRelation;

        currencyName = parser.getAttributeValue(namespace, "currency");
        currencyRelation = Double.valueOf(parser.getAttributeValue(namespace, "rate"));

        return new Currency(currencyName, currencyRelation, timeStamp);
    }
}

