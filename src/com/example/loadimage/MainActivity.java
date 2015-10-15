package com.example.loadimage;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.LauncherActivity.ListItem;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

//import com.example.customizedlist.R;

public class MainActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener{
	Context context;
	int categoryid;
	JSONParser jsonParser = new JSONParser();
	String TAG_URL = "imageurl";
	String urllist = "";
	String headline = "";
	String productid = "";
	String TAG_HEADLINES = "imageheading";
	String TAG_ID = "prod_id";
	private int jsonstatus = 0;
	private static String login_page = "http://192.168.40.80:81/loginapi/index.php/loadimage";
	private ProgressDialog pDialog;
	private SwipeRefreshLayout swipeRefreshLayout;
	// ListItemCustom newsData = new ListItemCustom();
	ArrayList<ListItemCustom> listMockData = new ArrayList<ListItemCustom>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent= getIntent();
		categoryid= intent.getIntExtra("id",999);
		//System.out.println(categoryid);
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				Log.d("refresh","ok");
				new FetchData().execute();
				//swipeRefreshLayout.setRefreshing(false);
			}
			
		});
		
		new FetchData().execute();

		// ArrayList<ListItemCustom> listData = getListData();

		// final ListView listView = (ListView) findViewById(R.id.custom_list);
		//
		// listView.setAdapter(new CustomListAdapter(this, listMockData));

	}

		
	class FetchData extends AsyncTask<String, String, String> {

		JSONArray imageurl;
		JSONArray imageheadline;
		JSONArray productidarry;
		private Context mContext;

		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("cat_id", String.valueOf(categoryid)));

			//Log.d("state", "beforehttp");
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(login_page, "POST",
					params);
			if (json == null) {
				jsonstatus = 1;
				Log.d("error", "connection error");
			} else {
				try {
					urllist = json.getString(TAG_URL);
					headline = json.getString(TAG_HEADLINES);
					productid = json.getString(TAG_ID);
					imageurl = new JSONArray(urllist);
					imageheadline = new JSONArray(headline);
					productidarry = new JSONArray(productid);
					 Log.d("productid", productidarry.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {

			pDialog.dismiss();

			if (jsonstatus == 1) {
				Log.d("status", String.valueOf(jsonstatus));
				Toast.makeText(getApplicationContext(),
						"Server Error!! Please Try Again Later.",
						Toast.LENGTH_LONG).show();
			} else {
				listMockData.clear();
				for (int i = 0; i < imageurl.length(); i++) {
					ListItemCustom newsData = new ListItemCustom();
					try {

						// Log.d("url",imageurl.getString(i));
						// Log.d("headline",imageheadline.getString(i));
						newsData.setProductID(productidarry.getInt(i));
						newsData.setUrl(imageurl.getString(i));
						newsData.setHeadline(imageheadline.getString(i));
						//newsData.setReporterName("Ravindra ");
						newsData.setDate("May 26, 2013, 13:35");

					} catch (JSONException e) {
						e.printStackTrace();
					}
					listMockData.add(newsData);
				}
				final ListView listView = (ListView) findViewById(R.id.custom_list);

				listView.setAdapter(new CustomListAdapter(
						getApplicationContext(), listMockData));
				swipeRefreshLayout.setRefreshing(false);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> a, View v,
							int position, long id) {
						ListItemCustom newsData = (ListItemCustom) listView
								.getItemAtPosition(position);
						Intent i = new Intent(getApplicationContext(),
								Product_detail.class);
						System.out.println(newsData.getProductID());
						i.putExtra("prod_id",newsData.getProductID());
						startActivity(i);
						// Toast.makeText(MainActivity.this,
						// "Selected :" + " " + newsData,
						// Toast.LENGTH_LONG).show();
					}
				});
				// Log.d("headline", listMockData.toString());
				// Toast.makeText(getApplicationContext(), "sss",
				// Toast.LENGTH_LONG).show();
			}
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		//
		int id = item.getItemId();
		if (id == R.id.action_refresh) {
			Log.d("click", "refresh");
			new FetchData().execute();
			return true;
		}
		return false;
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	

}
