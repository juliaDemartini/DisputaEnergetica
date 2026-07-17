package com.example.transicaoenergetica;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MoreInfoDialogFragment extends DialogFragment {

    public MoreInfoDialogFragment() {
        // Obrigatório vazio
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_more_info, container, false);

        // Remove fundos do sistema para aceitar as bordas arredondadas do CardView
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        ImageView btnFechar = view.findViewById(R.id.btnFecharInfo);
        Button btnEntendi = view.findViewById(R.id.btnEntendiInfo);

        // Fecha o diálogo com animação limpa ao clicar em qualquer um
        if (btnFechar != null) btnFechar.setOnClickListener(v -> dismiss());
        if (btnEntendi != null) btnEntendi.setOnClickListener(v -> dismiss());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }
}