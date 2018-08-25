package com.si.ordermanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.si.ordermanagement.adapter.OrderAdapter;
import com.si.ordermanagement.model.SeparatorDecoration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewOrderActivity extends AppCompatActivity {
    private RecyclerView rvOrder;
    private OrderAdapter orderAdapter;
    private ImageButton imgBtnWhatsapp,
            imgBtnShareMore;
    private TextView tvDate;
    private File finalImagePath;
    private Toolbar toolbar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        bindView();
        init();
        setAdapters();
        addListner();
        showProgressDialog();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new SaveImageAsync().execute();
            }
        }, 1500);

    }

    private void bindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("View Order");
        setSupportActionBar(toolbar);
        rvOrder = (RecyclerView) findViewById(R.id.rvOrder);
        imgBtnWhatsapp = (ImageButton) findViewById(R.id.imgBtnWhatsapp);
        imgBtnShareMore = (ImageButton) findViewById(R.id.imgBtnShareMore);
        tvDate = (TextView) findViewById(R.id.tvDate);
    }


    private void init() {
        tvDate.setText("Order Date : " + getCurrentDate());
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        SeparatorDecoration decoration = new SeparatorDecoration(this, Color.GRAY, 1.5f);
        rvOrder.addItemDecoration(decoration);
    }

    private void setAdapters() {
        Log.d("DATA", "==" + MyApplication.getInstance().getDB().orderDao().getAllOrder().size());
        orderAdapter = new OrderAdapter(ViewOrderActivity.this, MyApplication.getInstance().getDB().orderDao().getAllOrder());
        rvOrder.setAdapter(orderAdapter);
    }

    private void addListner() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imgBtnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatsappShare("com.whatsapp", "Whatsapp");
            }
        });

        imgBtnShareMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });
    }

    private void takeScreenShot() {

        NestedScrollView iv = (NestedScrollView) findViewById(R.id.scroll);
        Bitmap bitmap = Bitmap.createBitmap(
                iv.getChildAt(0).getWidth() * 2,
                iv.getChildAt(0).getHeight() * 2,
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        c.scale(2.0f, 2.0f);
        c.drawColor(getResources().getColor(R.color.colorWhite));
        iv.getChildAt(0).draw(c);
        // Do whatever you want with your bitmap
        saveBitmap(bitmap);

    }

    public void saveBitmap(Bitmap bitmap) {
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Amul Order/");

        if (!folder.exists()) {
            boolean success = folder.mkdir();
        }
        String path = folder.getAbsolutePath();
        String fileName = "Amul_order_" + System.currentTimeMillis() + ".jpg";// path where pdf will be stored


        File myPath = new File(folder, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            finalImagePath = myPath;

        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }


    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }


    private void whatsappShare(String packgeName, String AppName) {
        Intent shareIntent = new Intent("android.intent.action.SEND");
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra("android.intent.extra.STREAM",
                Uri.fromFile(finalImagePath));
        try {
            shareIntent.setPackage(packgeName);
            startActivity(shareIntent);
            return;
        } catch (Exception e) {
            Toast.makeText(ViewOrderActivity.this, AppName + " doesn't installed",
                    Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void share() {

        Intent shareIntent = new Intent("android.intent.action.SEND");
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra("android.intent.extra.STREAM",
                Uri.fromFile(finalImagePath));
        startActivity(Intent.createChooser(shareIntent, "Share Order"));
    }


    public class SaveImageAsync extends AsyncTask<Void, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            takeScreenShot();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
        }
    }


    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(ViewOrderActivity.this);
        mProgressDialog.setMessage("Please wait");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }


}
