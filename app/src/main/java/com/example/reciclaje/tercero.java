package com.example.reciclaje;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class tercero extends Fragment {

    EditText edtAmount;
    Button btnPayment;

    String clientId = "AY_kB5RquYiaAJU1TXKK3aSGe-EYTODhQ2V14S5e7pCAzJIZ6RPAOgVG1xOxcg3ttvYZQO3ykhNedx_V";
    int PAYPAL_REQUEST_CODE = 123;
    public static PayPalConfiguration configuration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configura PayPal
        configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(clientId);

        // Inicia el servicio de PayPal
        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        getActivity().startService(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tercero, container, false);

        edtAmount = view.findViewById(R.id.edtAmount);
        btnPayment = view.findViewById(R.id.btnPayment);

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment();
            }
        });

        return view;
    }

    private void getPayment() {
        String amounts = edtAmount.getText().toString();

        // Verifica que el monto no esté vacío antes de proceder
        if (amounts.isEmpty()) {
            Toast.makeText(getActivity(), "Por favor, ingrese una cantidad", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            BigDecimal amount = new BigDecimal(amounts);
            PayPalPayment payment = new PayPalPayment(amount, "USD", "Donación", PayPalPayment.PAYMENT_INTENT_SALE);

            Intent intent = new Intent(getActivity(), PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

            startActivityForResult(intent, PAYPAL_REQUEST_CODE);
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Cantidad no válida", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (paymentConfirmation != null) {
                    try {
                        String paymentDetails = paymentConfirmation.toJSONObject().toString(4);
                        JSONObject object = new JSONObject(paymentDetails);
                        Toast.makeText(getActivity(), "Pago exitoso", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "Error procesando el pago: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Pago cancelado", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(getActivity(), "Pago inválido, por favor intente de nuevo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        // Detiene el servicio de PayPal al destruir el Fragment
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }
}
