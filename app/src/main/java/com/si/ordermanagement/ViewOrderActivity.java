package com.si.ordermanagement;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.si.ordermanagement.adapter.OrderAdapter;
import com.si.ordermanagement.model.SeparatorDecoration;

public class ViewOrderActivity extends AppCompatActivity {
    private RecyclerView rvOrder;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        bindView();
        init();
        setAdapters();
        addListner();

    }

    private void bindView() {
        rvOrder = (RecyclerView) findViewById(R.id.rvOrder);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        SeparatorDecoration decoration = new SeparatorDecoration(this, Color.GRAY, 1.5f);
        rvOrder.addItemDecoration(decoration);
    }

    private void init() {

    }

    private void setAdapters() {
        orderAdapter=new OrderAdapter(ViewOrderActivity.this, MyApplication.getInstance().getDB().orderDao().getAllOrder());
        rvOrder.setAdapter(orderAdapter);
    }

    private void addListner() {

    }
}
