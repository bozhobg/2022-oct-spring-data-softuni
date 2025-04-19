package com.example.advquerying.service.impl;

import com.example.advquerying.constant.FormatTemplates;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repository.ShampooRepository;
import com.example.advquerying.service.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public String getAsStringOutputShampoosBySizeOrderedById(Size size) {

        return this.shampooRepository.findAllBySizeOrderById(size)
                .stream()
                .map(s -> String.format(
                        FormatTemplates.SHAMPOO_BRAND_SIZE_PRICE,
                        s.getBrand(), s.getSize().name(), s.getPrice().toString()
                ))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
