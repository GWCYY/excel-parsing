package com.rookiesquad.excelparsing.entity;

import jakarta.persistence.*;

import java.io.Serial;

@Entity
@Table(name = "excel_bill_parsing_data", schema = "excel", uniqueConstraints = {@UniqueConstraint(name = "unique_idx_id_card_number",
        columnNames = {"id_card_number"})})
public class BillParsingData extends ReconciliationData {

    @Serial
    private static final long serialVersionUID = -3601830221662600660L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
