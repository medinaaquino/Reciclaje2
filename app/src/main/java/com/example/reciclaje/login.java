package com.example.reciclaje;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class login extends AppCompatActivity {

    private TextInputEditText usuario, clave;
    private String user,pass, url, resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false); // Verificar el estado de la sesión

        if (isLoggedIn) {
            // Si el usuario está logueado, redirigir a la actividad principal
            String usuario = sharedPreferences.getString("usuario", "");
            String tipoUsuario = sharedPreferences.getString("tipoUsuario", "");

            Intent intent;
            if (tipoUsuario.equals("1")) {
                intent = new Intent(login.this, MainActivity.class);
            } else {
                intent = new Intent(login.this, MainActivity.class);
            }
            intent.putExtra("usuario", usuario);
            startActivity(intent);
            finish(); // Evitar que el usuario regrese a la pantalla de login
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usuario=findViewById(R.id.username);
        clave=findViewById(R.id.password);

    }

    public void regitrarse(View view) {
        Intent registro = new Intent(login.this, registro.class);
        startActivity(registro);
    }

    public void VerificarDatos(View view){

        user = usuario.getText().toString().trim();
        pass = clave.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(login.this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }

        url = "https://bajel.online/doggy/login.php";

        RequestParams parametros = new RequestParams();
        parametros.put("usu", user);
        parametros.put("pas", pass);

        Toast.makeText(login.this, "Verificando datos...", Toast.LENGTH_SHORT).show();
        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String respuesta = new String(responseBody);
                try {
                    JSONObject MiJson = new JSONObject(respuesta);

                    if (MiJson.has("exito")) {
                        String mensajeBienvenida = "Bienvenido";
                        String usuario = MiJson.getString("usuario");
                        String tipoUsuario = MiJson.getString("tipo");

                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);  // Guardar que el usuario está logueado
                        editor.putString("usuario", usuario);  // Guardar el nombre de usuario
                        editor.putString("tipoUsuario", tipoUsuario);  // Guardar el tipo de usuario
                        editor.apply();  // Guardar los datos

                        Toast.makeText(login.this, mensajeBienvenida, Toast.LENGTH_SHORT).show();

                        Intent intent;
                        if (tipoUsuario.equals("1")) {
                            intent = new Intent(login.this, MainActivity.class);
                        } else {
                            intent = new Intent(login.this, MainActivity.class);
                        }
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        finish();
                       /* if (MiJson.getString("tipo").equals("1")) {
                            Intent ventana2 = new Intent(login.this, infomascota.class);
                            ventana2.putExtra("usuario", usuario);
                            startActivity(ventana2);
                        } else {
                            Intent ventana2 = new Intent(login.this, MainActivity.class);
                            ventana2.putExtra("usuario", usuario);
                            startActivity(ventana2);
                        }
                        finish();*/
                    } else if (MiJson.has("error")) {
                        String mensajeError = MiJson.getString("error");
                        Toast.makeText(login.this, mensajeError, Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e) {
                    Toast.makeText(login.this, "Error en JSON", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(login.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }
}