package com.example.rajukumarsingh.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Bloodbank_list extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ListView listView;
    String[] lati,longi,h_name,state,add,lati1,longi1,h_name1,state1,add1;
    GetAllData get;
    Double d1,d2,d3,d4;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String[] dist;
    int d5,n=2;
    Spinner spinner;
    String str[]={"2","3","5","10","15","20"};
    int k=0;
    String url = "http://rahulraj47.coolpage.biz/gps_fetch.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodbank_list);

        getLatLng();

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(Bloodbank_list.this,MapsActivity.class);
                Double a,b;
                a = Double.parseDouble(lati1[position]);
                b = Double.parseDouble(longi1[position]);
                i.putExtra("Latitude1",a);
                i.putExtra("Longitude1",b);
                i.putExtra("Latitude2",d1);
                i.putExtra("Longitude2",d2);
                i.putExtra("LatitudeArray",lati);
                i.putExtra("LongitudeArray",longi);
                i.putExtra("HospitalArray",h_name);
                i.putExtra("Length",d5);
                startActivity(i);
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,str);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void getLatLng() {
        class GetLatLong extends AsyncTask<Void,Void,Void>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                Bundle bundle=getIntent().getExtras();
                d1=bundle.getDouble("Latitude");
                d2=bundle.getDouble("Longitude");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }
        GetLatLong get = new GetLatLong();
        get.execute();
    }


    private void getJSON() {
        class GetUrls extends AsyncTask<String, Void, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(Bloodbank_list.this, "Fetching Data", "Please Wait...", true, true);
            }

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return sb.toString().trim();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                get = new GetAllData(s);
                setData();
                progressDialog.dismiss();
            }
        }
        GetUrls g = new GetUrls();
        g.execute(url);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        k=0;
        n = Integer.parseInt((String) adapterView.getItemAtPosition(i));
        getJSON();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        getJSON();
    }


    public class GetAllData {
        public GetAllData(String s) {
            try {
                // json output in string
                jsonArray = new JSONArray(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void getAllData() {
            h_name = new String[jsonArray.length()];
            state = new String[jsonArray.length()];
            lati = new String[jsonArray.length()];
            longi = new String[jsonArray.length()];
            add = new String[jsonArray.length()];
            d5=jsonArray.length();

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    h_name[i] = jsonObject.getString("Hospital_Name");
                    state[i] = jsonObject.getString("State");
                    lati[i] = jsonObject.getString("Latitude");
                    longi[i] = jsonObject.getString("Longitude");
                    add[i] = jsonObject.getString("Address");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setData() {
        class GetImage extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... params) {
                get.getAllData();
                return null;
            }

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Bloodbank_list.this, "Downloading...", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                calculate();
                Custom custom = new Custom();
                listView.setAdapter(custom);
            }
        }
        GetImage getImage = new GetImage();
        getImage.execute();
    }

    private void calculate() {
        float[] result = new float[2];
        dist = new String[jsonArray.length()];
        h_name1 = new String[jsonArray.length()];
        state1 = new String[jsonArray.length()];
        lati1 = new String[jsonArray.length()];
        longi1 = new String[jsonArray.length()];
        add1 = new String[jsonArray.length()];
        for (int i=0;i<jsonArray.length();i++){
            d3 = Double.parseDouble(lati[i]);
            d4 = Double.parseDouble(longi[i]);
            Location.distanceBetween(d1,d2,d3,d4,result);
            if (result[0] <= (n*1000)) {
                dist[k] = Float.toString(result[0]);
                lati1[k]=lati[i];
                longi1[k]=longi[i];
                h_name1[k]=h_name[i];
                state1[k]=state[i];
                add1[k]=add[i];
                k++;
            }
        }
    }

    public class Custom extends BaseAdapter {

        @Override
        public int getCount() {
            return k;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.custom_layout, parent, false);
            TextView t1, t2, t3, t4, t5,t6;

            t1 = (TextView) v.findViewById(R.id.textView);
            t2 = (TextView) v.findViewById(R.id.textView2);
            t3 = (TextView) v.findViewById(R.id.textView3);
            t4 = (TextView) v.findViewById(R.id.textView4);
            t5 = (TextView) v.findViewById(R.id.textView5);
            t6 = (TextView) v.findViewById(R.id.textView6);

            t1.setText(h_name1[position]);
            t2.setText(state1[position]);
            t3.setText(lati1[position]);
            t4.setText(longi1[position]);
            t5.setText(add1[position]);
            t6.setText(dist[position]);
            return v;
        }
    }


}
