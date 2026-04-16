package com.example.ac1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class BancoHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "despesa.db";
    private static final int DATABASE_VERSION = 1;

    // Tabela
    private static final String TABLE_NAME = "despesa";

    // Colunas
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_DESCRICAO = "descricao";
    private static final String COLUMN_CATEGORIA = "Categoria";
    private static final String COLUMN_VALOR = "valor";
    private static final String COLUMN_PAGAMENTO = "pagamento";
    private static final String COLUMN_PAGO = "pago";

    private static final String COLUMN_DATA = "data";

    public BancoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOME + " TEXT NOT NULL, "
                + COLUMN_DESCRICAO + " TEXT NOT NULL, "
                + COLUMN_CATEGORIA + " TEXT NOT NULL, "
                + COLUMN_VALOR + " REAL NOT NULL, "
                + COLUMN_PAGAMENTO + " TEXT, "
                + COLUMN_PAGO + " INTEGER,"
                + COLUMN_DATA + " TEXT NOT NULL"
                + ");";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // INSERIR
    public long inserirDespesa(String nome, String descricao, String categoria, String data,
                                  double valor, String pagamento, boolean pago) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_CATEGORIA, categoria);
        values.put(COLUMN_VALOR, valor);
        values.put(COLUMN_DESCRICAO, descricao);
        values.put(COLUMN_PAGAMENTO, pagamento);
        values.put(COLUMN_DATA, data);
        values.put(COLUMN_PAGO,pago ? 1 : 0);

        return db.insert(TABLE_NAME, null, values);
    }

    // LISTAR (com filtro)
    public Cursor listarDespesa(String categoriaFiltro) {
        SQLiteDatabase db = this.getReadableDatabase();

        if (categoriaFiltro.equals("Todos")) {
            return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        } else {
            return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE categoria=?",
                    new String[]{categoriaFiltro});
        }
    }

    // 🔹 ATUALIZAR
    public int atualizarDespesa(int id, String nome, String categoria,String data,
                                   String descricao, double valor,
                                   String pagamento, boolean pago) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_CATEGORIA, categoria);
        values.put(COLUMN_VALOR, valor);
        values.put(COLUMN_DESCRICAO, descricao);
        values.put(COLUMN_PAGAMENTO, pagamento);
        values.put(COLUMN_DATA, data);
        values.put(COLUMN_PAGO,pago ? 1 : 0);

        return db.update(TABLE_NAME, values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
    }

    // EXCLUIR
    public int excluirDespesa(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
    }

    public void atualizar(Despesa d) {
    }

    public void inserir(Despesa d) {
    }

    public List<Despesa> listar(String filtro) {
        return java.util.Collections.emptyList();
    }
}