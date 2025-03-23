package com.example.prm392_final_project.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.prm392_final_project.data.database.DatabaseHelper;
import com.example.prm392_final_project.data.model.Voucher;

public class VoucherRepository {
    private DatabaseHelper dbHelper;

    public VoucherRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertVoucher(String code, int discountPercentage, String expirationDate, int createdBy) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Code", code);
        values.put("DiscountPercentage", discountPercentage);
        values.put("ExpirationDate", expirationDate);
        values.put("CreatedBy", createdBy);
        long id = db.insert("Vouchers", null, values);
        db.close();
        return id;
    }

    public Cursor getAllVouchers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Vouchers", null);
    }

    public Cursor getVoucherById(int voucherId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM Vouchers WHERE Id = ?";
        return db.rawQuery(query, new String[]{String.valueOf(voucherId)});
    }

    public int updateVoucher(int voucherId, String code, double discountPercentage, String expirationDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Code", code);
        values.put("DiscountPercentage", discountPercentage);
        values.put("ExpirationDate", expirationDate);
        values.put("UpdatedAt", getCurrentTimestamp());
        int rowsAffected = db.update("Vouchers", values, "Id = ?", new String[]{String.valueOf(voucherId)});
        db.close();
        return rowsAffected;
    }

    public int deleteVoucher(int voucherId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.delete("Vouchers", "Id = ?", new String[]{String.valueOf(voucherId)});
        db.close();
        return rowsAffected;
    }

    public Cursor searchVouchers(String searchQuery) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query;
        String[] selectionArgs;
        if (isNumeric(searchQuery)) {
            int discount = Integer.parseInt(searchQuery);
            query = "SELECT * FROM Vouchers WHERE Code LIKE ? OR DiscountPercentage = ?";
            selectionArgs = new String[]{"%" + searchQuery + "%", String.valueOf(discount)};
        } else {
            query = "SELECT * FROM Vouchers WHERE Code LIKE ?";
            selectionArgs = new String[]{"%" + searchQuery + "%"};
        }
        return db.rawQuery(query, selectionArgs);
    }

    public boolean isVoucherCodeExists(String code, int excludeVoucherId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM Vouchers WHERE Code = ? AND Id != ?";
        Cursor cursor = db.rawQuery(query, new String[]{code, String.valueOf(excludeVoucherId)});
        boolean exists = false;
        if (cursor != null && cursor.moveToFirst()) {
            exists = cursor.getInt(0) > 0;
            cursor.close();
        }
        db.close();
        return exists;
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

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String getCurrentTimestamp() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }
}
