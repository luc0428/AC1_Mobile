package com.example.ac1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etNome, etValor, etDescricao, etData;
    Spinner spPagamento, spCategoria, spFiltro;
    CheckBox swPago;
    Button btnSalvar;
    ListView listView;

    BancoHelper db;
    int idSelecionado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new BancoHelper(this);

        etNome = findViewById(R.id.etNome);
        etValor = findViewById(R.id.etValor);

        etDescricao = findViewById(R.id.etDescricao);
        spPagamento = findViewById(R.id.spPagamento);
        spCategoria = findViewById(R.id.spCategoria);
        spFiltro = findViewById(R.id.spFiltro);
        swPago = findViewById(R.id.swPago);
        btnSalvar = findViewById(R.id.btnSalvar);

        listView = findViewById(R.id.listView);
        etData = findViewById(R.id.etData);

        configurarSpinners();

        btnSalvar.setOnClickListener(v -> salvar());

        spFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                carregarLista();
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        listView.setOnItemClickListener((parent, view, pos, id) -> editar(pos));

        listView.setOnItemLongClickListener((parent, view, pos, id) -> {
            excluir(pos);
            return true;
        });

        carregarLista();
    }

    private void salvar() {
        String nome = etNome.getText().toString();
        String data = etData.toString().toString();
        String categoria = spCategoria.getSelectedItem().toString();
        String valorStr = etValor.getText().toString();
        String descricao = etDescricao.getText().toString();
        String pagamento = spPagamento.toString();
        Boolean pago = swPago.isChecked();


        if (nome.isEmpty() || valorStr.isEmpty() || categoria.isEmpty() || descricao.isEmpty() || data.isEmpty() ) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor = Double.parseDouble(valorStr);

        Despesa d = new Despesa(
                idSelecionado,
                nome,
                pagamento,
                descricao,
                categoria,
                valor,
                data,
                pago
        );

        if (idSelecionado == -1) {
            db.inserir(d);
        } else {
            db.atualizar(d);
            idSelecionado = -1;
        }

        limparCampos();
        carregarLista();
    }

    private void carregarLista() {
        String filtro = spFiltro.getSelectedItem().toString();
        List<Despesa> lista = db.listar(filtro);

        ArrayAdapter<Despesa> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                lista
        );

        listView.setAdapter(adapter);
    }

    private void editar(int pos) {
        Despesa d = (Despesa) listView.getItemAtPosition(pos);

        idSelecionado = d.getId();
        etNome.setText(d.getNome());
        etValor.setText(String.valueOf(d.getValor()));
        etDescricao.setText(d.getDescricao());
        etData.setText(d.getData());
        swPago.setChecked(d.isPago());

    }

    private void excluir(int pos) {
        Despesa d = (Despesa) listView.getItemAtPosition(pos);
        db.excluirDespesa(d.getId());
        carregarLista();
    }

    private void limparCampos() {
        etNome.setText("");
        etValor.setText("");
        etDescricao.setText("");
        swPago.setChecked(false);
    }

    private void configurarSpinners() {
        String[] pagamento = {"Pix", "Credito", "Debito"};
        String[] categoria = {"Alimentação", "Saude", "Contas"};
        String[] filtro = {"Todos", "Alimentação", "Saude", "Contas"};

        spPagamento.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, pagamento));

        spCategoria.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, categoria));

        spFiltro.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, filtro));
    }
}