package com.poc.barcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    Button scanButton;
    Button scanButtonImage;
    private IntentIntegrator qrScan;
    TextView textName;
    TextView textGender;
    TextView textYob;
    TextView textAddress;
    TextView barcodeContent;
    Map hintMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrScan = new IntentIntegrator(this);

        scanButton =(Button) findViewById(R.id.barcode_scan);
        scanButtonImage = (Button) findViewById(R.id.barcode_scan_image);
        textName = (TextView) findViewById(R.id.textname);
        textAddress = (TextView) findViewById(R.id.textaddress);
        textGender = (TextView) findViewById(R.id.textgender);
        textYob = (TextView) findViewById(R.id.textyob);

        hintMap = new HashMap();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });
        scanButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    try
                {
                    readQRCode();

                }
                catch(IOException ex)
                {
                    return;
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                   // JSONObject obj = new JSONObject(result.getContents());
                    XmlUtil xmlUtil = new XmlUtil();
                    Log.i("xmlutil"," result"+result.getContents());
                    Data info =  xmlUtil.parse(result.getContents());

                   //setting values to textviews
                    textName.setText(info.name);
                    textGender.setText(info.gender);
                    textYob.setText(info.yob);
                    textAddress.setText(info.address);


                }  catch (ParserConfigurationException e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    textName.setText(result.getFormatName());
                    textAddress.setText(result.getContents());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /*public String readQRCode() throws FileNotFoundException, IOException, NotFoundException {
        InputStream inputStream = getAssets().open("qr_code_5cdd30e269752.jpg");
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(inputStream))));

        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
                hintMap);

        return qrCodeResult.getText();
    }*/
}
