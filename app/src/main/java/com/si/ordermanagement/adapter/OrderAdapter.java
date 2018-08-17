package com.si.ordermanagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.si.ordermanagement.R;
import com.si.ordermanagement.model.OrderData;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<OrderData> orderDataList;
    private Activity activity;

    public OrderAdapter(Activity activity, List<OrderData> orderDataList) {
        this.activity = activity;
        this.orderDataList = orderDataList;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_list_order, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder viewHolder, int i) {
        final OrderData orderData = orderDataList.get(i);
        viewHolder.tvPNo.setText(orderData.getId());
        viewHolder.tvPName.setText(orderData.getPname());
        viewHolder.tvPDis.setText(orderData.getPdis());
        viewHolder.tvPQty.setText(orderData.getQty());
    }


    @Override
    public int getItemCount() {
        return orderDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPNo, tvPName, tvPDis, tvPQty;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPNo = (TextView) itemView.findViewById(R.id.tvNo);
            tvPName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvPDis = (TextView) itemView.findViewById(R.id.tvDis);
            tvPQty = (TextView) itemView.findViewById(R.id.tvQty);
        }
    }
}
