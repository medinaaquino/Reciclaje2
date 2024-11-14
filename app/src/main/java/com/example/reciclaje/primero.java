package com.example.reciclaje;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;

public class primero extends Fragment {
    private static final String BASE_URL = "https://newsapi.org/"; // URL base de la API
    private static final String API_KEY = "e02f52d94639422d945bef64137a669e"; // Reemplaza con tu API key de NewsAPI

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_primero, container, false);
        obtenerNoticias(view);
        return view;
    }

    private void obtenerNoticias(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApiService apiService = retrofit.create(NewsApiService.class);

        // Agregar el parámetro "language=es" para obtener noticias en español
        Call<NewsResponse> call = apiService.getEnvironmentalNews("climate change", API_KEY, "es");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NewsArticle> noticias = response.body().getArticles();
                    mostrarNoticias(noticias, view);
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                // Manejar error
            }
        });
    }

    private void mostrarNoticias(List<NewsArticle> noticias, View view) {
        LinearLayout layoutNoticias = view.findViewById(R.id.layout_noticias);

        for (NewsArticle noticia : noticias) {
            // Crear un CardView para cada noticia
            CardView cardView = new CardView(getContext());

            // Establecer parámetros para el CardView
            CardView.LayoutParams params = new CardView.LayoutParams(
                    CardView.LayoutParams.MATCH_PARENT,
                    CardView.LayoutParams.WRAP_CONTENT
            );
            cardView.setLayoutParams(params);

            // Configurar propiedades del CardView
            cardView.setCardElevation(8f); // Agrega sombra
            cardView.setRadius(16f); // Bordes redondeados
            cardView.setCardBackgroundColor(getResources().getColor(android.R.color.white)); // Fondo blanco

            // Crear un LinearLayout dentro del CardView para agregar el texto
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(16, 16, 16, 16); // Espaciado dentro del CardView

            // Crear el TextView para el título de la noticia
            TextView titulo = new TextView(getContext());
            titulo.setText(noticia.getTitle());
            titulo.setTextSize(18f);
            titulo.setTextColor(getResources().getColor(android.R.color.black));

            // Agregar el título al LinearLayout
            linearLayout.addView(titulo);

            // Agregar el LinearLayout al CardView
            cardView.addView(linearLayout);

            // Establecer márgenes para separar los CardViews
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 0, 0, 16); // Margen inferior de 16dp
            cardView.setLayoutParams(cardParams);

            // Agregar el CardView al layout principal
            layoutNoticias.addView(cardView);
        }
    }
}
