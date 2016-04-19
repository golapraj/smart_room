package com.akibgolap.kuet.SmartRoom;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    public final static String PREF_IP = "PREF_IP_ADDRESS";
    public final static String PREF_PORT = "PREF_PORT_NUMBER";
    public final static String PREF_ROOM= "PREF_STREAM_ADDRESS";


    private ImageButton startButton ;
    private EditText editArduinoIPAddress, editArduinoPortNumber, editRoomIPAddress;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences= getSharedPreferences("HTTP_HELPER_PREFS", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        startButton = (ImageButton) findViewById(R.id.startButton);

        editArduinoIPAddress = (EditText) findViewById(R.id.editArduinoIPAddress);
        editArduinoPortNumber = (EditText) findViewById(R.id.editArduinoPortNumber);
        editRoomIPAddress = (EditText) findViewById(R.id.editRoomIPAddress);


        editArduinoIPAddress.setText(sharedPreferences.getString(PREF_IP, ""));
        editArduinoPortNumber.setText(sharedPreferences.getString(PREF_PORT,""));
        editRoomIPAddress.setText(sharedPreferences.getString(PREF_ROOM,""));


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String arduinoIP = editArduinoIPAddress.getText().toString().trim();
                String arduinoPort = editArduinoPortNumber.getText().toString().trim();
                String roomAddress = editRoomIPAddress.getText().toString().trim();

                editor.putString(PREF_IP, arduinoIP);
                editor.putString(PREF_PORT, arduinoPort);
                editor.putString(PREF_ROOM, roomAddress);


                editor.commit();

                Intent intent = new Intent(MainActivity.this,Main22Activity.class);

                intent.putExtra("ARDUINO_IP",arduinoIP);
                intent.putExtra("ARDUINO_PORT",arduinoPort);
                intent.putExtra("ROOM_ADDRESS",roomAddress);


                startActivity(intent);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
