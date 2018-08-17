package com.si.ordermanagement;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.si.ordermanagement.model.OrderData;
import com.si.ordermanagement.model.Product;
import com.si.ordermanagement.model.ProductModel;
import com.si.ordermanagement.permission.MarshMallowPermission;
import com.si.ordermanagement.permission.MarshMallowPermissionListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ProductModel productModel;
    private ArrayList<Product> mListProductData;
    private Spinner spnName, spnDis;
    private TextView tvMsg;
    private EditText edtQuantity;
    private Button btnAddItem, btnNext;
    private ArrayAdapter adapterSpnName, adapterSpnDis;
    private String pName="",pDis="",pQty="";
    private int orderCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        bindView();
        init();
        setAdapters();
        addListner();
    }

    private void checkPermission()
    {
        MarshMallowPermissionListener permissionlistener = new MarshMallowPermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "MarshMallowPermission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        new MarshMallowPermission(MainActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [MarshMallowPermission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void init() {
        spnName.setPadding(0,10,40,10);
        spnDis.setPadding(0,10,40,10);
        mListProductData = new ArrayList<>();
        Log.d("data", "" + getJsonString());
        productModel = new Gson().fromJson(getJsonString(), ProductModel.class);
        if (productModel != null) {
            if (productModel.getProduct().size() > 0) {
                mListProductData = productModel.getProduct();
            }
        }

        for (int i = 0; i < mListProductData.size(); i++) {
            Log.d("listData", mListProductData.get(i).getName());
        }
    }

    private void bindView() {
        spnName = (Spinner) findViewById(R.id.spnName);
        spnDis = (Spinner) findViewById(R.id.spnDis);
        edtQuantity = (EditText) findViewById(R.id.editQuantity);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        btnAddItem = (Button) findViewById(R.id.btnAddItem);
        btnNext = (Button) findViewById(R.id.btnNext);
    }


    private void setAdapters() {
        adapterSpnName = new ArrayAdapter(this, R.layout.row_item_spinner, mListProductData);
        adapterSpnName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnName.setAdapter(adapterSpnName);

        adapterSpnDis = new ArrayAdapter(this, R.layout.row_item_spinner, mListProductData.get(0).getDis());
        adapterSpnDis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDis.setAdapter(adapterSpnDis);
    }

    private void addListner() {


        spnName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mListProductData.get(i).getId().equals("0")) {
                    spnDis.setEnabled(false);
                    edtQuantity.setEnabled(false);
                    btnAddItem.setEnabled(false);

                    adapterSpnDis = new ArrayAdapter(MainActivity.this, R.layout.row_item_spinner, mListProductData.get(i).getDis());
                    adapterSpnDis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnDis.setAdapter(adapterSpnDis);

                } else {
                    spnDis.setEnabled(true);
                    edtQuantity.setEnabled(true);
                    btnAddItem.setEnabled(true);

                    adapterSpnDis = new ArrayAdapter(MainActivity.this, R.layout.row_item_spinner, mListProductData.get(i).getDis());
                    adapterSpnDis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnDis.setAdapter(adapterSpnDis);

                }

                btnAddItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edtQuantity.getText().toString().equals("")) {
                            tvMsg.setText("Please Enter Quantity");
                            tvMsg.setTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.holo_red_light));
                            edtQuantity.setError("Please Enter Quantity");
                        } else {
                            addItemDialog();
                        }
                    }
                });

                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this,ViewOrderActivity.class));
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void addItemDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(
                MainActivity.this, R.style.AppAlertDialog);
        dialog.setTitle("Add Item");
        dialog.setMessage("Are you sure you want to add this item?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                    pName=spnName.getSelectedItem().toString();
                    pDis=spnDis.getSelectedItem().toString();
                    pQty=edtQuantity.getText().toString();

                    OrderData orderData=new OrderData();
                    orderData.setPname(pName);
                    orderData.setPdis(pDis);
                    orderData.setQty(pQty);

                    orderCount++;

                    Toast.makeText(MainActivity.this, "Item Add Successfully", Toast.LENGTH_SHORT).show();
                    tvMsg.setText(orderCount+" Item Add Successfully");
                    tvMsg.setTextColor(ContextCompat.getColor(MainActivity.this,android.R.color.holo_green_dark));

            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }


    private String getJsonString() {
        InputStream is = getResources().openRawResource(R.raw.product);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }
}
