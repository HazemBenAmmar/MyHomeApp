package tn.org.myhomeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemsList extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        setTitle(getString(R.string.devices));
        if (currentUser != null) {
            users = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
            users.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    List<String> ids = new ArrayList<>();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Terminal u = child.getValue(Terminal.class);
                        ids.add(u.getId());
                    }

                    ItemAdapter adapter = new ItemAdapter(ids);
                    adapter.setItemClick(id -> {
                        Intent in = new Intent(ItemsList.this, DashboardActivity.class);
                        in.putExtra("id", id);
                        startActivity(in);
                    });
                    RecyclerView recyclerView = findViewById(R.id.recycler);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ItemsList.this));
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

        findViewById(R.id.add).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_view, null, false);
        new MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setTitle("Add")
                .setMessage("Add terminal")
                .setPositiveButton("Add", (dialog, which) -> {
                    addValue(((EditText) dialogView.findViewById(R.id.id_field)).getText().toString());
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }

    private void addValue(String id) {
        users.child(id).setValue(new Terminal(id, 0, 0)).addOnCompleteListener(this, task -> {
            Toast.makeText(ItemsList.this, "Added",
                    Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
}