package com.example.loadimage;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Product_detail extends ActionBarActivity {
	int productid;
	private ProgressDialog pDialog;
	private int jsonstatus = 0;
	String TAG_IMGURL = "imageurl";
	String TAG_NAME = "name";
	String TAG_DESC = "description";
	String TAG_TYPES = "types";
	String TAG_PRICE = "price";
	String imageurl = "";
	String name = "";
	String description = "";
	String types = "";
	String price = "";
	ImageView productImg;
	TextView nameTxt,desTxt;
	Spinner spinnertypes;
	LinearLayout cangone;
	JSONParser jsonParser = new JSONParser();
	private static String product_page = "http://192.168.40.80:81/loginapi/index.php/loadimage";
	ArrayList<String> listspinner = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail);
		Intent intent = getIntent();
		productid = intent.getIntExtra("prod_id", 0);
		if (productid == 0) {
			Toast.makeText(Product_detail.this, "Some Problem Occured",
					Toast.LENGTH_LONG).show();
		} else {
			new GetSpecificProduct().execute();
		}

		Toast.makeText(Product_detail.this, "You got Productid :" + productid,
				Toast.LENGTH_LONG).show();
	}

	class GetSpecificProduct extends AsyncTask<String, String, String> {
		JSONArray typearray;
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Product_detail.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("prod_id", String
					.valueOf(productid)));
			JSONObject json = jsonParser.makeHttpRequest(product_page, "POST",
					params);
			if (json == null) {
				jsonstatus = 1;
				Log.d("error", "connection error");
			} else {
				try {
					Product myproduct = new Product();
					imageurl = json.getString(TAG_IMGURL);
					name = json.getString(TAG_NAME);
					myproduct.setProductname(name);
					description = json.getString(TAG_DESC);
					myproduct.setProductID(productid);
					types = json.getString(TAG_TYPES);
					typearray = new JSONArray(types);
					
					if (typearray != null) { 
						   int len = typearray.length();
						   for (int i=0;i<len;i++){ 
							   listspinner.add(typearray.get(i).toString());
						   } 
						} 
					// new ImageDownloaderTask(holder.imageView)
					// .execute(imageurl);
					Log.d("types", listspinner.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			productImg = (ImageView) findViewById(R.id.thumbImage);
			nameTxt = (TextView) findViewById(R.id.setname);
			desTxt = (TextView) findViewById(R.id.setdes);
			spinnertypes = (Spinner) findViewById(R.id.spType);
			cangone = (LinearLayout) findViewById(R.id.goneDiv);
			if(!listspinner.isEmpty()){
				ArrayAdapter<String> type_sp_adaptor = new ArrayAdapter<String>(getApplicationContext(),
					     android.R.layout.simple_spinner_item, listspinner);
				spinnertypes.setAdapter(type_sp_adaptor);
				Log.d("elsegone","no");
			}else{
				Log.d("elsegone","yes");
				cangone.setVisibility(View.GONE);
			}
			
			nameTxt.setText(name);
			desTxt.setText(description);
			new ImageDownloaderTask(productImg)
			.execute(imageurl);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
