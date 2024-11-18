package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.helpers.ResponseHelper;
import com.iDevWorks.HealthSys_API.common.utils.ObjectMappingUtil;
import com.iDevWorks.HealthSys_API.domain.entities.AppointmentEntity;
import com.iDevWorks.HealthSys_API.domain.entities.InvoiceEntity;
import com.iDevWorks.HealthSys_API.domain.services.IAppointmentService;
import com.iDevWorks.HealthSys_API.domain.services.IInvoiceService;
import com.iDevWorks.HealthSys_API.web.dtos.InvoiceDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "invoice")
public class InvoiceController {
    @Autowired
    private IAppointmentService appointmentService;
    @Autowired
    private IInvoiceService invoiceService;

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getInvoices() {
        Map<String, Object> resp = new HashMap<>();
        List<InvoiceEntity> invoices = invoiceService.findAll();
        if (!invoices.isEmpty()) {
            resp.put(ResponseHelper.DATA_KEY, invoices);
            return ResponseEntity.ok(resp);
        } else {
            resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.NoRegisteredItem("invoices"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @GetMapping(path = "find-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getInvoice(@PathVariable long id) {
        Map<String, Object> resp = new HashMap<>();
        Optional<InvoiceEntity> invoice = invoiceService.findById(id);
        if (invoice.isPresent()) {
            resp.put(ResponseHelper.DATA_KEY, invoice);
            return ResponseEntity.ok(resp);
        } else {
            resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("Invoice"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
        }
    }

    @PostMapping(path = "save")
    public ResponseEntity<Map<String, Object>> saveInvoice(@Valid @RequestBody InvoiceDto dto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            Optional<AppointmentEntity> appointment = appointmentService.findById(dto.getAppointment().getAppointmentId());
            if (appointment.isEmpty()) {
                resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("Appointment"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
            }

            InvoiceEntity entity = invoiceService.save(ObjectMappingUtil.toInvoice(0, dto));
            resp.put(ResponseHelper.DATA_KEY, entity);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            resp.put(ResponseHelper.ERROR_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (EntityNotFoundException e) {
            resp.put(ResponseHelper.ERROR_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (Exception e) {
            resp.put(ResponseHelper.ERROR_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<Map<String, Object>> updateInvoice(@PathVariable long id, @Valid @RequestBody InvoiceDto dto) {
        Map<String, Object> resp = new HashMap<>();
        try {
            Optional<AppointmentEntity> appointment = appointmentService.findById(dto.getAppointment().getAppointmentId());
            if (appointment.isEmpty()) {
                resp.put(ResponseHelper.ERROR_KEY, ResponseHelper.ItemNotFound("Appointment"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
            }

            InvoiceEntity entity = invoiceService.update(ObjectMappingUtil.toInvoice(id, dto));
            resp.put(ResponseHelper.DATA_KEY, entity);
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException e) {
            resp.put(ResponseHelper.ERROR_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (EntityNotFoundException e) {
            resp.put(ResponseHelper.ERROR_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } catch (Exception e) {
            resp.put(ResponseHelper.ERROR_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }
}
