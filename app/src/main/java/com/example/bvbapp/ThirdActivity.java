package com.example.bvbapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity {
    ArrayList<Standings> stand = new ArrayList<>();
    private TableLayout tableLayout;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        tableLayout = (TableLayout) findViewById(R.id.standings);
        queue = Volley.newRequestQueue(this);
        APIInvoke();
    }


    public void requestWithSomeHttpHeaders() {
        String url = "https://api.football-data.org/v4/competitions/PL/standings";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            Mapper(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Auth-Token", "60e91eeadc16445192e1efe8bf5d2d3f");
                return params;
            }

        };
        queue.add(getRequest);

    }

    public void APIInvoke() {
        requestWithSomeHttpHeaders();

    }

    public void Mapper(String jason) throws JSONException {
        JSONObject jsonObject = new JSONObject(jason);
        JSONArray standingsObj = jsonObject.getJSONArray("standings");
        JSONArray tableObj = standingsObj.getJSONObject(0).getJSONArray("table");
        ArrayList<Standings> stand = new ArrayList<>();
        for (int i = 0; i < tableObj.length(); i++) {
            int pos = tableObj.getJSONObject(i).getInt("position");
            int points = tableObj.getJSONObject(i).getInt("points");
            int games = tableObj.getJSONObject(i).getInt("playedGames");
            String names = tableObj.getJSONObject(i).getJSONObject("team").getString("name");
            Standings satandings = new Standings(pos, games, names, points);
            stand.add(satandings);
        }
        stand.sort(Comparator.naturalOrder());
        this.stand = stand;
        fillStandingTable();

    }

    public void fillStandingTable() {
        View tableRowlb = LayoutInflater.from(this).inflate(R.layout.tableitem, null, false);
        TextView poslb = (TextView) tableRowlb.findViewById(R.id.pos);
        TextView matchplayedlb = (TextView) tableRowlb.findViewById(R.id.matchplayed);
        TextView pointslb = (TextView) tableRowlb.findViewById(R.id.points);
        TextView teamnamelb = (TextView) tableRowlb.findViewById(R.id.teamname);


        teamnamelb.setText("team name");
        pointslb.setText("points ");
        poslb.setText("position ");
        matchplayedlb.setText("played matches ");
        tableLayout.addView(tableRowlb);
        for (int i = 0; i < stand.size(); i++) {
            View tableRow = LayoutInflater.from(this).inflate(R.layout.tableitem, null, false);
            TextView pos = (TextView) tableRow.findViewById(R.id.pos);
            TextView matchplayed = (TextView) tableRow.findViewById(R.id.matchplayed);
            TextView points = (TextView) tableRow.findViewById(R.id.points);
            TextView teamname = (TextView) tableRow.findViewById(R.id.teamname);

            pos.setText("" + (stand.get(i).pos));
            matchplayed.setText("" + stand.get(i).playedGames);
            points.setText("" + stand.get(i).points);
            teamname.setText(stand.get(i).teamName);
            tableLayout.addView(tableRow);
        }
    }

}