package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditProductActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCT = "product";
    public static final String EXTRA_POSITION = "position";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        EditText etName = findViewById(R.id.etName);
        EditText etPrice = findViewById(R.id.etPrice);
        Button btnSave = findViewById(R.id.btnSave);

        Intent in = getIntent();
        Product p = (Product) in.getSerializableExtra(EXTRA_PRODUCT);
        int pos = in.getIntExtra(EXTRA_POSITION, -1);

        if (p != null) {
            etName.setText(p.getName());
            etPrice.setText(String.valueOf(p.getPrice()));
        }

        btnSave.setOnClickListener(v -> {
            // validate inputs: name and price required
            String name = etName.getText().toString().trim();
            String priceStr = etPrice.getText().toString().trim();

            boolean ok = true;

            if (name.isEmpty()) {
                etName.setError("Tên sản phẩm bắt buộc");
                ok = false;
            } else {
                etName.setError(null);
            }

            if (priceStr.isEmpty()) {
                etPrice.setError("Giá bắt buộc");
                ok = false;
            }

            double price = 0;
            if (ok) {
                try {
                    price = Double.parseDouble(priceStr);
                    if (price < 0) {
                        etPrice.setError("Giá phải >= 0");
                        ok = false;
                    } else {
                        etPrice.setError(null);
                    }
                } catch (Exception ex) {
                    etPrice.setError("Giá không hợp lệ");
                    ok = false;
                }
            }

            if (!ok) {
                // không lưu nếu có lỗi (user sửa input)
                return;
            }

            // giữ nguyên id/description/quantity khi chỉnh sửa
            Product outP;
            if (p != null) {
                outP = new Product(p.getId(), name, p.getDescription() == null ? "" : p.getDescription(), price, p.getQuantity());
            } else {
                outP = new Product(name, price);
            }

            Intent out = new Intent();
            out.putExtra(EXTRA_PRODUCT, outP);
            out.putExtra(EXTRA_POSITION, pos);
            setResult(Activity.RESULT_OK, out);
            finish();
        });
    }
}