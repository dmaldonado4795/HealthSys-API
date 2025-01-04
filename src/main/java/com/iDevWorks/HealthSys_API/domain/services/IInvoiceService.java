package com.iDevWorks.HealthSys_API.domain.services;

import com.iDevWorks.HealthSys_API.domain.entities.InvoiceEntity;

import java.util.List;
import java.util.Optional;

/**
 * IInvoiceService provides a contract for managing invoice records.
 * This interface defines the methods required for CRUD operations on
 * invoice entities in the HealthSys API.
 */
public interface IInvoiceService {

    /**
     * Retrieves a list of all invoices.
     *
     * @return a {@link List} of {@link InvoiceEntity} objects representing all invoices.
     */
    List<InvoiceEntity> findAll();

    /**
     * Retrieves an invoice by its unique identifier.
     *
     * @param id the unique identifier of the invoice.
     * @return an {@link Optional} containing the {@link InvoiceEntity} if found,
     *         or an empty {@link Optional} if no invoice is found with the given ID.
     */
    Optional<InvoiceEntity> findById(long id);

    /**
     * Saves a new invoice to the database.
     *
     * @param entity the {@link InvoiceEntity} to save.
     * @return the saved {@link InvoiceEntity} including any generated identifiers.
     */
    InvoiceEntity save(InvoiceEntity entity);

    /**
     * Updates an existing invoice in the database.
     *
     * Note: The entity should already exist; otherwise, the behavior is undefined.
     *
     * @param entity the {@link InvoiceEntity} containing updated data.
     * @return the updated {@link InvoiceEntity}.
     */
    InvoiceEntity update(InvoiceEntity entity);
}
