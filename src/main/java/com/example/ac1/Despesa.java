package com.example.ac1;

public class Despesa {
    private int id;
    private String nome;
    private String pagamento;
    private String descricao;
    private String categoria;
    private double valor;
    private String data;
    private boolean pago;

    public Despesa() {}

    public Despesa(int id, String nome, String pagamento, String categoria, String data,
                    double valor, String descricao, boolean pago) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.valor = valor;
        this.pago = pago;
        this.pagamento = pagamento;
        this.data = data;
    }

    public int getId() {
        return 0;
    }

    public int getDescricao() {
        return 0;
    }

    public int getPagamento() {
        return 0;
    }

    public int getData() {
        return 0;
    }

    public int getCategoria() {return 0;}

    public boolean isPago() {
        return true;
    }

    public int getValor() {
        return 0;
    }

    public int getNome() {
        return 0;
    }

    // Getters e Setters
}