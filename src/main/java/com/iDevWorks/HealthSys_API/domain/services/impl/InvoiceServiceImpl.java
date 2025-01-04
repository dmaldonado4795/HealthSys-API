package com.iDevWorks.HealthSys_API.domain.services.impl;

import com.iDevWorks.HealthSys_API.domain.entities.InvoiceEntity;
import com.iDevWorks.HealthSys_API.domain.services.IInvoiceService;
import com.iDevWorks.HealthSys_API.infrastructure.repositories.InvoiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements IInvoiceService {
    private final InvoiceRepository repository;

    public InvoiceServiceImpl(InvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<InvoiceEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<InvoiceEntity> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public InvoiceEntity save(InvoiceEntity entity) {
        return repository.save(entity);
    }

    @Override
    public InvoiceEntity update(InvoiceEntity entity) {
        return repository.findById(entity.getInvoiceId()).map(resp -> {
            if (!Objects.equals(resp.getTotalAmount(), entity.getTotalAmount()))
                resp.setTotalAmount(entity.getTotalAmount());
            if (!Objects.equals(resp.getDate(), entity.getDate()))
                resp.setDate(entity.getDate());
            if (!Objects.equals(resp.getAppointment(), entity.getAppointment()))
                resp.setAppointment(entity.getAppointment());
            return repository.save(resp);
        }).orElseThrow(() -> new EntityNotFoundException("Invoice not found with id"));
    }
}
