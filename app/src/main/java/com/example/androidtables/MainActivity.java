package com.example.androidtables;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	TextView id;
	EditText name;
	EditText quantity;
	DatabaseHelper MyDB;
	String x = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		id = (TextView)findViewById(R.id.productID);
		name = (EditText)findViewById(R.id.productName);
		quantity = (EditText)findViewById(R.id.productQuantity);

		MyDB = new DatabaseHelper(this);

		final Button add = (Button)findViewById(R.id.bttnAdd);
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String Pname = name.getText().toString();
				String Pquantity = quantity.getText().toString();

				if(!MyDB.addData(Pname, Pquantity)){
					Toast.makeText(MainActivity.this,"Insertion Failed",Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(MainActivity.this,"Insertion Successful",Toast.LENGTH_SHORT).show();
					Cursor cursor = MyDB.getLast();
					x = cursor.getString(0);
					id.setText(x);
				}
			}
		});

		final Button find = (Button)findViewById(R.id.bttnFind);
		find.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String Pid = id.getText().toString();
				if(!Pid.equals("NaN") && !Pid.equals("0")) {
					int pID = Integer.parseInt(Pid);
					Cursor cur = MyDB.structuredQuery(pID);
					String cID = cur.getString(0);
					String cName = cur.getString(1);
					String cQuantity = cur.getString(2);
					Toast.makeText(MainActivity.this, cID + ", " + cName + ", " + cQuantity, Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(MainActivity.this,"Find Failed",Toast.LENGTH_SHORT).show();
				}
			}
		});

		final Button delete = (Button)findViewById(R.id.bttnDelete);
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String Pid = id.getText().toString();

				if(!Pid.equals("NaN") && !Pid.equals("0")){
					MyDB.deleteData(Pid);
					Toast.makeText(MainActivity.this,"Successful Delete",Toast.LENGTH_SHORT).show();
					int Iid = Integer.parseInt(Pid);
					Iid = Iid - 1;
					id.setText(Integer.toString(Iid));
				}else{
					Toast.makeText(MainActivity.this,"Delete Failed",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}