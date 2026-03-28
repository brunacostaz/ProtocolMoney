package br.com.protocolmoney.model;

import java.time.LocalDate;

/*
Classe responsável pela descrição dos ativos e motor de regras para resgate
 */
public enum AssetType {

    CBDI("CDB Liquidez Diária", 0),
    CBP3M("CDB Pós Fixado 3 meses", 3),
    CBPE1Y("CDB Pré Fixado 1 ano", 12);

    private final String description;
    private final int monthsToMaturity;

    AssetType(String description, int monthsToMaturity) {
        this.description = description;
        this.monthsToMaturity = monthsToMaturity;
    }

    // Calcula a data de vencimento da aplicação com base nos prazos permitidos de cada ativo
    public LocalDate calculatedDueDate(LocalDate investmentDate) {
        return investmentDate.plusMonths(this.monthsToMaturity);
    }

    // Verifica se a data atual é igual ou maior que a de vencimento, para permitir ou não o resgate
    public boolean canRedemption(LocalDate dueDate) {
        return LocalDate.now().isEqual(dueDate) || LocalDate.now().isAfter(dueDate);
    }

    public String getDescription() {
        return description;
    }

    public int getMonthsToMaturity() {
        return monthsToMaturity;
    }

}
