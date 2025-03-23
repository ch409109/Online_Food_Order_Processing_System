package com.example.prm392_final_project.ui.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class SearchVoucherActivity extends AppCompatActivity {
    private VoucherRepository voucherRepository;
    private RecyclerView recyclerView;
    private VoucherAdapter adapter;
    private List<Voucher> voucherList;
    private EditText searchBar;
    private TextView resultsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_voucher);

        voucherRepository = new VoucherRepository(this);

        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        searchBar = findViewById(R.id.search_bar);
        resultsLabel = findViewById(R.id.results_label);
        recyclerView = findViewById(R.id.search_results_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        voucherList = new ArrayList<>();
        adapter = new VoucherAdapter(voucherList, this);
        recyclerView.setAdapter(adapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void performSearch(String query) {
        voucherList.clear();
        Cursor cursor = voucherRepository.searchVouchers(query);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Voucher voucher = voucherRepository.cursorToVoucher(cursor);
                voucherList.add(voucher);
            } while (cursor.moveToNext());
            cursor.close();
        }
        resultsLabel.setText("Tìm thấy " + voucherList.size() + " kết quả");
        adapter.notifyDataSetChanged();
    }
}