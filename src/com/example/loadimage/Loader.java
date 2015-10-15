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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Loader extends ActionBarActivity {
	JSONParser jsonParser = new JSONParser();
	private ProgressDialog pDialog;
	private int jsonstatus = 0;
	private static String login_page = "http://192.168.40.80:81/loginapi/index.php/loadimage";
	String TAG_CATEGORY = "category";
	String category = "";
	ArrayList<Category> categoryListval  =  new ArrayList<Category>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_loader);
		new FetchCategory().execute();
	}

	class FetchCategory extends AsyncTask<String, String, String> {
		JSONArray catdatalist;
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Loader.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", "ravindra"));

			Log.d("state", "beforehttp");
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(login_page, "POST",
					params);
			if (json == null) {
				jsonstatus = 1;
				Log.d("error", "connection error");
			} else {
				try {
					category = json.getString(TAG_CATEGORY);
					catdatalist = new JSONArray(category);
					
					for(int i= 0;i<catdatalist.length();i++){
						Category catdata = new Category();
						catdata.setCat_id(i);
						catdata.setCat_name(catdatalist.getString(i));
						categoryListval.add(catdata);
					}
					Log.d("catdatalist", catdatalist.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			if (jsonstatus == 1) {
				Log.d("status", String.valueOf(jsonstatus));
				Toast.makeText(getApplicationContext(),
						"Server Error!! Please Try Again Later.",
						Toast.LENGTH_LONG).show();
			} else {
				final ListView listView = (ListView) findViewById(R.id.category_list);
				ArrayAdapter<Category> adapter;
				adapter=new ArrayAdapter<Category>(getApplicationContext(),
			            android.R.layout.simple_list_item_1,
			            categoryListval);
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> a, View v,
							int position, long id) {
						Category mycategory = (Category) listView
								.getItemAtPosition(position);
						Intent i = new Intent(getApplicationContext(),
								MainActivity.class);
						i.putExtra("id",position);
						Toast.makeText(Loader.this,
								"Selected :" + mycategory,
								Toast.LENGTH_LONG).show();
						startActivity(i);
						
					}
				});
				Toast.makeText(getApplicationContext(), "ssss",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loader, menu);
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
