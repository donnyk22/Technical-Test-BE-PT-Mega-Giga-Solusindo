package com.github.donnyk22.project.services.books;

import org.springframework.web.multipart.MultipartFile;

import com.github.donnyk22.project.models.dtos.BooksDto;
import com.github.donnyk22.project.models.dtos.FindResponse;
import com.github.donnyk22.project.models.forms.BookAddForm;
import com.github.donnyk22.project.models.forms.BookEditForm;
import com.github.donnyk22.project.models.forms.BookFindForm;

public interface BooksService {
    BooksDto create(BookAddForm form, MultipartFile image) throws Exception;
    FindResponse<BooksDto> find(BookFindForm body) throws Exception;
    BooksDto findOne(Integer id) throws Exception;
    BooksDto update(Integer id, BookEditForm form, MultipartFile image) throws Exception;
    BooksDto delete(Integer id) throws Exception;
}
