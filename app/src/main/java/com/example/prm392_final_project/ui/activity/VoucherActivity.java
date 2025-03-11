//package com.example.prm392_final_project.ui.activity;
//
//import android.database.Cursor;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.prm392_final_project.R;
//import com.example.prm392_final_project.data.model.Voucher;
//import com.example.prm392_final_project.data.repository.VoucherRepository;
////import com.example.prm392_final_project.ui.adapter.VoucherAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class VoucherActivity extends AppCompatActivity  implements VoucherAdapter.OnItemClickListener {
//    private VoucherRepository voucherRepository;
//    private VoucherAdapter voucherAdapter;
//    private RecyclerView recyclerView;
//    private List<Voucher> voucherList;
//    private EditText etCode, etDiscount, etExpiration, etSearch;
//    private Button btnAdd, btnUpdate, btnDelete, btnSearch;
//    private Voucher selectedVoucher;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_voucher);
//
//        voucherRepository = new VoucherRepository(this);
//        recyclerView = findViewById(R.id.rv_vouchers);
//        etCode = findViewById(R.id.et_voucher_code);
//        etDiscount = findViewById(R.id.et_voucher_discount);
//        etExpiration = findViewById(R.id.et_voucher_expiration);
//        etSearch = findViewById(R.id.et_search_voucher);
//        btnAdd = findViewById(R.id.btn_add_voucher);
//        btnUpdate = findViewById(R.id.btn_update_voucher);
//        btnDelete = findViewById(R.id.btn_delete_voucher);
//        btnSearch = findViewById(R.id.btn_search_voucher);
//
//        voucherList = new ArrayList<>();
//        voucherAdapter = new VoucherAdapter(voucherList, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(voucherAdapter);
//
//        loadVouchers();
//
//        btnAdd.setOnClickListener(v -> addVoucher());
//        btnUpdate.setOnClickListener(v -> updateVoucher());
//        btnDelete.setOnClickListener(v -> deleteVoucher());
//        btnSearch.setOnClickListener(v -> searchVouchers());
//    }
//
//    private void loadVouchers() {
//        Cursor cursor = voucherRepository.getAllVouchers();
//        voucherList.clear();
//        while (cursor.moveToNext()) {
//            Voucher voucher = voucherRepository.cursorToVoucher(cursor);
//            voucherList.add(voucher);
//        }
//        cursor.close();
//        voucherAdapter.updateVouchers(voucherList);
//    }
//
//    private void addVoucher() {
//        String code = etCode.getText().toString().trim();
//        String discountStr = etDiscount.getText().toString().trim();
//        String expiration = etExpiration.getText().toString().trim();
//        if (!code.isEmpty() && !discountStr.isEmpty() && !expiration.isEmpty()) {
//            int discount = Integer.parseInt(discountStr);
//            long id = voucherRepository.insertVoucher(code, discount, expiration, 1); // Giả định createdBy = 1
//            if (id != -1) {
//                Toast.makeText(this, "Voucher added", Toast.LENGTH_SHORT).show();
//                loadVouchers();
//                clearInputs();
//            } else {
//                Toast.makeText(this, "Failed to add voucher", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private void updateVoucher() {
//        if (selectedVoucher != null) {
//            String code = etCode.getText().toString().trim();
//            String discountStr = etDiscount.getText().toString().trim();
//            String expiration = etExpiration.getText().toString().trim();
//            if (!code.isEmpty() && !discountStr.isEmpty() && !expiration.isEmpty()) {
//                int discount = Integer.parseInt(discountStr);
//                int rowsAffected = voucherRepository.updateVoucher(selectedVoucher.getId(), code, discount, expiration);
//                if (rowsAffected > 0) {
//                    Toast.makeText(this, "Voucher updated", Toast.LENGTH_SHORT).show();
//                    loadVouchers();
//                    clearInputs();
//                    selectedVoucher = null;
//                } else {
//                    Toast.makeText(this, "Failed to update voucher", Toast.LENGTH_SHORT).show();
//                }
//            }
//        } else {
//            Toast.makeText(this, "Please select a voucher to update", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void deleteVoucher() {
//        if (selectedVoucher != null) {
//            int rowsAffected = voucherRepository.deleteVoucher(selectedVoucher.getId());
//            if (rowsAffected > 0) {
//                Toast.makeText(this, "Voucher deleted", Toast.LENGTH_SHORT).show();
//                loadVouchers();
//                clearInputs();
//                selectedVoucher = null;
//            } else {
//                Toast.makeText(this, "Failed to delete voucher", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(this, "Please select a voucher to delete", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void searchVouchers() {
//        String searchQuery = etSearch.getText().toString().trim();
//        Cursor cursor = voucherRepository.searchVouchers(searchQuery);
//        voucherList.clear();
//        while (cursor.moveToNext()) {
//            Voucher voucher = voucherRepository.cursorToVoucher(cursor);
//            voucherList.add(voucher);
//        }
//        cursor.close();
//        voucherAdapter.updateVouchers(voucherList);
//    }
//
//    private void clearInputs() {
//        etCode.setText("");
//        etDiscount.setText("");
//        etExpiration.setText("");
//    }
//
//    @Override
//    public void OnItemClick(Voucher voucher) {
//        selectedVoucher = voucher;
//        etCode.setText(voucher.getCode());
//        etDiscount.setText(String.valueOf(voucher.getDiscountPercentage()));
//        etExpiration.setText(voucher.getExpirationDate());
//    }
//}