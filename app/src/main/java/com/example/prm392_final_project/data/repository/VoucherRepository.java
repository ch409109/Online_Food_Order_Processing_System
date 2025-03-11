package com.example.prm392_final_project.data.repository;

import android.content.Context;
import android.database.Cursor;

import com.example.prm392_final_project.data.database.DatabaseHelper;
import com.example.prm392_final_project.data.model.Voucher;

public class VoucherRepository {
    private DatabaseHelper dbHelper;

    public VoucherRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertVoucher(String code, int discountPercentage, String expirationDate, int createdBy) {
        return dbHelper.insertVoucher(code, discountPercentage, expirationDate, createdBy);
    }

    public Cursor getAllVouchers() {
        return dbHelper.getAllVouchers();
    }

    public Cursor getVoucherById(int voucherId) {
        return dbHelper.getVoucherById(voucherId);
    }

    public int updateVoucher(int voucherId, String code, double discountPercentage, String expirationDate) {
        return dbHelper.updateVoucher(voucherId, code, discountPercentage, expirationDate);
    }

    public int deleteVoucher(int voucherId) {
        return dbHelper.deleteVoucher(voucherId);
    }

    public Cursor searchVouchers(String searchQuery) {
        return dbHelper.searchVouchers(searchQuery);
    }

    public Voucher cursorToVoucher(Cursor cursor) {
        return new Voucher(
                cursor.getInt(cursor.getColumnIndexOrThrow("Id")),
                cursor.getString(cursor.getColumnIndexOrThrow("Code")),
                cursor.getInt(cursor.getColumnIndexOrThrow("DiscountPercentage")),
                cursor.getString(cursor.getColumnIndexOrThrow("ExpirationDate")),
                cursor.getInt(cursor.getColumnIndexOrThrow("CreatedBy")),
                cursor.getString(cursor.getColumnIndexOrThrow("CreatedAt")),
                cursor.getString(cursor.getColumnIndexOrThrow("UpdatedAt"))
        );
    }
}
