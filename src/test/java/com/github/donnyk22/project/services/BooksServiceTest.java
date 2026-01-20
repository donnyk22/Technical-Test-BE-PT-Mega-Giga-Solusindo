package com.github.donnyk22.project.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import com.github.donnyk22.project.exceptions.BadRequestException;
import com.github.donnyk22.project.exceptions.ResourceNotFoundException;
import com.github.donnyk22.project.models.dtos.BooksDto;
import com.github.donnyk22.project.models.dtos.FindResponse;
import com.github.donnyk22.project.models.entities.Books;
import com.github.donnyk22.project.models.entities.Categories;
import com.github.donnyk22.project.models.forms.BookAddForm;
import com.github.donnyk22.project.models.forms.BookEditForm;
import com.github.donnyk22.project.models.forms.BookFindForm;
import com.github.donnyk22.project.repositories.BooksRepository;
import com.github.donnyk22.project.services.books.BooksServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BooksServiceTest {

    @InjectMocks
    private BooksServiceImpl booksService;

    @Mock
    private BooksRepository booksRepository;

    // ================= CREATE =================
    
    @Test
    void create_shouldSaveBookAndReturnDto() throws Exception {
        BookAddForm form = new BookAddForm()
            .setTitle("Clean Code")
            .setAuthor("Robert C. Martin")
            .setPrice(BigDecimal.valueOf(100_000))
            .setStock(10);

        MultipartFile image = mock(MultipartFile.class);

        byte[] imageBytes = "fake-image-content".getBytes();

        when(image.getBytes()).thenReturn(imageBytes);

        when(booksRepository.save(any(Books.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        BooksDto result = booksService.create(form, image);

        assertNotNull(result);
        assertEquals("Clean Code", result.getTitle());
        assertEquals("Robert C. Martin", result.getAuthor());
        assertEquals(BigDecimal.valueOf(100_000), result.getPrice());

        verify(booksRepository).save(any(Books.class));
    }

    // ================= FIND =================

    @Test
    void find_shouldReturnPagedBooks_whenKeywordAndCategoryProvided() {
        // given
        BookFindForm params = new BookFindForm();
        params.setPage(0);
        params.setSize(2);
        params.setKeyword("clean");
        params.setCategoyId(1);

        Books book1 = new Books()
            .setId(1)
            .setTitle("Clean Code")
            .setAuthor("Robert Martin");

        Books book2 = new Books()
            .setId(2)
            .setTitle("The Clean Coder")
            .setAuthor("Robert Martin");

        Page<Books> page = new PageImpl<>(
            List.of(book1, book2),
            PageRequest.of(0, 2),
            4
        );

        when(booksRepository.findAll(any(Specification.class), any(Pageable.class)))
            .thenReturn(page);

        // when
        FindResponse<BooksDto> result = booksService.find(params);

        // then
        assertNotNull(result);
        assertEquals(2, result.getRecords().size());
        assertEquals(2, result.getTotalPage());
        assertEquals(4, result.getTotalItem());
        assertTrue(result.getHasNext());
        assertFalse(result.getHasPrev());

        verify(booksRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void find_shouldReturnAllBooks_whenNoFilterProvided() {
        // given
        BookFindForm params = new BookFindForm();
        params.setPage(0);
        params.setSize(10);
        params.setKeyword(null);
        params.setCategoyId(null);

        Books book = new Books()
            .setId(1)
            .setTitle("Refactoring")
            .setAuthor("Martin Fowler");

        Page<Books> page = new PageImpl<>(
            List.of(book),
            PageRequest.of(0, 10),
            1
        );

        when(booksRepository.findAll(any(Specification.class), any(Pageable.class)))
            .thenReturn(page);

        // when
        FindResponse<BooksDto> result = booksService.find(params);
        
        // then
        assertNotNull(result);
        assertEquals(1, result.getRecords().size());
        assertEquals(1, result.getTotalItem());
        assertEquals(1, result.getTotalPage());
        assertFalse(result.getHasNext());
        assertFalse(result.getHasPrev());

        verify(booksRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    // ================= FIND ONE =================

    @Test
    void findOne_shouldThrowBadRequest_whenIdIsNull() {
        assertThrows(
            BadRequestException.class,
            () -> booksService.findOne(null)
        );

        verifyNoInteractions(booksRepository);
    }

    @Test
    void findOne_shouldThrowNotFound_whenBookDoesNotExist() {
        when(booksRepository.findById(99))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> booksService.findOne(99)
        );

        verify(booksRepository).findById(99);
    }

    @Test
    void findOne_shouldReturnBook_whenBookExists() {
        // given
        Books book = new Books()
            .setId(1)
            .setTitle("Clean Code")
            .setAuthor("Robert C. Martin")
            .setCategory(new Categories().setId(1))
            .setOrderItems(List.of());

        when(booksRepository.findById(1))
            .thenReturn(Optional.of(book));

        // when
        BooksDto result = booksService.findOne(1);

        // then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Clean Code", result.getTitle());

        verify(booksRepository).findById(1);
    }

    // ================= UPDATE =================

    @Test
    void update_shouldThrowBadRequest_whenIdIsNull() {
        assertThrows(
            BadRequestException.class,
            () -> booksService.update(null, new BookEditForm(), null)
        );

        verifyNoInteractions(booksRepository);
    }

    @Test
    void update_shouldThrowNotFound_whenBookDoesNotExist() {
        Integer bookId = 99;

        when(booksRepository.findById(bookId))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> booksService.update(bookId, new BookEditForm(), null)
        );

        verify(booksRepository).findById(bookId);
        verify(booksRepository, never()).save(any());
    }

    @Test
    void update_shouldUpdateBookAndReturnDto() throws Exception {
        // given
        Integer bookId = 1;

        BookEditForm form = new BookEditForm();
        form.setTitle("Clean Code (Updated)");
        form.setAuthor("Robert C. Martin");
        form.setPrice(BigDecimal.valueOf(200));
        form.setStock(20);

        MultipartFile image = mock(MultipartFile.class);
        byte[] imageBytes = "fake-image-content".getBytes();

        Books existingBook = new Books()
            .setId(bookId)
            .setTitle("Old Title");

        when(image.getBytes()).thenReturn(imageBytes);

        when(booksRepository.findById(bookId))
            .thenReturn(Optional.of(existingBook));

        when(booksRepository.save(any(Books.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        BooksDto result = booksService.update(bookId, form, image);

        // then
        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Clean Code (Updated)", result.getTitle());
        assertEquals("Robert C. Martin", result.getAuthor());

        verify(booksRepository).findById(bookId);
        verify(booksRepository).save(any(Books.class));
    }

    // ================= DELETE =================

    @Test
    void delete_shouldThrowBadRequest_whenIdIsNull() {
        assertThrows(
            BadRequestException.class,
            () -> booksService.delete(null)
        );

        verifyNoInteractions(booksRepository);
    }
    
    @Test
    void delete_shouldThrowNotFound_whenBookDoesNotExist() {
        Integer bookId = 99;

        when(booksRepository.findById(bookId))
            .thenReturn(Optional.empty());

        assertThrows(
            ResourceNotFoundException.class,
            () -> booksService.delete(bookId)
        );

        verify(booksRepository).findById(bookId);
        verify(booksRepository, never()).deleteById(any());
    }

    @Test
    void delete_shouldDeleteBookAndReturnDto_whenBookExists() {
        // given
        Integer bookId = 1;

        Books book = new Books()
            .setId(bookId)
            .setTitle("Clean Code")
            .setAuthor("Robert C. Martin");

        when(booksRepository.findById(bookId))
            .thenReturn(Optional.of(book));

        // when
        BooksDto result = booksService.delete(bookId);

        // then
        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Clean Code", result.getTitle());

        verify(booksRepository).findById(bookId);
        verify(booksRepository).deleteById(bookId);
    }
}
