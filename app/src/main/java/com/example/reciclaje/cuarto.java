package com.example.reciclaje;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import androidx.fragment.app.Fragment;

public class cuarto extends Fragment {

    // Parámetros para inicializar el fragment
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public cuarto() {
        // Constructor vacío requerido
    }

    public static cuarto newInstance(String param1, String param2) {
        cuarto fragment = new cuarto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el layout para este fragmento
        return inflater.inflate(R.layout.fragment_cuarto, container, false);
    }

        private TextView textoReciclaje, textoTresR, textoBeneficiosReciclar;

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            // Inicializar los TextViews
            textoReciclaje = view.findViewById(R.id.textoReciclaje);
            textoTresR = view.findViewById(R.id.textoTresR);
            textoBeneficiosReciclar = view.findViewById(R.id.textoBeneficiosReciclar);

            // Inicializar los botones
            Button buttonReciclaje = view.findViewById(R.id.buttonReciclaje);
            Button buttonTresR = view.findViewById(R.id.buttonTresR);
            Button buttonBeneficiosReciclar = view.findViewById(R.id.buttonBeneficiosReciclar);

            // Configuración de los listeners
            buttonReciclaje.setOnClickListener(v -> toggleReciclaje(view));
            buttonTresR.setOnClickListener(v -> toggleTresR(view));
            buttonBeneficiosReciclar.setOnClickListener(v -> toggleBeneficiosReciclar(view));
        }


        // Método para alternar la visibilidad de los TextViews
        public void toggleReciclaje(View view) {
            // Obtener el TextView donde se mostrará la información
            TextView textoReciclaje = view.findViewById(R.id.textoReciclaje);

            // Alternar la visibilidad del TextView
            if (textoReciclaje.getVisibility() == View.GONE) {
                textoTresR.setVisibility(View.GONE);
                textoBeneficiosReciclar.setVisibility(View.GONE);

                textoReciclaje.setVisibility(View.VISIBLE);
                textoReciclaje.setText("\nEl reciclaje es el proceso de recolectar, tratar y transformar materiales que ya han sido utilizados, con el fin de reutilizarlos en la fabricación de nuevos productos. Este proceso ayuda a reducir la necesidad de extraer nuevos recursos naturales y a disminuir la cantidad de residuos que se generan, lo que contribuye a la conservación del medio ambiente.\n\n" +
                        "El reciclaje puede incluir una amplia gama de materiales, como:\n\n" +
                        "1. Papel y cartón: Se reutilizan para hacer nuevos productos de papel, como periódicos, cajas, y otros artículos.\n" +
                        "2. Plástico: Se transforma en nuevos envases, productos plásticos o fibras textiles.\n" +
                        "3. Vidrio: Se puede reciclar indefinidamente sin perder calidad, y se utiliza para fabricar nuevos envases de vidrio.\n" +
                        "4. Metales: Los metales reciclados se utilizan para producir nuevos productos metálicos, como latas o componentes industriales.\n" +
                        "5. Electrodomésticos y dispositivos electrónicos: Los componentes valiosos, como los metales y plásticos, se separan para ser reciclados.\n\n" +
                        "El reciclaje no solo ayuda a reducir los residuos y la contaminación, sino que también contribuye al ahorro de energía y a la sostenibilidad de los recursos naturales."+
                        "\n");
            } else {
                textoReciclaje.setVisibility(View.GONE);
            }
        }

    // Método para mostrar la información sobre las 3R
    public void toggleTresR(View view) {
        // Obtener el TextView donde se mostrará la información
        TextView textoTresR = view.findViewById(R.id.textoTresR);

        // Alternar la visibilidad del TextView
        if (textoTresR.getVisibility() == View.GONE) {
            textoReciclaje.setVisibility(View.GONE);
            textoBeneficiosReciclar.setVisibility(View.GONE);

            textoTresR.setVisibility(View.VISIBLE);
            textoTresR.setText("\nLas 3R son un conjunto de principios para el manejo responsable de los residuos y la conservación de los recursos naturales. Estas son:\n\n" +
                    "1. Reducir: Minimizar la cantidad de residuos generados, evitando el uso de productos desechables y optando por aquellos que puedan ser reutilizados o reciclados.\n" +
                    "2. Reutilizar: Darles un nuevo uso a los productos o materiales, lo que ayuda a alargar su vida útil y reduce la necesidad de consumir recursos adicionales.\n" +
                    "3. Reciclar: Proceso mediante el cual los materiales ya utilizados son transformados para convertirse en nuevos productos. Esto contribuye a la reducción de residuos y a la conservación de los recursos naturales.\n\n" +
                    "La práctica de las 3R es fundamental para promover un estilo de vida más sostenible y responsable con el medio ambiente. Implementarlas en nuestra vida diaria ayuda a disminuir la contaminación y el desperdicio de recursos naturales.\n");
        } else {
            textoTresR.setVisibility(View.GONE);
        }
    }

    // Método para mostrar los beneficios de reciclar
    public void toggleBeneficiosReciclar(View view) {
        // Obtener el TextView donde se mostrará la información
        TextView textoBeneficiosReciclar = view.findViewById(R.id.textoBeneficiosReciclar);

        // Alternar la visibilidad del TextView
        if (textoBeneficiosReciclar.getVisibility() == View.GONE) {
            textoReciclaje.setVisibility(View.GONE);
            textoTresR.setVisibility(View.GONE);

            textoBeneficiosReciclar.setVisibility(View.VISIBLE);
            textoBeneficiosReciclar.setText("\nLos beneficios del reciclaje son numerosos y son cruciales para la protección del medio ambiente. Algunos de los principales beneficios incluyen:\n\n" +
                    "1. Conservación de recursos naturales: El reciclaje reduce la necesidad de extraer recursos naturales para producir nuevos materiales, lo que ayuda a conservar los recursos de la Tierra.\n" +
                    "2. Reducción de residuos: Reciclar ayuda a disminuir la cantidad de desechos que se generan y que terminan en vertederos, lo que reduce la contaminación.\n" +
                    "3. Ahorro de energía: El proceso de reciclaje consume menos energía en comparación con la fabricación de productos a partir de materiales vírgenes, lo que contribuye a la reducción de la huella de carbono.\n" +
                    "4. Generación de empleos: La industria del reciclaje crea empleo en áreas como la recolección, el procesamiento y la venta de materiales reciclados.\n" +
                    "5. Protección de la fauna y flora: Reducir la cantidad de residuos y desechos tóxicos ayuda a proteger la biodiversidad y los ecosistemas.\n\n" +
                    "El reciclaje, por lo tanto, juega un papel fundamental en la creación de un entorno más limpio, saludable y sostenible.\n");
        } else {
            textoBeneficiosReciclar.setVisibility(View.GONE);
        }
    }

    // Método para alternar la visibilidad de los TextViews
    private void toggleVisibility(TextView textView) {
        if (textView.getVisibility() == View.GONE) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    public void cerrar(View view) {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);

        finish();

    }

}


