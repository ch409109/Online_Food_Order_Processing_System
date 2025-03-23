package com.example.prm392_final_project.ui.activity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_final_project.R;
import com.example.prm392_final_project.data.model.Voucher;
import com.example.prm392_final_project.data.repository.VoucherRepository;

public class DeleteVoucherActivity extends AppCompatActivity {
    private TextView txtVoucherCode, txtVoucherDiscount, txtExpirationDate;
    private Button btnDelete;
    private ImageButton btnBack;
    private VoucherRepository voucherRepository;
    private int voucherId;
    private Voucher voucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_voucher);

        voucherRepository = new VoucherRepository(this);

        voucherId = getIntent().getIntExtra("voucher_id", -1);
        if (voucherId == -1) {
            Toast.makeText(this, "Không tìm thấy voucher", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();

        loadVoucherData();

        setupListeners();
    }

    private void setupListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa voucher này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    deleteVoucher();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void deleteVoucher() {
        int result = voucherRepository.deleteVoucher(voucherId);
        if (result > 0) {
            Toast.makeText(this, "Xóa voucher thành công", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Không thể xóa voucher", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadVoucherData() {
        Cursor cursor = voucherRepository.getVoucherById(voucherId);
        if (cursor != null && cursor.moveToFirst()) {
            voucher = voucherRepository.cursorToVoucher(cursor);

            txtVoucherCode.setText("Mã: " + voucher.getCode());

            txtVoucherDiscount.setText("Giảm giá " + voucher.getDiscountPercentage() + ".000đ");

            String expirationDate = voucher.getExpirationDate();
            if (expirationDate != null && expirationDate.contains(" ")) {
                expirationDate = expirationDate.split(" ")[0];
                String[] parts = expirationDate.split("-");
                if (parts.length == 3) {
                    expirationDate = parts[2] + "/" + parts[1] + "/" + parts[0];
                }
            }
            txtExpirationDate.setText("Hết hạn: " + expirationDate);

            cursor.close();
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin voucher", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initViews() {
        txtVoucherCode = findViewById(R.id.txt_voucher_code);
        txtVoucherDiscount = findViewById(R.id.txt_voucher_discount);
        txtExpirationDate = findViewById(R.id.txt_expiration_date);
        btnDelete = findViewById(R.id.btn_delete);
        btnBack = findViewById(R.id.btn_back);
    }
}