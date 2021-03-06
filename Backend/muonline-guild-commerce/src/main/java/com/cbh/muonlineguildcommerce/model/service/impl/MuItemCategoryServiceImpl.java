package com.cbh.muonlineguildcommerce.model.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbh.muonlineguildcommerce.dto.request.MuItemCategoryRequest;
import com.cbh.muonlineguildcommerce.dto.response.MuItemCategoryResponse;
import com.cbh.muonlineguildcommerce.exception.MuItemCategoryNotFound;
import com.cbh.muonlineguildcommerce.mapper.MuItemCategoryMapper;
import com.cbh.muonlineguildcommerce.model.entity.MuItemCategory;
import com.cbh.muonlineguildcommerce.model.repository.MuItemCategoryRepository;
import com.cbh.muonlineguildcommerce.model.service.MuItemCategoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MuItemCategoryServiceImpl implements MuItemCategoryService {

	private final MuItemCategoryRepository muItemCategoryRepository;
	private final MuItemCategoryMapper muItemCategoryMapper;

	@Override
	@Transactional(readOnly = true)
	public Page<MuItemCategoryResponse> findAll(int page, int size) {
		Page<MuItemCategory> entities = muItemCategoryRepository.findAll(PageRequest.of(page, size));
		return entities.map(muItemCategoryMapper::mapEntityToDto);
	}

	@Override
	@Transactional(readOnly = true)
	public MuItemCategoryResponse findOneById(Long id) {
		MuItemCategory muItemCategory = findMuItemCategoryById(id);
		return muItemCategoryMapper.mapEntityToDto(muItemCategory);
	}

	@Override
	@Transactional
	public MuItemCategoryResponse save(MuItemCategoryRequest muItemCategoryRequest) {
		MuItemCategory muItemCategory = muItemCategoryRepository
				.saveAndFlush(muItemCategoryMapper.mapToSave(muItemCategoryRequest));
		return muItemCategoryMapper.mapEntityToDto(muItemCategory);
	}

	@Override
	@Transactional
	public MuItemCategoryResponse edit(MuItemCategoryRequest muItemCategoryRequest, Long id) {
		MuItemCategory muItemCategory = findMuItemCategoryById(id);
		muItemCategory = muItemCategoryRepository
				.save(muItemCategoryMapper.mapToEdit(muItemCategoryRequest, muItemCategory));
		return muItemCategoryMapper.mapEntityToDto(muItemCategory);
	}

	@Override
	@Transactional(readOnly = true)
	public MuItemCategory findMuItemCategoryById(Long id) {
		return muItemCategoryRepository.findById(id)
				.orElseThrow(() -> new MuItemCategoryNotFound("Mu Item Category with ID - " + id + " is not found"));
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		muItemCategoryRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MuItemCategoryResponse> findAll() {
		return muItemCategoryRepository.findByEnabledTrue().stream().map(muItemCategoryMapper::mapEntityToDto)
				.collect(Collectors.toList());
	}
}
