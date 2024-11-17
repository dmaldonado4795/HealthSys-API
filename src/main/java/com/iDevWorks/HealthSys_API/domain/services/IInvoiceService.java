package com.iDevWorks.HealthSys_API.domain.services;

import com.iDevWorks.HealthSys_API.domain.entities.InvoiceEntity;

import java.util.List;
import java.util.Optional;

public interface IInvoiceService {
    List<InvoiceEntity> findAll();

    Optional<InvoiceEntity> findById(long id);

    InvoiceEntity save(InvoiceEntity entity);

    InvoiceEntity update(InvoiceEntity entity);
}
