package com.example.prm392_final_project.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

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
import com.example.prm392_final_project.ui.adapter.VoucherAdapter;

import java.util.ArrayList;
import java.util.List;

public class VoucherManagerActivity extends AppCompatActivity {
    private VoucherRepository voucherRepository;
    private RecyclerView recyclerView;
    private VoucherAdapter adapter;
    private List<Voucher> voucherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_manager);

        voucherRepository = new VoucherRepository(this);

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        Button addButton = findViewById(R.id.btn_add_voucher);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(VoucherManagerActivity.this, AddVoucherActivity.class);
            startActivity(intent);
        });

        ImageView searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {
            Intent intent = new Intent(VoucherManagerActivity.this, SearchVoucherActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.voucher_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        voucherList = new ArrayList<>();
        adapter = new VoucherAdapter(voucherList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVouchers();
    }

    private void loadVouchers() {
        voucherList.clear();
        Cursor cursor = voucherRepository.getAllVouchers();

        if(cursor != null && cursor.moveToFirst()) {
            do {
                Voucher voucher = voucherRepository.cursorToVoucher(cursor);
                voucherList.add(voucher);
            } while (cursor.moveToNext());
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }
}