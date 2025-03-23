package com.example.prm392_final_project.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FoodApp.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true); 
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE Users (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Username TEXT UNIQUE," +
                "Email TEXT UNIQUE," +
                "PasswordHash TEXT," +
                "PhoneNumber TEXT UNIQUE," +
                "ProfilePicture TEXT," +
                "Address TEXT," +
                "Role TEXT DEFAULT 'customer'," +
                "CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_ORDERS_TABLE = "CREATE TABLE Orders (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "UserId INTEGER," +
                "Status TEXT," +
                "TotalPrice INTEGER," +
                "DeliveryAddress TEXT," +
                "PaymentMethod TEXT," +
                "CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (UserId) REFERENCES Users(Id)" +
                ")";
        db.execSQL(CREATE_ORDERS_TABLE);

        String CREATE_FOODS_TABLE = "CREATE TABLE Foods (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Description TEXT," +
                "Price INTEGER," +
                "Category TEXT," +
                "ImageUrl TEXT," +
                "CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(CREATE_FOODS_TABLE);

        String CREATE_ORDER_ITEMS_TABLE = "CREATE TABLE OrderItems (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "OrderId INTEGER," +
                "FoodId INTEGER," +
                "Quantity INTEGER," +
                "Price INTEGER," +
                "CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (OrderId) REFERENCES Orders(Id)," +
                "FOREIGN KEY (FoodId) REFERENCES Foods(Id)" +
                ")";
        db.execSQL(CREATE_ORDER_ITEMS_TABLE);

        String CREATE_VOUCHERS_TABLE = "CREATE TABLE Vouchers (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Code TEXT UNIQUE," +
                "DiscountPercentage INTEGER," +
                "ExpirationDate DATETIME," +
                "CreatedBy INTEGER," +
                "CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (CreatedBy) REFERENCES Users(Id)" +
                ")";
        db.execSQL(CREATE_VOUCHERS_TABLE);

        String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE Notifications (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Title TEXT," +
                "Message TEXT," +
                "CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
        db.execSQL(CREATE_NOTIFICATIONS_TABLE);

        String CREATE_REVIEWS_TABLE = "CREATE TABLE Reviews (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "UserId INTEGER," +
                "FoodId INTEGER," +
                "Rating INTEGER," +
                "Feedback TEXT," +
                "CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (UserId) REFERENCES Users(Id)," +
                "FOREIGN KEY (FoodId) REFERENCES Foods(Id)" +
                ")";
        db.execSQL(CREATE_REVIEWS_TABLE);

        String CREATE_CART_ITEMS_TABLE = "CREATE TABLE CartItems (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "UserId INTEGER," +
                "FoodId INTEGER," +
                "Quantity INTEGER," +
                "CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (UserId) REFERENCES Users(Id)," +
                "FOREIGN KEY (FoodId) REFERENCES Foods(Id)" +
                ")";
        db.execSQL(CREATE_CART_ITEMS_TABLE);

        String CREATE_OTPS_TABLE = "CREATE TABLE OTPs (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "UserId INTEGER," +
                "OtpCode TEXT," +
                "ExpiresAt DATETIME," +
                "IsUsed INTEGER DEFAULT 0," +
                "FOREIGN KEY (UserId) REFERENCES Users(Id)" +
                ")";
        db.execSQL(CREATE_OTPS_TABLE);

        seedSampleData(db);
    }

    private void seedSampleData(SQLiteDatabase db) {
        ContentValues user1 = new ContentValues();
        user1.put("Username", "conghthe172673");
        user1.put("Email", "conghthe172673@fpt.edu.vn");
        user1.put("PasswordHash", "123456");
        user1.put("PhoneNumber", "0976579959");
        user1.put("ProfilePicture", "url_to_johns_picture");
        user1.put("Address", "Phu Xuyen Town, Ha Noi");
        user1.put("Role", "admin");
        db.insert("Users", null, user1);

        ContentValues user2 = new ContentValues();
        user2.put("Username", "hailthe172555");
        user2.put("Email", "hailthe172555@fpt.edu.vn");
        user2.put("PasswordHash", "123456");
        user2.put("PhoneNumber", "0987654321");
        user2.put("ProfilePicture", "url_to_janes_picture");
        user2.put("Address", "Hung Ha, Thai Binh");
        user2.put("Role", "customer");
        db.insert("Users", null, user2);

        ContentValues user3 = new ContentValues();
        user3.put("Username", "haindhe173149");
        user3.put("Email", "haindhe173149@fpt.edu.vn");
        user3.put("PasswordHash", "123456");
        user3.put("PhoneNumber", "0123456789");
        user3.put("ProfilePicture", "url_to_janes_picture");
        user3.put("Address", "Vietnam");
        user3.put("Role", "admin");
        db.insert("Users", null, user3);

        ContentValues user4 = new ContentValues();
        user4.put("Username", "longpthe161426");
        user4.put("Email", "longpthe161426@fpt.edu.vn");
        user4.put("PasswordHash", "123456");
        user4.put("PhoneNumber", "0998877654");
        user4.put("ProfilePicture", "url_to_janes_picture");
        user4.put("Address", "Vietnam");
        user4.put("Role", "customer");
        db.insert("Users", null, user4);

        ContentValues food1 = new ContentValues();
        food1.put("Name", "Phở Bò");
        food1.put("Description", "Món phở truyền thống với thịt bò.");
        food1.put("Price", 50000);
        food1.put("Category", "Món chính");
        food1.put("ImageUrl", "url_to_pho_bo_image");
        db.insert("Foods", null, food1);

        ContentValues food2 = new ContentValues();
        food2.put("Name", "Bánh Mì Thịt Nướng");
        food2.put("Description", "Bánh mì kẹp thịt nướng đặc biệt.");
        food2.put("Price", 25000);
        food2.put("Category", "Món ăn nhanh");
        food2.put("ImageUrl", "url_to_banh_mi_image");
        db.insert("Foods", null, food2);

        ContentValues order1 = new ContentValues();
        order1.put("UserId", 1); // john_doe
        order1.put("Status", "Đang xử lý");
        order1.put("TotalPrice", 50000);
        order1.put("DeliveryAddress", "123 Main St, City A");
        order1.put("PaymentMethod", "Thanh toán khi nhận hàng");
        db.insert("Orders", null, order1);

        ContentValues order2 = new ContentValues();
        order2.put("UserId", 2); // admin_jane
        order2.put("Status", "Đã giao");
        order2.put("TotalPrice", 25000);
        order2.put("DeliveryAddress", "456 Admin Rd, City B");
        order2.put("PaymentMethod", "Thẻ tín dụng");
        db.insert("Orders", null, order2);

        ContentValues orderItem1 = new ContentValues();
        orderItem1.put("OrderId", 1);
        orderItem1.put("FoodId", 1); // Phở Bò
        orderItem1.put("Quantity", 1);
        orderItem1.put("Price", 50000);
        db.insert("OrderItems", null, orderItem1);

        ContentValues orderItem2 = new ContentValues();
        orderItem2.put("OrderId", 2);
        orderItem2.put("FoodId", 2); // Bánh Mì Thịt Nướng
        orderItem2.put("Quantity", 1);
        orderItem2.put("Price", 25000);
        db.insert("OrderItems", null, orderItem2);

        ContentValues voucher1 = new ContentValues();
        voucher1.put("Code", "DISCOUNT10");
        voucher1.put("DiscountPercentage", 10);
        voucher1.put("ExpirationDate", "2025-12-31 23:59:59");
        voucher1.put("CreatedBy", 2); // admin_jane
        db.insert("Vouchers", null, voucher1);

        ContentValues voucher2 = new ContentValues();
        voucher2.put("Code", "DISCOUNT20");
        voucher2.put("DiscountPercentage", 20);
        voucher2.put("ExpirationDate", "2025-12-31 23:59:59");
        voucher2.put("CreatedBy", 2); // admin_jane
        db.insert("Vouchers", null, voucher2);

        // 6. Seed bảng Notifications (tham chiếu UserId)
        ContentValues notification1 = new ContentValues();
        notification1.put("Title", "Chào mừng!");
        notification1.put("Message", "Cảm ơn bạn đã đăng ký.");
        db.insert("Notifications", null, notification1);

        ContentValues notification2 = new ContentValues();
        notification2.put("Title", "Khuyến mãi đặc biệt");
        notification2.put("Message", "Giảm giá 20% cho đơn hàng tiếp theo.");
        db.insert("Notifications", null, notification2);

        ContentValues review1 = new ContentValues();
        review1.put("UserId", 1); // john_doe
        review1.put("FoodId", 1); // Phở Bò
        review1.put("Rating", 5);
        review1.put("Feedback", "Rất ngon, sẽ quay lại!");
        db.insert("Reviews", null, review1);

        ContentValues review2 = new ContentValues();
        review2.put("UserId", 2); // admin_jane
        review2.put("FoodId", 2); // Bánh Mì Thịt Nướng
        review2.put("Rating", 4);
        review2.put("Feedback", "Ngon nhưng hơi ít thịt.");
        db.insert("Reviews", null, review2);

        ContentValues cartItem1 = new ContentValues();
        cartItem1.put("UserId", 1); // john_doe
        cartItem1.put("FoodId", 1); // Phở Bò
        cartItem1.put("Quantity", 2);
        db.insert("CartItems", null, cartItem1);

        ContentValues cartItem2 = new ContentValues();
        cartItem2.put("UserId", 2); // admin_jane
        cartItem2.put("FoodId", 2); // Bánh Mì Thịt Nướng
        cartItem2.put("Quantity", 1);
        db.insert("CartItems", null, cartItem2);

        ContentValues otp1 = new ContentValues();
        otp1.put("UserId", 1); // john_doe
        otp1.put("OtpCode", "123456");
        otp1.put("ExpiresAt", "2025-01-01 00:00:00");
        otp1.put("IsUsed", 0);
        db.insert("OTPs", null, otp1);

        ContentValues otp2 = new ContentValues();
        otp2.put("UserId", 2); // admin_jane
        otp2.put("OtpCode", "654321");
        otp2.put("ExpiresAt", "2025-01-01 00:00:00");
        otp2.put("IsUsed", 0);
        db.insert("OTPs", null, otp2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Orders");
        db.execSQL("DROP TABLE IF EXISTS OrderItems");
        db.execSQL("DROP TABLE IF EXISTS Foods");
        db.execSQL("DROP TABLE IF EXISTS Vouchers");
        db.execSQL("DROP TABLE IF EXISTS Notifications");
        db.execSQL("DROP TABLE IF EXISTS Reviews");
        db.execSQL("DROP TABLE IF EXISTS CartItems");
        db.execSQL("DROP TABLE IF EXISTS OTPs");
        onCreate(db);
    }
}
