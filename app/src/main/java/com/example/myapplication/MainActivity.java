package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProductAdapter.Listener {

    private final ArrayList<Product> products = new ArrayList<>();
    private ProductAdapter adapter;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Product p = (Product) result.getData().getSerializableExtra(EditProductActivity.EXTRA_PRODUCT);
                    int pos = result.getData().getIntExtra(EditProductActivity.EXTRA_POSITION, -1);
                    if (p != null) {
                        if (pos >= 0 && pos < products.size()) {
                            products.set(pos, p);
                            adapter.notifyItemChanged(pos);
                        } else {
                            products.add(p);
                            adapter.notifyItemInserted(products.size() - 1);
                        }
                    }
                }
            }
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.rvProducts);
        FloatingActionButton fab = findViewById(R.id.fabAdd);

        adapter = new ProductAdapter(products, this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        // sample data
        products.add(new Product("Bánh mì", 12000));
        products.add(new Product("Cà phê", 30000));
        adapter.notifyDataSetChanged();

        fab.setOnClickListener(v -> {
            Intent i = new Intent(this, EditProductActivity.class);
            i.putExtra(EditProductActivity.EXTRA_POSITION, -1);
            launcher.launch(i);
        });
    }

    @Override
    public void onEdit(int position, Product product) {
        Intent i = new Intent(this, EditProductActivity.class);
        i.putExtra(EditProductActivity.EXTRA_PRODUCT, product);
        i.putExtra(EditProductActivity.EXTRA_POSITION, position);
        launcher.launch(i);
    }

    @Override
    public void onDelete(int position) {
        if (position >= 0 && position < products.size()) {
            products.remove(position);
            adapter.notifyItemRemoved(position);
        }
    }
}