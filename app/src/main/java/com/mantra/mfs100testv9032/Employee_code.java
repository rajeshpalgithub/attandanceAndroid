package com.mantra.mfs100testv9032;



import android.app.Activity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Base64;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.JsonObject;
import com.mantra.mfs100.FingerData;
import com.mantra.mfs100.MFS100;
import com.mantra.mfs100.MFS100Event;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by rajesh on 4/2/19.
 */

public class Employee_code extends Activity implements MFS100Event {

    private String emp_code = "";


    Button btnInit;
    Button btnUninit;
    Button btnSyncCapture;
    Button btnStopCapture;
    Button btnMatchISOTemplate;
    Button btnExtractISOImage;
    Button btnExtractAnsi;
    Button btnExtractWSQImage;
    // Button btnClearLog;
    TextView lblMessage;
    EditText txtEventLog;
    ImageView imgFinger;
    ImageView employeeImage;
    //CheckBox cbFastDetection;
    //////// Rajesh Code ////
    Button btnEnter;
    EditText et;
    TextView textViewEmpName;
    TextView textViewBranch;
    JSONArray templates;

    private ProgressBar spinner;

    private enum ScannerAction {
        Capture, Verify
    }

    byte[] Enroll_Template;
    byte[] Verify_Template;
    private FingerData lastCapFingerData = null;
    ScannerAction scannerAction = ScannerAction.Capture;

    int timeout = 10000;
    MFS100 mfs100 = null;

