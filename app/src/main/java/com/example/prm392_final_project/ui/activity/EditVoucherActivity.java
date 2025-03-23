package com.example.prm392_final_project.ui.activity;

import android.app.DatePickerDialog;
import android.database.Cursor;
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
import com.example.prm392_final_project.data.model.Voucher;
import com.example.prm392_final_project.data.repository.VoucherRepository;

import java.util.Calendar;

public class EditVoucherActivity extends AppCompatActivity {
    private VoucherRepository voucherRepository;
    private EditText etVoucherCode, etDiscountValue, etExpirationDate;
    private Button btnSave;
    private int voucherId;
    private Voucher currentVoucher;

    private static final int MAX_VOUCHER_CODE_LENGTH = 20;
    private static final int MAX_DISCOUNT_VALUE_LENGTH = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_voucher);

        voucherId = getIntent().getIntExtra("voucher_id", -1);
        if (voucherId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy voucher", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        voucherRepository = new VoucherRepository(this);

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        etVoucherCode = findViewById(R.id.et_voucher_code);
        etDiscountValue = findViewById(R.id.et_discount_value);
        etExpirationDate = findViewById(R.id.et_expiration_date);
        btnSave = findViewById(R.id.btn_save);

        etExpirationDate.setKeyListener(null);
        etExpirationDate.setOnClickListener(v -> showDatePickerDialog());

        btnSave.setOnClickListener(v -> updateVoucher());

        loadVoucherData();
    }

    private void loadVoucherData() {
        Cursor cursor = voucherRepository.getVoucherById(voucherId);

        if (cursor != null && cursor.moveToFirst()) {
            currentVoucher = voucherRepository.cursorToVoucher(cursor);
            cursor.close();

            etVoucherCode.setText(currentVoucher.getCode());
            etDiscountValue.setText(String.valueOf(currentVoucher.getDiscountPercentage()));

            String expirationDateOnly = currentVoucher.getExpirationDate().split(" ")[0];
            etExpirationDate.setText(expirationDateOnly);
        } else {
            Toast.makeText(this, "Không thể tải thông tin voucher", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();

        String currentDate = etExpirationDate.getText().toString();
        if (!currentDate.isEmpty()) {
            String[] dateParts = currentDate.split("-");
            if (dateParts.length == 3) {
                calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[0]));
                calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1]) - 1);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[2]));
            }
        }

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

    private void updateVoucher() {
        String code = etVoucherCode.getText().toString().trim();
        String discountStr = etDiscountValue.getText().toString().trim();
        String expirationDate = etExpirationDate.getText().toString().trim();

        // Validation
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
            double discountPercentage = Double.parseDouble(discountStr);
            String formattedExpirationDate = expirationDate + " 23:59:59";

            if (voucherRepository.isVoucherCodeExists(code, voucherId)) {
                Toast.makeText(this, "Mã voucher đã tồn tại, vui lòng chọn mã khác", Toast.LENGTH_SHORT).show();
                return;
            }

            int result = voucherRepository.updateVoucher(
                    voucherId,
                    code,
                    discountPercentage,
                    formattedExpirationDate);

            if (result > 0) {
                Toast.makeText(this, "Voucher đã được cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Cập nhật voucher không thành công", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá trị giảm không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }

}