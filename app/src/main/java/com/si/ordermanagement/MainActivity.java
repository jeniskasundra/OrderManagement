package com.si.ordermanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.si.ordermanagement.model.Product;
import com.si.ordermanagement.model.ProductModel;

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
    private Spinner spnName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        bindView();
        addListner();
    }


    private void init() {
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
        spnName= (Spinner) findViewById(R.id.spnName);
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.row_item_spinner, mListProductData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnName.setAdapter(adapter);
    }

    private void addListner() {
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
