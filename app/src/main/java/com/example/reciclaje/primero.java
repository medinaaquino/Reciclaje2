package com.example.reciclaje;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;

public class primero extends Fragment {
    private static final String BASE_URL = "https://newsapi.org/";
    private static final String API_KEY = "e02f52d94639422d945bef64137a669e"; // Coloca tu clave de API de NewsAPI aquí

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

        // Consulta en NewsAPI para temas específicos de contaminación en El Salvador
        String consulta = "contaminación ambiental OR tala de árboles OR deforestación OR crisis ambiental";

        Call<NewsResponse> call = apiService.getEnvironmentalNews(consulta, API_KEY, "es");
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

        // Mostrar solo las primeras 10 noticias que no contengan palabras clave no deseadas
        int maxNoticias = 10;
        int noticiasMostradas = 0;

        for (NewsArticle noticia : noticias) {
            // Verificar si el título contiene palabras no deseadas
            String tituloNoticia = noticia.getTitle();
            if (tituloNoticia != null && (tituloNoticia.toLowerCase().contains("hoy no circula") ||
                    tituloNoticia.toLowerCase().contains("sabatino") ||
                    tituloNoticia.toLowerCase().contains("ia") ||
                    tituloNoticia.toLowerCase().contains("político") ||
                    tituloNoticia.toLowerCase().contains("atún") ||
                    tituloNoticia.toLowerCase().contains("tribunal") ||
                    tituloNoticia.toLowerCase().contains("[removed]"))) {
                continue; // Omitir esta noticia si contiene palabras clave no deseadas
            }

            // Mostrar la noticia solo si el límite no ha sido alcanzado
            if (noticiasMostradas >= maxNoticias) {
                break;
            }

            CardView cardView = new CardView(getContext());
            CardView.LayoutParams params = new CardView.LayoutParams(
                    CardView.LayoutParams.MATCH_PARENT,
                    CardView.LayoutParams.WRAP_CONTENT
            );
            cardView.setLayoutParams(params);
            cardView.setCardElevation(8f);
            cardView.setRadius(16f);
            cardView.setCardBackgroundColor(getResources().getColor(android.R.color.white));

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(16, 16, 16, 16);

            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    400
            );
            imageView.setLayoutParams(imageParams);

            Glide.with(this).load(noticia.getUrlToImage()).into(imageView);

            TextView titulo = new TextView(getContext());
            titulo.setText(tituloNoticia);
            titulo.setTextSize(18f);
            titulo.setTextColor(getResources().getColor(android.R.color.black));

            linearLayout.addView(imageView);
            linearLayout.addView(titulo);

            cardView.addView(linearLayout);

            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 0, 0, 16);
            cardView.setLayoutParams(cardParams);

            layoutNoticias.addView(cardView);

            noticiasMostradas++;
        }
    }
}
