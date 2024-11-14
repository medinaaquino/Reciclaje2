package com.example.reciclaje;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class registro extends AppCompatActivity {

    private TextInputEditText usuario,nombre,correo,contra;
    private String user,nom,email,pas, url,resultado, usuarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usuario= findViewById(R.id.username);
        nombre= findViewById(R.id.Nombre);
        correo= findViewById(R.id.email);
        contra= findViewById(R.id.password);
    }

    public void registrarse(View view) {

        user=usuario.getText().toString();
        nom=nombre.getText().toString();
        email=correo.getText().toString();
        pas=contra.getText().toString();


        url = "https://bajel.online/doggy/registro.php";


        if (user.isEmpty() || nom.isEmpty() || email.isEmpty() || pas.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
        else if (pas.length() < 8) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
        } else {



            RequestParams parametros = new RequestParams();
            parametros.put("usu", user);
            parametros.put("nom", nom);
            parametros.put("correo", email);
            parametros.put("pas", pas);
            Toast.makeText(this, "Registrando....", Toast.LENGTH_SHORT).show();

            AsyncHttpClient cliente = new AsyncHttpClient();
            cliente.post(url, parametros, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        String respuesta = new String(responseBody);
                        try {
                            JSONObject MiJson = new JSONObject(respuesta);
                            if (MiJson.has("usurepe")) {
                                Toast.makeText(registro.this, "El usuario ya está en uso", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (MiJson.has("correorepe")) {
                                Toast.makeText(registro.this, "El correo ya está registrado", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (MiJson.has("registra")) {
                                Toast.makeText(registro.this, "Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                                Intent login = new Intent( registro.this,login.class);
                                startActivity(login);
                                finish();
                                return;
                            } else {
                                resultado = MiJson.getString("error");
                            }

                        } catch (Exception e) {
                            Toast.makeText(registro.this, "Error en JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(registro.this, resultado, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(registro.this, "Error en la solicitud: " + statusCode, Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(registro.this, "Error en la conexion", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}