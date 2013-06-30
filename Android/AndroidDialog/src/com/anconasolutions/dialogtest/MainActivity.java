package com.anconasolutions.dialogtest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonOpenDialog = (Button)findViewById(R.id.opendialog);
        buttonOpenDialog.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				OpenDialog();
			}
		});
    }

    void OpenDialog(){
        MyDialogFragment myDialogFragment = MyDialogFragment.newInstance();
        myDialogFragment.show(getFragmentManager(), "myDialogFragment");
   }

    public void okClicked() {
    	Toast.makeText(MainActivity.this, "OK Clicked!", Toast.LENGTH_LONG).show();
    }

    public void cancelClicked() {
    	Toast.makeText(MainActivity.this, "Cancel Clicked!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
