package uz.nt.uzumproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.uzumproject.dto.CategoryDto;
import uz.nt.uzumproject.dto.ProductDto;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.model.Category;
import uz.nt.uzumproject.repository.CategoryRepository;
import uz.nt.uzumproject.service.mapper.CategoryMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.nt.uzumproject.service.validator.AppStatusCodes.*;
import static uz.nt.uzumproject.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public final CategoryMapper categoryMapper;
    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryDto.getParentId());

        if (categoryOptional.isEmpty()) {
            return ResponseDto.<CategoryDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message("Category with provided PARENT ID " + categoryDto.getParentId() + " is not found!")
                    .data(categoryDto)
                    .build();
        }

        try{
            return ResponseDto.<CategoryDto>builder()
                    .message(OK)
                    .success(true)
                    .data(categoryMapper.toDto(categoryRepository
                            .save(categoryMapper
                                    .toEntity(categoryDto))))
                    .build();

        }
        catch (Exception e){
            return ResponseDto.<CategoryDto>builder()
                    .message(DATABASE_ERROR + " " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .data(categoryDto)
                    .build();
        }
    }

    public ResponseDto<List<CategoryDto>> getSubCategories(Integer id) {

        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isEmpty()) {
            return ResponseDto.<List<CategoryDto>>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message("Category with provided ID " + id + " is not found!")
                    .data(null)
                    .build();
        }

        return ResponseDto.<List<CategoryDto>>builder()
                .message("Subcategories of " + categoryOptional.get().getName() + " found by id " + id)
                .success(true)
                .data(categoryRepository.findAllByParentId(id).stream().map(categoryMapper::toDto).collect(Collectors.toList()))
                .build();
    }
}