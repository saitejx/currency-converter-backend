
package com.teja.currency_converter.controller;

import com.teja.currency_converter.model.Conversion;
import com.teja.currency_converter.model.ConversionRequest;
import com.teja.currency_converter.service.ConversionService;
import com.teja.currency_converter.service.CurrencyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversions")
@CrossOrigin(origins = "*")
public class ConversionController {

    private final ConversionService conversionService;
    private final CurrencyService currencyService;

    public ConversionController(
            ConversionService conversionService,
            CurrencyService currencyService) {
        this.conversionService = conversionService;
        this.currencyService = currencyService;
    }

    // CREATE: Convert currency and save the result
    @PostMapping("/convert")
    public ResponseEntity<Conversion> convert(
            @RequestBody ConversionRequest request) {

        Conversion result = currencyService.convert(
                request.amount(),
                request.fromCurrency(),
                request.toCurrency()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // CREATE: Save a conversion record
    @PostMapping
    public ResponseEntity<Conversion> create(
            @RequestBody Conversion conversion) {

        Conversion saved = conversionService.create(conversion);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // READ ALL: Get conversion history
    @GetMapping
    public List<Conversion> getAll() {
        return conversionService.getAll();
    }

    // READ ONE: Get a conversion by ID
    @GetMapping("/{id}")
    public Conversion getById(@PathVariable Long id) {
        return conversionService.getById(id);
    }

    // UPDATE: Update an existing record
    @PutMapping("/{id}")
    public Conversion update(
            @PathVariable Long id,
            @RequestBody Conversion conversion) {

        return conversionService.update(id, conversion);
    }

    // DELETE: Delete a record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        conversionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