    private boolean isCaptureRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_code);

        et = (EditText) findViewById(R.id.txtEmpcode);
        et.setKeyListener(null);

        FindFormControls();

        /*String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        TextView dateTime = (TextView) findViewById(R.id.lblDateTime);
        dateTime.setText(currentDateTimeString);*/

        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {

                            public void run() {
                                TextView tdate = (TextView) findViewById(R.id.lblDateTime);

                                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                tdate.setText(currentDateTimeString);
                            }
                        });
                    }
                } catch (InterruptedException e) {

                }
            }
        };
        t.start();
        try {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }


        Button eb = (Button) findViewById(R.id.btnE);
        eb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = et.getText().toString();
                et.setText(txt.concat("E"));
            }
        });
        Button ezero = (Button) findViewById(R.id.btn0);
        ezero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = et.getText().toString();
                et.setText(txt.concat("0"));
            }
        });
        Button bone = (Button) findViewById(R.id.btn1);
        bone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = et.getText().toString();
                et.setText(txt.concat("1"));
            }
        });
        Button btwo = (Button) findViewById(R.id.btn2);
        btwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = et.getText().toString();
                et.setText(txt.concat("2"));
            }
        });
        Button bthree = (Button) findViewById(R.id.btn3);
        bthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = et.getText().toString();
                et.setText(txt.concat("3"));
            }
        });
        Button b4 = (Button) findViewById(R.id.btn4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = et.getText().toString();
                et.setText(txt.concat("4"));
            }
        });
        Button b5 = (Button) findViewById(R.id.btn5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = et.getText().toString();
                et.setText(txt.concat("5"));
            }
        });
        Button b6 = (Button) findViewById(R.id.btn6);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = et.getText().toString();
                et.setText(txt.concat("6"));
            }
        });
        Button b7 = (Button) findViewById(R.id.btn7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = et.getText().toString();
                et.setText(txt.concat("7"));
            }
        });
        Button b8 = (Button) findViewById(R.id.btn8);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = et.getText().toString();
                et.setText(txt.concat("8"));
            }
        });
        Button b9 = (Button) findViewById(R.id.btn9);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = et.getText().toString();
                et.setText(txt.concat("9"));
            }
        });
        ImageButton bclear = (ImageButton) findViewById(R.id.btnClear);
        bclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = et.getText().toString();
                et.setText("");
                textViewEmpName.setText("Employee Name");
                textViewBranch.setText("Branch Name");
                lblMessage.setText("");
                btnEnter.setEnabled(true);
                // employeeImage.getDrawable();
                String uri = "@drawable/employee";  // where myresource (without the extension) is the file
                int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                Drawable res = getResources().getDrawable(imageResource);
                employeeImage.setImageDrawable(res);

                String uri2 = "@drawable/finger";  // where myresource (without the extension) is the file
                int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                Drawable res2 = getResources().getDrawable(imageResource2);
                imgFinger.setImageDrawable(res2);


            }
        });
    }

    /***********************/
    @Override
    protected void onStart() {
        if (mfs100 == null) {
            mfs100 = new MFS100(this);
            mfs100.SetApplicationContext(Employee_code.this);
        } else {
            InitScanner();
        }
        super.onStart();
    }

    /*protected void onStop() {
            UnInitScanner();
            super.onStop();
    }
    protected void onResume() {

            InitScanner();

            super.onResume();
        }*/
    @Override
    protected void onDestroy() {
        if (mfs100 != null) {
            mfs100.Dispose();
        }
        super.onDestroy();
    }

    public void FindFormControls() {
        btnInit = (Button) findViewById(R.id.btnInit);
        btnUninit = (Button) findViewById(R.id.btnUninit);
        btnMatchISOTemplate = (Button) findViewById(R.id.btnMatchISOTemplate);
        btnExtractISOImage = (Button) findViewById(R.id.btnExtractISOImage);
        btnExtractAnsi = (Button) findViewById(R.id.btnExtractAnsi);
        btnExtractWSQImage = (Button) findViewById(R.id.btnExtractWSQImage);
        // btnClearLog = (Button) findViewById(R.id.btnClearLog);
        lblMessage = (TextView) findViewById(R.id.lblMessage);
        txtEventLog = (EditText) findViewById(R.id.txtEventLog);
        imgFinger = (ImageView) findViewById(R.id.imgFinger);
        btnSyncCapture = (Button) findViewById(R.id.btnSyncCapture);
        btnStopCapture = (Button) findViewById(R.id.btnStopCapture);
        //
        // cbFastDetection = (CheckBox) findViewById(R.id.cbFastDetection);
        //////// Rajesh Code /////
        btnEnter = (Button) findViewById(R.id.btnEnter);
        textViewEmpName = (TextView) findViewById(R.id.lblEmployeeName);
        textViewBranch = (TextView) findViewById(R.id.lblBranchName);
        employeeImage = (ImageView) findViewById(R.id.employeeImage);

    }

    public void onControlClicked(View v) {


        switch (v.getId()) {

            case R.id.btnEnter:
                if(et.getText().toString().length() > 0){

                    scannerAction = Employee_code.ScannerAction.Capture;
                    if (!isCaptureRunning) {
                        btnEnter.setEnabled(false);
                        StartSyncCapture();
                        //callApi(et.getText().toString());
                    }
                    //callApi(et.getText().toString());
                    //addLog("Yes",emp_code);
                }

                break;

            default:
                break;
        }
    }

    private void InitScanner() {
        try {

            int ret = mfs100.Init();
            if (ret != 0) {
                SetTextOnUIThread(mfs100.GetErrorMsg(ret));
            } else {
                SetTextOnUIThread("Init success");
                String info = "Serial: " + mfs100.GetDeviceInfo().SerialNo()
                        + " Make: " + mfs100.GetDeviceInfo().Make()
                        + " Model: " + mfs100.GetDeviceInfo().Model()
                        + "\nCertificate: " + mfs100.GetCertification();
                SetLogOnUIThread(info);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Init failed, unhandled exception",
                    Toast.LENGTH_LONG).show();
            SetTextOnUIThread("Init failed, unhandled exception");
        }

    }

    private void StartSyncCapture() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                SetTextOnUIThread("");
                isCaptureRunning = true;
                try {
                    FingerData fingerData = new FingerData();
                    int ret = mfs100.AutoCapture(fingerData, timeout, true);
                    Log.e("StartSyncCapture.RET", "" + ret);
                    if (ret != 0) {
                        SetTextOnUIThread(mfs100.GetErrorMsg(ret));
                    } else {
                        lastCapFingerData = fingerData;
                        final Bitmap bitmap = BitmapFactory.decodeByteArray(fingerData.FingerImage(), 0,
                                fingerData.FingerImage().length);
                        Employee_code.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgFinger.setImageBitmap(bitmap);
                            }
                        });

                        SetTextOnUIThread("Capture Success");
                        /*String log = "\nQuality: " + fingerData.Quality()
                                + "\nNFIQ: " + fingerData.Nfiq()
                                + "\nWSQ Compress Ratio: "
                                + fingerData.WSQCompressRatio()
                                + "\nImage Dimensions (inch): "
                                + fingerData.InWidth() + "\" X "
                                + fingerData.InHeight() + "\""
                                + "\nImage Area (inch): " + fingerData.InArea()
                                + "\"" + "\nResolution (dpi/ppi): "
                                + fingerData.Resolution() + "\nGray Scale: "
                                + fingerData.GrayScale() + "\nBits Per Pixal: "
                                + fingerData.Bpp() + "\nWSQ Info: "
                                + fingerData.WSQInfo();
                        SetLogOnUIThread(log);*/
                        // save data to variable
                        SetData2(fingerData);
                    }

                } catch (Exception ex) {
                    SetTextOnUIThread(ex.toString() + " " + ex.getStackTrace()[0].getLineNumber());
                } finally {
                    isCaptureRunning = false;
                }
            }
        }).start();
    }

    private void StopCapture() {
        try {
            mfs100.StopAutoCapture();
        } catch (Exception e) {
            SetTextOnUIThread("Error");
        }
    }

    private void ExtractANSITemplate() {
        try {
            if (lastCapFingerData == null) {
                SetTextOnUIThread("Finger not capture");
                return;
            }
            byte[] tempData = new byte[2000]; // length 2000 is mandatory
            byte[] ansiTemplate;
            int dataLen = mfs100.ExtractANSITemplate(lastCapFingerData.RawData(), tempData);
            if (dataLen <= 0) {
                if (dataLen == 0) {
                    SetTextOnUIThread("Failed to extract ANSI Template");
                } else {
                    SetTextOnUIThread(mfs100.GetErrorMsg(dataLen));
                }
            } else {
                ansiTemplate = new byte[dataLen];
                System.arraycopy(tempData, 0, ansiTemplate, 0, dataLen);
                // WriteFile("ANSITemplate.ansi", ansiTemplate);
                //SetTextOnUIThread("Extract ANSI Template Success");
            }
        } catch (Exception e) {
            Log.e("Error", "Extract ANSI Template Error", e);
        }
    }

    private void ExtractISOImage() {
        try {
            if (lastCapFingerData == null) {
                SetTextOnUIThread("Finger not capture");
                return;
            }
            byte[] tempData = new byte[(mfs100.GetDeviceInfo().Width() * mfs100.GetDeviceInfo().Height()) + 1078];
            byte[] isoImage;
            int dataLen = mfs100.ExtractISOImage(lastCapFingerData.RawData(), tempData);
            if (dataLen <= 0) {
                if (dataLen == 0) {
                    SetTextOnUIThread("Failed to extract ISO Image");
                } else {
                    SetTextOnUIThread(mfs100.GetErrorMsg(dataLen));
                }
            } else {
                isoImage = new byte[dataLen];
                System.arraycopy(tempData, 0, isoImage, 0, dataLen);
                // WriteFile("ISOImage.iso", isoImage);
                //SetTextOnUIThread("Extract ISO Image Success");
            }
        } catch (Exception e) {
            Log.e("Error", "Extract ISO Image Error", e);
        }
    }

    private void ExtractWSQImage() {
        try {
            if (lastCapFingerData == null) {
                SetTextOnUIThread("Finger not capture");
                return;
            }
            byte[] tempData = new byte[(mfs100.GetDeviceInfo().Width() * mfs100.GetDeviceInfo().Height()) + 1078];
            byte[] wsqImage;
            int dataLen = mfs100.ExtractWSQImage(lastCapFingerData.RawData(), tempData);
            if (dataLen <= 0) {
                if (dataLen == 0) {
                    SetTextOnUIThread("Failed to extract WSQ Image");
                } else {
                    SetTextOnUIThread(mfs100.GetErrorMsg(dataLen));
                }
            } else {
                wsqImage = new byte[dataLen];
                System.arraycopy(tempData, 0, wsqImage, 0, dataLen);
                // WriteFile("WSQ.wsq", wsqImage);
                // SetTextOnUIThread("Extract WSQ Image Success");
            }
        } catch (Exception e) {
            Log.e("Error", "Extract WSQ Image Error", e);
        }
    }

    private void UnInitScanner() {
        try {
            int ret = mfs100.UnInit();
            if (ret != 0) {
                SetTextOnUIThread(mfs100.GetErrorMsg(ret));
            } else {
                SetLogOnUIThread("Uninit Success");
                SetTextOnUIThread("Uninit Success");
                lastCapFingerData = null;
            }
        } catch (Exception e) {
            Log.e("UnInitScanner.EX", e.toString());
        }
    }

   /* private void WriteFile(String filename, byte[] bytes) {
        try {
            String path = Environment.getExternalStorageDirectory()
                    + "//FingerData";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = path + "//" + filename;
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(path);
            stream.write(bytes);
            stream.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }*/

    private void ClearLog() {
       /* txtEventLog.post(new Runnable() {
            public void run() {
                txtEventLog.setText("", BufferType.EDITABLE);
            }
        });*/
    }

    private void SetTextOnUIThread(final String str) {

        lblMessage.post(new Runnable() {
            public void run() {
                lblMessage.setText(str);
            }
        });
        //Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }

    private void SetLogOnUIThread(final String str) {

        /*txtEventLog.post(new Runnable() {
            public void run() {
                txtEventLog.append("\n" + str);
            }
        });*/
    }

    public void SetData2(FingerData fingerData) {
        if (scannerAction.equals(Employee_code.ScannerAction.Capture)) {
            Enroll_Template = new byte[fingerData.ISOTemplate().length];
            System.arraycopy(fingerData.ISOTemplate(), 0, Enroll_Template, 0,
                    fingerData.ISOTemplate().length);

            ///// call api ////
            //
            //
            //
            callApi(et.getText().toString());


        } /*else if (scannerAction.equals(Employee_code.ScannerAction.Verify)) {
            Verify_Template = new byte[fingerData.ISOTemplate().length];
            System.arraycopy(fingerData.ISOTemplate(), 0, Verify_Template, 0,
                    fingerData.ISOTemplate().length);
            int ret = mfs100.MatchISO(Enroll_Template, Verify_Template);
            if (ret < 0) {
                SetTextOnUIThread("Error: " + ret + "(" + mfs100.GetErrorMsg(ret) + ")");
            } else {
                if (ret >= 1400) {
                    SetTextOnUIThread("Finger matched with score: " + ret);
                } else {
                    SetTextOnUIThread("Finger not matched, score: " + ret);
                }
            }*/
    }

    // WriteFile("Raw.raw", fingerData.RawData());
    //WriteFile("Bitmap.bmp", fingerData.FingerImage());
    // WriteFile("ISOTemplate.iso", fingerData.ISOTemplate());


    @Override
    public void OnDeviceAttached(int vid, int pid, boolean hasPermission) {
        int ret;
        if (!hasPermission) {
            SetTextOnUIThread("Permission denied");
            return;
        }
        if (vid == 1204 || vid == 11279) {
            if (pid == 34323) {
                ret = mfs100.LoadFirmware();
                if (ret != 0) {
                    SetTextOnUIThread(mfs100.GetErrorMsg(ret));
                } else {
                    SetTextOnUIThread("Load firmware success");
                }
            } else if (pid == 4101) {
                String key = "Without Key";
                ret = mfs100.Init();
                if (ret == 0) {
                    showSuccessLog(key);
                } else {
                    SetTextOnUIThread(mfs100.GetErrorMsg(ret));
                }

            }
        }
    }

    private void showSuccessLog(String key) {
        SetTextOnUIThread("Init success");
        String info = "\nKey: " + key + "\nSerial: "
                + mfs100.GetDeviceInfo().SerialNo() + " Make: "
                + mfs100.GetDeviceInfo().Make() + " Model: "
                + mfs100.GetDeviceInfo().Model()
                + "\nCertificate: " + mfs100.GetCertification();
        SetLogOnUIThread(info);
    }

    @Override
    public void OnDeviceDetached() {
        UnInitScanner();
        SetTextOnUIThread("Device removed");
    }

    @Override
    public void OnHostCheckFailed(String err) {
        try {
            SetLogOnUIThread(err);
            Toast.makeText(this, err, Toast.LENGTH_LONG).show();
        } catch (Exception ignored) {
        }
    }

    /************* API CALL *********/
    final String URL = "http://52.44.198.156/index.php/employee";

    public void callApi(final String emp_code) {


        /*pDialog.setMessage("Verifing...");
        pDialog.show();*/
        SetTextOnUIThread("Verifying...");


        String iso_image = "";
        try {
            iso_image = new String(Enroll_Template, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final JSONObject request = new JSONObject();
        try {
            request.put("employee_code", emp_code);
            //request.put("image", iso_image);

        } catch (Exception e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(this);


        final JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST, URL + "/check_code/",
                request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.e("Rest Response", response.toString());
                        try {

                            String error = response.getString("error");


                            if (error == "true") {
                                JSONArray errorTexts = response.getJSONArray("errorText");
                                String err = null;
                                for (int i = 0; i < errorTexts.length(); i++) {
                                    err = errorTexts.getString(i) + ",";


                                }
                                play_wrong();
                                SetTextOnUIThread(err);

                            }else {
                                JSONObject result = response.getJSONObject("result");
                                JSONObject employee = result.getJSONObject("employee");


                                JSONArray templates = employee.getJSONArray("templates");
                                Log.e("templates", templates.toString());

                                //txtEventLog.append("\n"+"Length:"+templates.length());

                                byte[][] Verify_Template = new byte[4][0];
                                /*byte[] Verify_Template2 = new byte[2000];
                                byte[] Verify_Template3 = new byte[2000];
                                byte[] Verify_Template4 = new byte[2000];*/

                                for(int temp=0;temp<templates.length();temp++){
                                    JSONObject jsonObject = templates.getJSONObject(temp);
                                    String value = jsonObject.optString("ios_template");

                                    byte[] secondTemp = Base64.decode(value, Base64.DEFAULT);
                                    Verify_Template[temp] = new byte[secondTemp.length];
                                    System.arraycopy(secondTemp, 0, Verify_Template[temp], 0,
                                            secondTemp.length);

                                }


                                /*JSONObject jsonObject1 = templates.getJSONObject(0);
                                String value1 = jsonObject1.optString("ios_template");

                                byte[] secondTemp1 = Base64.decode(value1, Base64.DEFAULT);
                                Verify_Template1 = new byte[secondTemp1.length];
                                System.arraycopy(secondTemp1, 0, Verify_Template1, 0,
                                        secondTemp1.length);
                                //// 2 /////
                                JSONObject jsonObject2 = templates.getJSONObject(1);
                                String value2 = jsonObject2.optString("ios_template");

                                byte[] secondTemp2 = Base64.decode(value2, Base64.DEFAULT);
                                Verify_Template2 = new byte[secondTemp2.length];
                                System.arraycopy(secondTemp2, 0, Verify_Template2, 0,
                                        secondTemp2.length);
                                //////// 3 //////
                                JSONObject jsonObject3 = templates.getJSONObject(2);
                                String value3 = jsonObject3.optString("ios_template");

                                byte[] secondTemp3 = Base64.decode(value3, Base64.DEFAULT);
                                Verify_Template3 = new byte[secondTemp3.length];
                                System.arraycopy(secondTemp3, 0, Verify_Template3, 0,
                                        secondTemp3.length);
                                //////// 4 //////
                                JSONObject jsonObject4 = templates.getJSONObject(3);
                                String value4 = jsonObject4.optString("ios_template");

                                byte[] secondTemp4 = Base64.decode(value4, Base64.DEFAULT);
                                Verify_Template4 = new byte[secondTemp4.length];
                                System.arraycopy(secondTemp4, 0, Verify_Template4, 0,
                                        secondTemp4.length);*/


                                int ret1 = 1;
                                if(Verify_Template[0] != null && Verify_Template[0].length > 0) {
                                    ret1 = mfs100.MatchISO(Enroll_Template, Verify_Template[0]);
                                }
                                int ret2 = 1;
                                if(Verify_Template[1] != null && Verify_Template[1].length > 0){
                                    ret2 = mfs100.MatchISO(Enroll_Template, Verify_Template[1]);
                                }
                                int ret3 = 1;
                                if(Verify_Template[2] != null && Verify_Template[2].length > 0){
                                    ret3 = mfs100.MatchISO(Enroll_Template, Verify_Template[2]);
                                }
                                int ret4 = 1;
                                if(Verify_Template[3] != null && Verify_Template[3].length > 0){
                                    ret4 = mfs100.MatchISO(Enroll_Template, Verify_Template[3]);
                                }


                                //Log.e("fingre",value1);
                                /******** matching **********/

                                //txtEventLog.append("\n"+"Stape "+i+":" + ret);


                                if ((ret1 < 0) || (ret2 < 0) || (ret3 < 0) || (ret4 < 0)) {
                                    SetTextOnUIThread("Error: " + "(" + mfs100.GetErrorMsg(4) + ")");
                                } else {
                                    if ((ret1 >= 1400) || (ret2 >= 1400) || (ret3 >= 1400) || (ret4 >= 1400)) {

                                        String name = employee.getString("name");
                                        String branch = employee.getString("branch");
                                        //String image = response.getString("Image");
                                        // String isMatch = response.getString("IsMatch");
                                        textViewEmpName.setText(name);
                                        textViewBranch.setText(branch);

                                        /************* image **************/
                                        String base64String = employee.getString("image");
                                        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

                                        final Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0,
                                                decodedString.length);
                                        Employee_code.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                employeeImage.setImageBitmap(bitmap);
                                            }
                                        });
                                        //txtEventLog.append("\n Image:"+decodedString);
                                        /*********** end of image ******/
                                        addLog("Yes", emp_code);

                                    } else {
                                        addLog("No", emp_code);
                                        // SetTextOnUIThread("Finger not matched");

                                   }
                                }

                                //Log.e("template",templates.toString());


                                //Log.e("Eamloyee", employee.getString("name"));

                            }
                        } catch (JSONException e) {
                            play_wrong();
                            e.printStackTrace();
                            SetTextOnUIThread(e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        play_wrong();
                        SetTextOnUIThread(error.toString());
                       // Log.e("Rest Error", error.toString());
                    }
                }

        );
        requestQueue.add(objectRequest);
    }

    public void addLog(final String match,final String emp_code) {
        final JSONObject request = new JSONObject();





        try {
            request.put("employee_code", emp_code);
            request.put("match", match);

        } catch (Exception e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(this);



        final JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.POST, URL + "/add_attandance/",
                request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.e("Rest Response", response.toString());
                        try {

                            //Log.e("templates", response.toString());
                            JSONObject result = response.getJSONObject("result");

                            String success = result.getString("success");

                            if(success=="true" && match=="Yes") {
                                play_correct();
                                SetTextOnUIThread("Attandance Accepted");

                            }else if(success=="true" && match=="No"){
                                play_wrong();
                                SetTextOnUIThread("Finger not matched");

                            }
                            else{
                                play_wrong();
                                SetTextOnUIThread(response.toString());

                            }


                        } catch (JSONException e) {
                            play_wrong();
                            SetTextOnUIThread(e.toString());
                            //e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        play_wrong();
                        SetTextOnUIThread(error.toString());

                    }
                }

        );
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(objectRequest);


    }

    public void play_correct()
    {
        MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.correct);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //mMediaPlayer.setLooping(true);

        mMediaPlayer.start();
    }
    public void play_wrong(){
        MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.wrong2);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }
}
