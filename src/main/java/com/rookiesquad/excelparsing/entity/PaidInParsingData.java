package com.rookiesquad.excelparsing.entity;

import jakarta.persistence.*;

import java.io.Serial;

@Entity
@Table(name = "excel_paid_in_parsing_data", schema = "excel", uniqueConstraints = {@UniqueConstraint(name = "unique_idx_id_card_number",
        columnNames = {"id_card_number"})})
public class PaidInParsingData extends ReconciliationData {

    @Serial
    private static final long serialVersionUID = -4531609410742971578L;

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
