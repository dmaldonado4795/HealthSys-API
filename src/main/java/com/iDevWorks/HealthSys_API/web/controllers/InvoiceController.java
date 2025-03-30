package com.iDevWorks.HealthSys_API.web.controllers;

import com.iDevWorks.HealthSys_API.common.helper.ResponseHelper;
import com.iDevWorks.HealthSys_API.common.util.MapperObjectUtil;
import com.iDevWorks.HealthSys_API.domain.entities.AppointmentEntity;
import com.iDevWorks.HealthSys_API.domain.entities.InvoiceEntity;
import com.iDevWorks.HealthSys_API.domain.services.IAppointmentService;
import com.iDevWorks.HealthSys_API.domain.services.IInvoiceService;
import com.iDevWorks.HealthSys_API.web.dtos.InvoiceDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.iDevWorks.HealthSys_API.common.api.ApiPath.PATH_V1;
import static com.iDevWorks.HealthSys_API.common.schema.Schema.BEARER_SCHEMA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = PATH_V1 + "/invoice")
@SecurityRequirement(name = BEARER_SCHEMA)
public class InvoiceController {
    private final IAppointmentService appointmentService;
    private final IInvoiceService invoiceService;

    public InvoiceController(IAppointmentService appointmentService, IInvoiceService invoiceService) {
        this.appointmentService = appointmentService;
        this.invoiceService = invoiceService;
    }

    @GetMapping(path = "find-all")
    public ResponseEntity<Map<String, Object>> getInvoices() {
        Map<String, Object> response = new HashMap<>();
        List<InvoiceEntity> invoices = invoiceService.findAll();
        if (!invoices.isEmpty()) {
            response.put(ResponseHelper.DATA_KEY, invoices);
            return ResponseEntity.ok(response);
        } else {
            response.put(ResponseHelper.MESSAGE_KEY, ResponseHelper.NoRegisteredItem("invoices"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping(path = "find-by-id/{id}")
    public ResponseEntity<Map<String, Object>> getInvoice(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<InvoiceEntity> invoice = invoiceService.findById(id);
        if (invoice.isPresent()) {
            response.put(ResponseHelper.DATA_KEY, invoice);
            return ResponseEntity.ok(response);
        } else {
            response.put(ResponseHelper.MESSAGE_KEY, ResponseHelper.ItemNotFound("Invoice"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping(path = "save")
    public ResponseEntity<Map<String, Object>> saveInvoice(@Valid @RequestBody InvoiceDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<AppointmentEntity> appointment = appointmentService
                    .findById(dto.getAppointment().getAppointmentId());
            if (appointment.isEmpty()) {
                response.put(ResponseHelper.MESSAGE_KEY, ResponseHelper.ItemNotFound("Appointment"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            InvoiceEntity entity = invoiceService.save(MapperObjectUtil.toInvoice(0, dto));
            response.put(ResponseHelper.DATA_KEY, entity);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put(ResponseHelper.MESSAGE_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (EntityNotFoundException e) {
            response.put(ResponseHelper.MESSAGE_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put(ResponseHelper.MESSAGE_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping(path = "update/{id}")
    public ResponseEntity<Map<String, Object>> updateInvoice(@PathVariable long id,
            @Valid @RequestBody InvoiceDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<AppointmentEntity> appointment = appointmentService
                    .findById(dto.getAppointment().getAppointmentId());
            if (appointment.isEmpty()) {
                response.put(ResponseHelper.MESSAGE_KEY, ResponseHelper.ItemNotFound("Appointment"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            InvoiceEntity entity = invoiceService.update(MapperObjectUtil.toInvoice(id, dto));
            response.put(ResponseHelper.DATA_KEY, entity);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put(ResponseHelper.MESSAGE_KEY, "Invalid input: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (EntityNotFoundException e) {
            response.put(ResponseHelper.MESSAGE_KEY, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put(ResponseHelper.MESSAGE_KEY, "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
