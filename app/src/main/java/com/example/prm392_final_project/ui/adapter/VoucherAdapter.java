package com.example.prm392_final_project.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_final_project.R;
import com.example.prm392_final_project.data.model.Voucher;
import com.example.prm392_final_project.data.repository.VoucherRepository;
import com.example.prm392_final_project.ui.activity.DeleteVoucherActivity;
import com.example.prm392_final_project.ui.activity.EditVoucherActivity;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    private List<Voucher> voucherList;
    private Context context;

    public VoucherAdapter(List<Voucher> voucherList, Context context) {
        this.voucherList = voucherList;
        this.context = context;
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voucher, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAdapter.VoucherViewHolder holder, int position) {
        Voucher voucher = voucherList.get(position);

        holder.voucherCode.setText(voucher.getCode());
        holder.voucherDiscount.setText("Giảm " + voucher.getDiscountPercentage() + "%");
        holder.expirationDate.setText("Hết hạn: " + formatDate(voucher.getExpirationDate()));

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditVoucherActivity.class);
            intent.putExtra("voucher_id", voucher.getId());
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            // Navigate to DeleteVoucherActivity instead of showing an alert dialog directly
            Intent intent = new Intent(context, DeleteVoucherActivity.class);
            intent.putExtra("voucher_id", voucher.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    private String formatDate(String date) {
        if (date != null && date.contains(" ")) {
            date = date.split(" ")[0];
            String[] parts = date.split("-");
            if (parts.length == 3) {
                date = parts[2] + "/" + parts[1] + "/" + parts[0];
            }
        }
        return date;
    }

    public static class VoucherViewHolder extends RecyclerView.ViewHolder {
        TextView voucherCode, voucherDiscount, expirationDate;
        Button editButton, deleteButton;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            voucherCode = itemView.findViewById(R.id.txt_voucher_code);
            voucherDiscount = itemView.findViewById(R.id.txt_voucher_discount);
            expirationDate = itemView.findViewById(R.id.txt_expiration_date);
            editButton = itemView.findViewById(R.id.btn_edit);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}
