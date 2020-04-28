package com.huriati.project.tvkabel.ui;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dicoding.picodiploma.mynavigationdrawer.R;
import com.huriati.project.tvkabel.DeviceList;
import com.huriati.project.tvkabel.MainActivity;
import com.huriati.project.tvkabel.PrinterCommands;
import com.huriati.project.tvkabel.SharedPrefManager;
import com.huriati.project.tvkabel.Utils;
import com.huriati.project.tvkabel.api.BaseApiService;
import com.huriati.project.tvkabel.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BayarActivity extends AppCompatActivity {

    TextView txtbulan, txtNominal, txtTotal, txtNamapel, txtIdpel, txtStatus;


    BaseApiService mApiService;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    String idTagihan,idPel, nominal, status, bulan, nama;
    TextView btnBayar, btnCetak;

    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar);

        sharedPrefManager = new SharedPrefManager(this);
        mApiService = UtilsApi.getAPIService(sharedPrefManager.getAuth());

        txtNamapel = findViewById(R.id.namaPel);
        txtIdpel = findViewById(R.id.idPel);
        txtbulan = findViewById(R.id.bulan);
        txtStatus = findViewById(R.id.status);
        txtNominal = findViewById(R.id.nominal);
        txtTotal = findViewById(R.id.total_bayar);
        btnBayar = findViewById(R.id.bayar);
        btnCetak = findViewById(R.id.cetak);

        status = getIntent().getStringExtra("status");
        bulan = getIntent().getStringExtra("bulan");
        nama = getIntent().getStringExtra("nama");
        idPel = getIntent().getStringExtra("idPel");
        nominal = getIntent().getStringExtra("nominal");
        idTagihan = getIntent().getStringExtra("idTagihan");

        if(status.equalsIgnoreCase("1")) {
            txtStatus.setText("Lunas");
            txtStatus.setTextColor(getResources().getColor(R.color.colorPrimary));
        }else {
            txtStatus.setText("Belum Bayar");
            txtStatus.setTextColor(getResources().getColor(R.color.colorAccent));
        }
        txtbulan.setText(bulan);
        txtNamapel.setText(nama);
        txtIdpel.setText(idPel);
        txtNominal.setText("Rp. "+ nominal);
        txtTotal.setText("Rp. "+nominal);

        if(status.equalsIgnoreCase("1")){
            btnBayar.setEnabled(false);
            btnCetak.setEnabled(true);
        }else{
            btnBayar.setEnabled(true);
            btnCetak.setEnabled(false);
        }
    }

    public void bayar(View view) {

        String ambilBulan = (getCurrentDate()).substring(6,7);
        mApiService.tagihanDetailRequest(
                sharedPrefManager.getSpIdkolektor(),
                idTagihan,
                idPel,
                getCurrentDate(),
                ambilBulan,
                Integer.parseInt(nominal)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    btnCetak.setEnabled(true);
                    btnBayar.setEnabled(false);

                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        String id = jsonRESULTS.getString("status");
                        Toast.makeText(getApplicationContext(), "Pembayaran Berhasil", Toast.LENGTH_SHORT).show();
                        txtStatus.setText("Lunas");
                        txtStatus.setTextColor(getResources().getColor(R.color.colorPrimary));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        String id = jsonRESULTS.getString("status");
                        Toast.makeText(getApplicationContext(), ""+response.code(), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("TAG", ""+ response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("TAG", ""+ t);
            }
        });
    }

    private String getCurrentDate() {
        Date current = new Date();
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = frmt.format(current);
        return dateString;
    }

    public void cetak(View view) {
        Toast.makeText(getApplicationContext(), "Fitur Dalam Tahap Pengembangan", Toast.LENGTH_SHORT).show();
        printBill();
    }

    protected void printBill() {
        if(btsocket == null){
            Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);
        }
        else{
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream = btsocket.getOutputStream();
                byte[] printformat = new byte[]{0x1B,0x21,0x03};
                outputStream.write(printformat);


                printCustom("PT. IRAMA MITRA",2,1);
                printCustom("Padang",0,1);
                printPhoto(R.drawable.ic_logo);
//                printCustom("H-123, R-123, Dhanmondi, Dhaka-1212",0,1);
//                printCustom("Hot Line: +88000 000000",0,1);
//                printCustom("Vat Reg : 0000000000,Mushak : 11",0,1);
                String dateTime[] = getDateTime();
                printText(leftRightAlign(dateTime[0], dateTime[1]));
                printText(leftRightAlign("ID Pelanggan" , idPel));
                printText(leftRightAlign("Nama pelanggan" , nama));
                printText(leftRightAlign("Bulan", bulan));
//                printCustom(new String(new char[32]).replace("\0", "."),0,1);
                printText(leftRightAlign("Total Bayar" , nominal));
                printNewLine();
                printCustom("Terimakasih Sudah Berlangganan",0,1);
//                printCustom("forward to serve you again",0,1);
                printNewLine();
                printNewLine();

                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //print custom
    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B,0x21,0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text
        try {
            switch (size){
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
            }

            switch (align){
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print photo
    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    img);
            if(bmp!=null){
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            }else{
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    //print unicode
    public void printUnicode(){
        try {
            outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(Utils.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //print new line
    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void resetPrint() {
        try{
            outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
            outputStream.write(PrinterCommands.FS_FONT_ALIGN);
            outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            outputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
            outputStream.write(PrinterCommands.LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String leftRightAlign(String str1, String str2) {
        String ans = str1 +str2;
        if(ans.length() <31){
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }


    private String[] getDateTime() {
        final Calendar c = Calendar.getInstance();
        String dateTime [] = new String[2];
        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) +"/"+ c.get(Calendar.MONTH) +"/"+ c.get(Calendar.YEAR);
        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) +":"+ c.get(Calendar.MINUTE);
        return dateTime;
    }

}
