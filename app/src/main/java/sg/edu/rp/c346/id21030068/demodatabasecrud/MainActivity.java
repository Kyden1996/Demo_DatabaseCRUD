package sg.edu.rp.c346.id21030068.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvDBcontent;
    EditText etContent;
    Button btnAdd;
    Button btnEdit;
    Button btnRetrieve;
    ArrayList<Note> alNote;
    ListView lvNote;
    ArrayAdapter<Note> aaNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDBcontent = findViewById(R.id.tvContent);
        etContent = findViewById(R.id.etIDContent);
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnRetrieve = findViewById(R.id.btnRetrieve);
        lvNote = findViewById(R.id.lvNote);
        alNote = new ArrayList<Note>();
        aaNote = new ArrayAdapter<Note>(this,
                android.R.layout.simple_list_item_1, alNote);
        lvNote.setAdapter(aaNote);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etContent.getText().toString();
                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertNote(data);

                if (inserted_id != -1){
                    alNote.clear();
                    alNote.addAll(dbh.getAllNotes(data));
                    aaNote.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note target = alNote.get(0);

                Intent i = new Intent(MainActivity.this,
                        EditActivity.class);
                i.putExtra("data", target);
                startActivity(i);
            }
        });



        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                alNote.clear();
                // al.addAll(dbh.getAllNotes());
                String filterText = etContent.getText().toString().trim();
                if(filterText.length() == 0) {
                    alNote.addAll(dbh.getAllNotes(" "));
                }
                else{
                    alNote.addAll(dbh.getAllNotes(filterText));
                }
                aaNote.notifyDataSetChanged();
            }
        });


        lvNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Note data = alNote.get(position);
                Intent i = new Intent(MainActivity.this,
                        EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });


}

    @Override
    protected void onResume() {
        super.onResume();
        btnRetrieve.performClick();
    }
}