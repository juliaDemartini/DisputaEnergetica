package com.example.transicaoenergetica;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditarJogadorDialogFragment extends DialogFragment {

    // 🔗 Interface de Callback para devolver os dados modificados para a tela principal
    public interface OnJogadorEditadoListener {
        void onJogadorSalvo(String novoNome, String novaCorHex);
    }

    private OnJogadorEditadoListener listener;
    private String nomeInicial, corSelecionadaHex;
    private ImageView ivPreviewPeao;
    private EditText etEditarNome;

    // Construtor estático para passar o nome e cor atuais do jogador ao abrir o pop-up
    public static EditarJogadorDialogFragment newInstance(String nome, String corHex, OnJogadorEditadoListener listener) {
        EditarJogadorDialogFragment fragment = new EditarJogadorDialogFragment();
        fragment.nomeInicial = nome;
        fragment.corSelecionadaHex = corHex;
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_editar_jogador, container, false);

        // Deixa o fundo transparente para respeitar as bordas arredondadas do CardView
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        // Inicializa os componentes do pop-up
        ivPreviewPeao = view.findViewById(R.id.ivPreviewPeao);
        etEditarNome = view.findViewById(R.id.etEditarNome);
        Button btnSalvar = view.findViewById(R.id.btnSalvarCustomizacao);

        View btnVermelho = view.findViewById(R.id.viewCorVermelho);
        View btnAzul = view.findViewById(R.id.viewCorAzul);
        View btnVerde = view.findViewById(R.id.viewCorVerde);
        View btnAmarelo = view.findViewById(R.id.viewCorAmarelo);

        // Preenche com os dados atuais recebidos
        etEditarNome.setText(nomeInicial);
        ivPreviewPeao.setColorFilter(Color.parseColor(corSelecionadaHex));

        // 🎨 Lógica dos cliques nos círculos coloridos (muda o preview na hora)
        btnVermelho.setOnClickListener(v -> atualizarCorPreview("#FF2E2E"));
        btnAzul.setOnClickListener(v -> atualizarCorPreview("#2E7DFF"));
        btnVerde.setOnClickListener(v -> atualizarCorPreview("#2EFF3A"));
        btnAmarelo.setOnClickListener(v -> atualizarCorPreview("#FFD42E"));

        // 🟩 Ação do botão salvar
        btnSalvar.setOnClickListener(v -> {
            String novoNome = etEditarNome.getText().toString().trim();
            if (novoNome.isEmpty()) {
                Toast.makeText(getContext(), "Digite um nome válido!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (listener != null) {
                listener.onJogadorSalvo(novoNome, corSelecionadaHex);
            }
            dismiss(); // Fecha o pop-up
        });

        return view;
    }

    private void atualizarCorPreview(String corHex) {
        this.corSelecionadaHex = corHex;
        ivPreviewPeao.setColorFilter(Color.parseColor(corHex));
    }

    // Garante que o diálogo fique com tamanho adequado no emulador
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