package com.project.pertemuandelapan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditTeman extends AppCompatActivity {
    TextView idText;
    EditText edNama, edTelpon;
    Button editBtn;
    String id, nm, tlp, namaEd, telponEd;
    int sukses;

    public static String url_upate = "https://20200140018.praktikumtiumy.com/updatetm.php";
    public static final String TAG = EditTeman.class.getSimpleName();
    public static final String TAG_SUCCES = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teman);

        idText = findViewById(R.id.textId);
        edNama = findViewById(R.id.edtNm);
        edTelpon = findViewById(R.id.edtTlp);
        editBtn = findViewById(R.id.buttonEdit);

        Bundle bundle =  getIntent().getExtras();
        id = bundle.getString("kunci1");
        nm = bundle.getString("kunci2");
        tlp = bundle.getString("kunci3");

        idText.setText("Id: "+ id);
        edNama.setText(nm);
        edTelpon.setText(tlp);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditData();
            }
        });
    }

    public void EditData(){
        namaEd =edNama.getText().toString();
        telponEd = edTelpon.getText().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringReq = new StringRequest(Request.Method.POST, url_upate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Respon: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    sukses = jObj.getInt(TAG_SUCCES);
                    if (sukses == 1) {
                        Toast.makeText(EditTeman.this, "Sukses Mengedit Data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditTeman.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }}
            },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Error : "+error.getMessage());
                Toast.makeText(EditTeman.this, "Gagal Edit Data", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id",id);
                params.put("nama",namaEd);
                params.put("telpon",telponEd);

                return params;
            }
        };
        requestQueue.add(stringReq);
        CallHomeActivity();

    }

    public  void CallHomeActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

}