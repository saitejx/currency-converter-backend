
package com.teja.currency_converter.service;

import com.teja.currency_converter.model.Conversion;
import com.teja.currency_converter.repository.ConversionRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversionService {

    private final ConversionRepository repository;

    // Constructor injection
    public ConversionService(ConversionRepository repository) {
        this.repository = repository;
    }

    // CREATE: Save a new conversion record
    public Conversion create(Conversion conversion) {
        return repository.save(conversion);
    }

    // READ ALL: Retrieve all conversion records
    public List<Conversion> getAll() {
        return repository.findAll();
    }

    // READ ONE: Retrieve a conversion by ID
    public Conversion getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Conversion not found with ID: " + id
                        )
                );
    }

    // UPDATE: Update an existing conversion record
    public Conversion update(Long id, Conversion updated) {

        Conversion existing = getById(id);

        existing.setAmount(updated.getAmount());
        existing.setFromCurrency(updated.getFromCurrency());
        existing.setToCurrency(updated.getToCurrency());
        existing.setExchangeRate(updated.getExchangeRate());
        existing.setConvertedAmount(updated.getConvertedAmount());

        return repository.save(existing);
    }

    // DELETE: Delete a conversion record by ID
    public void delete(Long id) {
        Conversion existing = getById(id);
        repository.delete(existing);
    }
}
