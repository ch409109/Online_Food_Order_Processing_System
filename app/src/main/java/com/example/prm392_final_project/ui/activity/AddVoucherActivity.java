package com.example.prm392_final_project.ui.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_final_project.R;
import com.example.prm392_final_project.data.repository.VoucherRepository;

import java.util.Calendar;

public class AddVoucherActivity extends AppCompatActivity {
    private VoucherRepository voucherRepository;
    private EditText etVoucherCode, etDiscountValue, etExpirationDate;
    private Button btnSave;

    private static final int MAX_VOUCHER_CODE_LENGTH = 20;
    private static final int MAX_DISCOUNT_VALUE_LENGTH = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voucher);

        voucherRepository = new VoucherRepository(this);

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        etVoucherCode = findViewById(R.id.et_voucher_code);
        etDiscountValue = findViewById(R.id.et_discount_value);
        etExpirationDate = findViewById(R.id.et_expiration_date);
        btnSave = findViewById(R.id.btn_save);

        etExpirationDate.setKeyListener(null);
        etExpirationDate.setOnClickListener(v -> showDatePickerDialog());

        btnSave.setOnClickListener(v -> saveVoucher());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = selectedYear + "-" +
                            String.format("%02d", selectedMonth + 1) + "-" +
                            String.format("%02d", selectedDay);
                    etExpirationDate.setText(formattedDate);
                },
                year, month, day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void saveVoucher() {
        String code = etVoucherCode.getText().toString().trim();
        String discountStr = etDiscountValue.getText().toString().trim();
        String expirationDate = etExpirationDate.getText().toString().trim();

        if (code.isEmpty() || discountStr.isEmpty() || expirationDate.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (code.length() > MAX_VOUCHER_CODE_LENGTH) {
            Toast.makeText(this, "Mã voucher không được dài quá " + MAX_VOUCHER_CODE_LENGTH + " ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (discountStr.length() > MAX_DISCOUNT_VALUE_LENGTH) {
            Toast.makeText(this, "Giá trị giảm không được dài quá " + MAX_DISCOUNT_VALUE_LENGTH + " ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int discountPercentage = Integer.parseInt(discountStr);
            String formattedExpirationDate = expirationDate + " 23:59:59";

            if (voucherRepository.isVoucherCodeExists(code, -1)) {
                Toast.makeText(this, "Mã voucher đã tồn tại, vui lòng chọn mã khác", Toast.LENGTH_SHORT).show();
                return;
            }

            int currentUserId = 1;

            long result = voucherRepository.insertVoucher(
                    code,
                    discountPercentage,
                    formattedExpirationDate,
                    currentUserId);

            if (result > 0) {
                Toast.makeText(this, "Voucher đã được thêm thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Thêm voucher không thành công", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá trị giảm không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}