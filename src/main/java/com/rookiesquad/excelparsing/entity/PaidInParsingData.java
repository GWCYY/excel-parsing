package com.rookiesquad.excelparsing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "excel_paid_in_parsing_data", schema = "excel", uniqueConstraints = {@UniqueConstraint(name = "unique_idx_id_card_number",
        columnNames = {"id_card_number"})})
public class PaidInParsingData extends ReconciliationData implements Serializable {

    @Serial
    private static final long serialVersionUID = -4531609410742971578L;

}
