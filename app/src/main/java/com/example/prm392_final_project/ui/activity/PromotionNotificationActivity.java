package com.example.prm392_final_project.ui.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_final_project.R;
import com.example.prm392_final_project.data.model.Voucher;
import com.example.prm392_final_project.data.repository.VoucherRepository;
import com.example.prm392_final_project.ui.adapter.PromotionNotificationAdapter;

import java.util.ArrayList;
import java.util.List;

public class PromotionNotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PromotionNotificationAdapter adapter;
    private VoucherRepository voucherRepository;
    private List<Voucher> voucherList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_notification);

        voucherRepository = new VoucherRepository(this);

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recycler_vouchers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadVouchers();
    }

    private void loadVouchers() {
        voucherList.clear();

        Cursor cursor = voucherRepository.getAllVouchers();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Voucher voucher = voucherRepository.cursorToVoucher(cursor);
                voucherList.add(voucher);
            } while (cursor.moveToNext());

            cursor.close();
        }

        adapter = new PromotionNotificationAdapter(voucherList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVouchers();
    }
}