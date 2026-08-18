package com.example.transicaoenergetica; 

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment; //IMPORTANTE: Garante o funcionamento do .show()
import java.util.List;

public class PlacarDialogFragment extends DialogFragment {

    private LinearLayout llPlacarJogadoresContainer;
    private Button btnFecharPlacar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_placar, container, false);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        llPlacarJogadoresContainer = view.findViewById(R.id.llPlacarJogadoresContainer);
        btnFecharPlacar = view.findViewById(R.id.btnFecharPlacar);

        montarPainelRecursos();

        btnFecharPlacar.setOnClickListener(v -> dismiss());

        return view;
    }

    private void montarPainelRecursos() {
        if (llPlacarJogadoresContainer == null) return;
        llPlacarJogadoresContainer.removeAllViews();

        List<Jogador> listaJogadores = GameManager.getInstance().getListaJogadores();
        LayoutInflater inflater = LayoutInflater.from(getContext());

        if (listaJogadores != null) {
            for (Jogador j : listaJogadores) {
                //Usando o novo arquivo de layout exclusivo do pop-up
                View itemView = inflater.inflate(R.layout.item_recursos_popup, llPlacarJogadoresContainer, false);

                TextView tvNome = itemView.findViewById(R.id.tvPopUpPlacarNome);
                TextView tvMoedas = itemView.findViewById(R.id.tvPopUpPlacarMoedas);
                TextView tvEnergia = itemView.findViewById(R.id.tvPopUpPlacarEnergia);
                TextView tvSust = itemView.findViewById(R.id.tvPopUpPlacarSust);
                TextView tvPoluicao = itemView.findViewById(R.id.tvPopUpPlacarPoluicao);

                String emoji = "⚪ ";
                if (j.getCorPeao().equalsIgnoreCase("Vermelho")) emoji = "🔴 ";
                else if (j.getCorPeao().equalsIgnoreCase("Azul")) emoji = "🔵 ";
                else if (j.getCorPeao().equalsIgnoreCase("Amarelo")) emoji = "🟡 ";
                else if (j.getCorPeao().equalsIgnoreCase("Verde")) emoji = "🟢 ";

                if (tvNome != null) tvNome.setText(emoji + j.getNome());
                if (tvMoedas != null) tvMoedas.setText("💰 " + j.getMoedas());
                if (tvEnergia != null) tvEnergia.setText("⚡ " + j.getEnergia());
                if (tvSust != null) tvSust.setText("🌱 " + j.getSustentabilidade());
                if (tvPoluicao != null) tvPoluicao.setText("☣️ " + j.getPoluicao());

                llPlacarJogadoresContainer.addView(itemView);
            }
        }
    }
}
