package com.example.prm392_final_project.ui.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_final_project.R;
import com.example.prm392_final_project.data.model.Voucher;

import java.util.List;

public class PromotionNotificationAdapter extends RecyclerView.Adapter<PromotionNotificationAdapter.VoucherViewHolder>{
    private List<Voucher> voucherList;
    private Context context;

    public PromotionNotificationAdapter(List<Voucher> voucherList, Context context) {
        this.voucherList = voucherList;
        this.context = context;
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_promotion_voucher, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Voucher voucher = voucherList.get(position);

        holder.voucherCode.setText("Voucher mới: " + voucher.getCode());

        holder.voucherDiscount.setText("Giảm giá " + formatMoney(voucher.getDiscountPercentage()) + "%");

        holder.expirationDate.setText("Hết hạn: " + formatDate(voucher.getExpirationDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyVoucherToClipboard(voucher.getCode());
            }
        });
    }

    private void copyVoucherToClipboard(String voucherCode) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Voucher Code", voucherCode);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context, "Đã sao chép mã voucher: " + voucherCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    private String formatMoney(int amount) {
        return String.format("%,d", amount).replace(",", ".");
    }

    private String formatDate(String date) {
        if (date != null && date.contains(" ")) {
            date = date.split(" ")[0];
            String[] parts = date.split("-");
            if (parts.length == 3) {
                return parts[2] + "/" + parts[1] + "/" + parts[0];
            }
        }
        return date;
    }

    public static class VoucherViewHolder extends RecyclerView.ViewHolder {
        TextView voucherCode, voucherDiscount, expirationDate;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            voucherCode = itemView.findViewById(R.id.txt_voucher_code);
            voucherDiscount = itemView.findViewById(R.id.txt_voucher_discount);
            expirationDate = itemView.findViewById(R.id.txt_expiration_date);
        }
    }
}
