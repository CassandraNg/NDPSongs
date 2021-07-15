package sg.edu.rp.c346.id20017476.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etTitle, etSinger, etYear;
    RadioGroup rgstars;
    Button bnInsert, bnShow;
    Integer star, year;
    String title, singer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        rgstars = findViewById(R.id.rgStar);
        bnInsert = findViewById(R.id.bnInsert);
        bnShow = findViewById(R.id.bnShow);


        bnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etYear.length() != 0 && etSinger.length() != 0 && etTitle.length() != 0){
                    title = etTitle.getText().toString().trim();
                    singer = etSinger.getText().toString().trim();
                    year = Integer.valueOf(etYear.getText().toString().trim());
                    star = getStars();
                    DBHelper dbh = new DBHelper(MainActivity.this);
                    dbh.insertSong(title,singer,year,star);
                    dbh.close();
                    Toast.makeText(MainActivity.this, "Song inserted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });

        etTitle.setText("");
        etSinger.setText("");
        etYear.setText("");

    }
    private int getStars() {
        int stars = 1;
        switch (rgstars.getCheckedRadioButtonId()) {
            case R.id.rb1:
                stars = 1;
                break;
            case R.id.rb2:
                stars = 2;
                break;
            case R.id.rb3:
                stars = 3;
                break;
            case R.id.rb4:
                stars = 4;
                break;
            case R.id.rb5:
                stars = 5;
                break;
        }
        return stars;
    }
}