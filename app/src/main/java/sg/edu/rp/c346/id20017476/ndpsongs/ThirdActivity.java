package sg.edu.rp.c346.id20017476.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {
    EditText etID, etEditTitle, etEditSinger, etEditYear;
    RadioGroup rgEditStar;
    RadioButton r1,r2,r3,r4,r5;
    Button bnUpdate, bnDelete, bnCancel;
    Integer starChecked, editStar;
    Song data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etID = findViewById(R.id.etID);
        etEditTitle = findViewById(R.id.etEditTitle);
        etEditSinger = findViewById(R.id.etEditSinger);
        etEditYear = findViewById(R.id.etEditYear);
        bnUpdate = findViewById(R.id.bnUpdate);
        bnDelete = findViewById(R.id.bnDelete);
        bnCancel = findViewById(R.id.bnCancel);
        r1 = findViewById(R.id.rbE1);
        r2 = findViewById(R.id.rbE2);
        r3 = findViewById(R.id.rbE3);
        r4 = findViewById(R.id.rbE4);
        r5 = findViewById(R.id.rbE5);

        Intent i = getIntent();
        final Song data = (Song) i.getSerializableExtra("data");

        etID.setText(data.getId());
        etEditTitle.setText(data.getTitle());
        etEditSinger.setText(data.getSingers());
        etEditYear.setText(data.getYear());

        starChecked = data.getStars();


        if (rgEditStar.getCheckedRadioButtonId() == R.id.rbE1){
            r1.setChecked(true);
        }else if (rgEditStar.getCheckedRadioButtonId() == R.id.rbE2){
            r2.setChecked(true);
        }else if (rgEditStar.getCheckedRadioButtonId() == R.id.rbE3){
            r3.setChecked(true);
        }else if (rgEditStar.getCheckedRadioButtonId() == R.id.rbE4){
            r4.setChecked(true);
        }else if (rgEditStar.getCheckedRadioButtonId() == R.id.rbE5){
            r5.setChecked(true);
        }


        bnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                RadioButton editStar = findViewById(rgEditStar.getCheckedRadioButtonId());
                data.setTitle(etEditTitle.getText().toString().trim());
                data.setSingers(etEditSinger.getText().toString().trim());
                data.setYear(Integer.parseInt(etEditYear.getText().toString().trim()));
                data.setStars(Integer.parseInt(editStar.getText().toString()));
                dbh.updateSong(data);
                dbh.close();

                finish();
            }
        });

        bnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                dbh.deleteSong(data.getId());

                finish();
            }
        });

        bnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}