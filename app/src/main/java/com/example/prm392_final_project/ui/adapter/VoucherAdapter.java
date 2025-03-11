//package com.example.prm392_final_project.ui.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.prm392_final_project.R;
//import com.example.prm392_final_project.data.model.Voucher;
//
//import java.util.List;
//
//public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
//    private List<Voucher> voucherList;
//    private AdapterView.OnItemClickListener listener;
//
//    public VoucherAdapter(List<Voucher> voucherList, AdapterView.OnItemClickListener listener){
//        this.voucherList = voucherList;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher, parent, false);
//        return new VoucherViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull VoucherAdapter.VoucherViewHolder holder, int position) {
//        Voucher voucher = voucherList.get(position);
//        holder.codeTextView.setText(voucher.getCode());
//        holder.discountTextView.setText(String.format("%d%%", voucher.getDiscountPercentage()));
//        holder.expirationDateTextView.setText(voucher.getExpirationDate());
//        holder.itemView.setOnClickListener(v -> listener.onItemClick(voucher));
//    }
//
//    @Override
//    public int getItemCount() {
//        return voucherList.size();
//    }
//
//    public void updateVouchers(List<Voucher> newVoucherList) {
//        voucherList.clear();
//        voucherList.addAll(newVoucherList);
//        notifyDataSetChanged();
//    }
//
//    static class VoucherViewHolder extends RecyclerView.ViewHolder {
//        TextView codeTextView, discountTextView, expirationDateTextView;
//
//        public VoucherViewHolder(@NonNull View itemView) {
//            super(itemView);
////            codeTextView = itemView.findViewById(R.id.codeTextView);
////            discountTextView = itemView.findViewById(R.id.discountTextView);
////            expirationDateTextView = itemView.findViewById(R.id.expirationDateTextView);
//        }
//    }
//
//    public interface OnItemClickListener {
//        void OnItemClick(Voucher voucher);
//    }
//}
