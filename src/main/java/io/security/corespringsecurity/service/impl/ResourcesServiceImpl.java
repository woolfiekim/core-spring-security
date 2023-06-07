package io.security.corespringsecurity.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.security.corespringsecurity.domain.entity.Resources;
import io.security.corespringsecurity.repository.ResourcesRepository;
import io.security.corespringsecurity.service.ResourcesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourcesServiceImpl implements ResourcesService {

    private final ResourcesRepository repository;

    @Transactional
    public Resources getResources(long id) {
        return repository.findById(id).orElse(new Resources());
    }

    @Transactional
    public List<Resources> getResources() {
        return repository.findAll(Sort.by(Sort.Order.asc("orderNum")));
    }

    @Transactional
    public void createResources(Resources resources){
        repository.save(resources);
    }

    @Transactional
    public void deleteResources(long id) {
        repository.deleteById(id);
    }
}
