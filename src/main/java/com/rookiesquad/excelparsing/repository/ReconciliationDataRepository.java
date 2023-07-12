package com.rookiesquad.excelparsing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ReconciliationDataRepository<T, K> extends JpaRepository<T, K> {

}
