package com.rookiesquad.excelparsing.repository;

import com.rookiesquad.excelparsing.entity.ReconciliationData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReconciliationDataRepository<T extends ReconciliationData> extends JpaRepository<T, Long> {

}
