package com.example.snoozbo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    SnoDbHandler dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dh = new SnoDbHandler(this, null, null, 1);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, 21);
                intent.putExtra(AlarmClock.EXTRA_MINUTES, 10);
                startActivity(intent);
                SQLiteDatabase db = dh.getWritableDatabase();
                Alarm ala = new Alarm(0 );
                dh.addSnooze(ala);
                db.close();
            }
        });
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(AlarmClock.ACTION_SNOOZE_ALARM);
                SQLiteDatabase d = dh.getWritableDatabase();
                dh.updateSnooze(1);
                if(dh.checkSnooze() == false) {
                    startActivity(in);
                }

                d.close();
            }
        });
    }
}
