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
            String name = etName.getText().toString().trim();
            double price = 0;
            try { price = Double.parseDouble(etPrice.getText().toString().trim()); } catch (Exception ignored) {}
            Product outP = new Product(name, price);
            Intent out = new Intent();
            out.putExtra(EXTRA_PRODUCT, outP);
            out.putExtra(EXTRA_POSITION, pos);
            setResult(Activity.RESULT_OK, out);
            finish();
        });
    }
}