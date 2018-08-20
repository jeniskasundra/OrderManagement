package com.si.ordermanagement;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    private String pName = "", pDis = "", pQty = "";
    private int orderCount = 0;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        orderCount = 0;
        pName = "";
        pDis = "";
        pQty = "";
        edtQuantity.setText("");
        tvMsg.setText("");
        MyApplication.getInstance().getDB().orderDao().deleteTable();
        init();
        setAdapters();
        addListner();
    }

    private void init() {
        spnName.setPadding(10, 20, 40, 20);
        spnDis.setPadding(10, 20, 40, 20);
        mListProductData = new ArrayList<>();
        Log.d("data", "" + getJsonString());
        productModel = new Gson().fromJson(getJsonString(), ProductModel.class);
        if (productModel != null) {
            if (productModel.getProduct().size() > 0) {
                mListProductData = productModel.getProduct();
            }
        }
    }

    private void bindView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Order Management");
        setSupportActionBar(toolbar);
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
                        hideSoftKeyboard(MainActivity.this);
                        tvMsg.setText("");
                        if (spnDis.getSelectedItem().toString().equals("--- Select ---")) {
                            tvMsg.setText("Please select description.");
                            tvMsg.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_light));
                            Toast.makeText(MainActivity.this, "Please select description", Toast.LENGTH_SHORT).show();
                        } else if (edtQuantity.getText().toString().equals("")) {
                            tvMsg.setText("Please enter quantity.");
                            tvMsg.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_light));
                            edtQuantity.setError("Please Enter Quantity");
                        } else {
                            addItemDialog();
                        }
                    }
                });

                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (orderCount > 0) {
                            generateOrderDialog();
                        } else {
                            Toast.makeText(MainActivity.this, "Please add item first", Toast.LENGTH_SHORT).show();
                        }
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

                pName = spnName.getSelectedItem().toString();
                pDis = spnDis.getSelectedItem().toString();
                pQty = edtQuantity.getText().toString();
                orderCount++;
                OrderData orderData = new OrderData();
                orderData.setId(orderCount);
                orderData.setPname(pName);
                orderData.setPdis(pDis);
                orderData.setQty(pQty);
                MyApplication.getInstance().getDB().orderDao().insertAll(orderData);


                Toast.makeText(MainActivity.this, "Item Add Successfully", Toast.LENGTH_SHORT).show();
                tvMsg.setText(orderCount + " Item added successfully.");
                tvMsg.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_green_dark));

                pName = "";
                pDis = "";
                pQty = "";
                edtQuantity.setText("");
                spnName.setSelection(0);
                spnDis.setSelection(0);


            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }


    private void generateOrderDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(
                MainActivity.this, R.style.AppAlertDialog);
        dialog.setTitle("Generate Order");
        dialog.setMessage("Are you sure you want to generate order?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(MainActivity.this, ViewOrderActivity.class));
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

    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
