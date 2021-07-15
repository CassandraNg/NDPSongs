package sg.edu.rp.c346.id20017476.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    Button bnshow5Star;
    Spinner spnYear;
    ListView lv;
    ArrayList<Song> al;
    ArrayAdapter<Song> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        bnshow5Star = findViewById(R.id.bnshow5stars);
        spnYear = findViewById(R.id.spnYear);
        lv = findViewById(R.id.lv);

        DBHelper dbh = new DBHelper(SecondActivity.this);
        al = dbh.getAllSongs();
        dbh.close();

        aa = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        bnshow5Star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                al.clear();
                al.addAll(dbh.getAllSongs(5));
                aa.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {

                Intent i = new Intent(SecondActivity.this,
                        ThirdActivity.class);
                i.putExtra("data",al.get(position));
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper dbh = new DBHelper(SecondActivity.this);
        al.clear();
        al.addAll(dbh.getAllSongs());
        aa.notifyDataSetChanged();
    }
}