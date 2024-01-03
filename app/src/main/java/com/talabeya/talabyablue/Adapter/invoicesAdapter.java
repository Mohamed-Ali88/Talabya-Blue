package com.talabeya.talabyablue.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.talabeya.talabyablue.Activities.ShowInvoicesData;
import com.talabeya.talabyablue.Activities.ShowUserData;
import com.talabeya.talabyablue.Domain.invoicesDomain;
import com.talabeya.talabyablue.R;

import java.util.ArrayList;

public class invoicesAdapter extends RecyclerView.Adapter<invoicesAdapter.ViewHolder> {

    private ArrayList<invoicesDomain> InvoicesDomain;

    public invoicesAdapter(ArrayList<invoicesDomain> InvoicesDomain) {
        this.InvoicesDomain = InvoicesDomain;
    }

    @NonNull
    @Override
    public invoicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.veiwholder_invoices, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull invoicesAdapter.ViewHolder holder, int position) {
        invoicesDomain InvoicesDate = InvoicesDomain.get(position);
        holder.orderId.setText(InvoicesDate.getOrder_id());
        holder.orderPrice.setText(String.valueOf(InvoicesDate.getTotal_order_price()));
        holder.orderAgalAmount.setText(String.valueOf(InvoicesDate.getAgalValueAmount()));
        holder.orderState.setText(InvoicesDate.getOrder_state());
        holder.orderDate.setText(InvoicesDate.getRequest_date());
        holder.checkedData.setText(InvoicesDate.getChecked_date());
        holder.storeName.setText(InvoicesDate.getStoreName());
        holder.clientName.setText(InvoicesDate.getClient());
        holder.mainLin.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), ShowInvoicesData.class);
            intent.putExtra("object", InvoicesDomain.get(position));
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return InvoicesDomain.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, clientName, storeName, checkedData, orderDate, orderState, orderPrice, orderAgalAmount;
        LinearLayout mainLin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            mainLin = itemView.findViewById(R.id.mainLin);
            clientName = itemView.findViewById(R.id.client);
            orderState = itemView.findViewById(R.id.order_state);
            storeName = itemView.findViewById(R.id.storeName);
            orderPrice = itemView.findViewById(R.id.total_order_price);
            checkedData = itemView.findViewById(R.id.checked_date);
            orderAgalAmount = itemView.findViewById(R.id.agalValueAmount);
            orderDate = itemView.findViewById(R.id.request_date);
        }
    }

}
