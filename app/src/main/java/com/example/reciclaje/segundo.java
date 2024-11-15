package com.example.reciclaje;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.osmdroid.views.MapView;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

public class segundo extends Fragment {

    private MapView mapView;

    public segundo() {
        // Required empty public constructor
    }

    public static segundo newInstance(String param1, String param2) {
        segundo fragment = new segundo();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar la vista del fragmento
        View view = inflater.inflate(R.layout.fragment_segundo, container, false);

        // Configuración de OSMDroid
        Configuration.getInstance().setUserAgentValue(requireActivity().getPackageName());

        // Inicializar el MapView
        mapView = view.findViewById(R.id.map);
        // Cambiar la fuente del mapa para una con menos detalles (CYCLEMAP o alguna que prefieras)
        mapView.setTileSource(TileSourceFactory.MAPNIK );  // O cambiar por otro estilo como TileSourceFactory.MAPNIK
        mapView.setMultiTouchControls(true);

        // Control del mapa y zoom
        IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(13.9946, -89.5596); // Punto inicial en Santa Ana
        mapController.setCenter(startPoint);


        addMarker(new GeoPoint(13.977964, -89.565788), "Depósito de Basura");


        addMarker(new GeoPoint(13.979308, -89.566619), "Depósito de Basura");


        addMarker(new GeoPoint(13.979186, -89.566012), "Depósito de Basura");


        addMarker(new GeoPoint(13.980037, -89.566322), "Depósito de Basura");


        addMarker(new GeoPoint(13.979495, -89.565734), "Depósito de Basura");


        addMarker(new GeoPoint(13.980161, -89.564466), "Depósito de Basura");


        addMarker(new GeoPoint(13.980378, -89.563370), "Depósito de Basura");

        addMarker(new GeoPoint(13.976941, -89.567148), "Depósito de Basura ");

        return view;
    }

    private void addMarker(GeoPoint point, String title) {
        // Crear un marcador en la ubicación especificada
        Marker marker = new Marker(mapView);
        marker.setPosition(point);

        // Establecer el título que se mostrará con el ícono (esto es opcional, puedes quitarlo si no quieres que se muestre el texto)
        marker.setTitle(title);

        // Convertir el recurso drawable a un bitmap y escalarlo
        int width = 80; // Aumentar el ancho del ícono (más grande)
        int height = 80; // Aumentar el alto del ícono (más grande)
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.basura); // Asegúrate de tener el ícono 'basura' en la carpeta drawable
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, false);
        BitmapDrawable scaledDrawable = new BitmapDrawable(getResources(), scaledBitmap);

        // Establecer el ícono del marcador con el tamaño ajustado
        marker.setIcon(scaledDrawable);

        // Agregar el marcador al mapa
        mapView.getOverlays().add(marker);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume(); // Llamar al onResume() del mapa
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause(); // Llamar al onPause() del mapa
    }
}
