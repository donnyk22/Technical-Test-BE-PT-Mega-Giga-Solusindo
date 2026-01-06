package com.github.donnyk22.project.services.categories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.donnyk22.project.exceptions.BadRequestException;
import com.github.donnyk22.project.exceptions.ResourceNotFoundException;
import com.github.donnyk22.project.models.dtos.CategoriesDto;
import com.github.donnyk22.project.models.entities.Categories;
import com.github.donnyk22.project.repositories.CategoriesRepository;

@ExtendWith(MockitoExtension.class)
public class CategoriesServiceTest {

    @InjectMocks
    private CategoriesServiceImpl categoriesService;

    @Mock
    private CategoriesRepository categoriesRepository;

    // ================= CREATE =================

    @Test
    void create_shouldThrowBadRequest_whenCategoryIsBlank() {
        assertThrows(
            BadRequestException.class,
            () -> categoriesService.create("")
        );
    }

    @Test
    void create_shouldReturnDto_whenCategoryIsValid() {
        Categories saved = new Categories().setName("Fantasy");

        when(categoriesRepository.save(any(Categories.class)))
            .thenReturn(saved);

        CategoriesDto result = categoriesService.create("Fantasy");

        assertNotNull(result);
        assertEquals("Fantasy", result.getName());
        verify(categoriesRepository).save(any(Categories.class));
    }

    // ================= FIND ONE =================

    @Test
    void findOne_shouldThrowBadRequest_whenIdIsNull() {
        assertThrows(
            BadRequestException.class,
            () -> categoriesService.findOne(null)
        );
    }

    @Test
    void findOne_shouldThrowNotFound_whenCategoryDoesNotExist() {
        when(categoriesRepository.findById(1))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> categoriesService.findOne(1)
        );
    }

    @Test
    void findOne_shouldReturnDto_whenCategoryExist() {
        Categories category = new Categories()
            .setId(1)
            .setName("Fantasy")
            .setBooks(new ArrayList<>());

        when(categoriesRepository.findById(1))
            .thenReturn(Optional.of(category));

        CategoriesDto result = categoriesService.findOne(1);

        assertNotNull(result);
        assertEquals("Fantasy", result.getName());
    }

    // ================= FIND ONE =================

    @Test
    void findAll_shouldReturnList_whenDataExists() {
        List<Categories> data = List.of(
            new Categories().setName("Fantasy"),
            new Categories().setName("Drama")
        );

        when(categoriesRepository.findAll()).thenReturn(data);

        List<CategoriesDto> result = categoriesService.findAll();

        assertEquals(2, result.size());
        assertEquals("Fantasy", result.get(0).getName());
    }

    // ================= UPDATE =================

    @Test
    void update_shouldThrowBadRequest_whenInvalidInput() {
        assertThrows(
            BadRequestException.class,
            () -> categoriesService.update(null, "")
        );
    }

    @Test
    void update_shouldThrowNotFound_whenCategoryMissing() {
        when(categoriesRepository.findById(1))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> categoriesService.update(1, "Fantasy")
        );
    }

    @Test
    void update_shouldReturnUpdatedDto_whenValid() {
        Categories category = new Categories().setId(1).setName("OldCategory");

        when(categoriesRepository.findById(1))
            .thenReturn(Optional.of(category));
        when(categoriesRepository.save(any()))
            .thenReturn(category);

        CategoriesDto result = categoriesService.update(1, "NewCategory");

        assertEquals("NewCategory", result.getName());
    }

    // ================= DELETE =================

    @Test
    void delete_shouldThrowNotFound_whenCategoryMissing() {
        when(categoriesRepository.findById(1))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> categoriesService.delete(1)
        );
    }

    @Test
    void delete_shouldReturnDto_whenCategoryExists() {
        Categories category = new Categories().setId(1).setName("Fantasy");

        when(categoriesRepository.findById(1))
            .thenReturn(Optional.of(category));

        CategoriesDto result = categoriesService.delete(1);

        assertNotNull(result);
        verify(categoriesRepository).deleteById(1);
    }

}
