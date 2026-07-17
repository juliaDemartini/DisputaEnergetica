package com.example.transicaoenergetica;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VencedorFragment extends Fragment {

    private String nomeVencedor;
    private String corVencedorHex;

    public static VencedorFragment newInstance(String nome, String corHex) {
        VencedorFragment fragment = new VencedorFragment();
        Bundle args = new Bundle();
        args.putString("vencedor_nome", nome);
        args.putString("vencedor_cor", corHex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nomeVencedor = getArguments().getString("vencedor_nome");
            corVencedorHex = getArguments().getString("vencedor_cor");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vencedor, container, false);

        TextView tvNome = view.findViewById(R.id.tvNomeVencedor);
        ImageView ivPeao = view.findViewById(R.id.ivTrofeuVencedor);
        Button btnSair = view.findViewById(R.id.btnVoltarMenu);

        tvNome.setText(nomeVencedor);
        if (corVencedorHex != null) {
            ivPeao.setColorFilter(Color.parseColor(corVencedorHex));
        }

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).trocarMusicaGlobal(R.raw.vencedor);
        }

        btnSair.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).trocarMusicaGlobal(R.raw.musica_menu);
            }
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainMenuFragment())
                    .commit();
        });

        return view;
    }
}