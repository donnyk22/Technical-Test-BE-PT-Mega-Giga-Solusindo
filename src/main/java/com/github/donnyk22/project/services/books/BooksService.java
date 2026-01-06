package com.github.donnyk22.project.services.books;

import org.springframework.web.multipart.MultipartFile;

import com.github.donnyk22.project.models.dtos.BooksDto;
import com.github.donnyk22.project.models.dtos.FindResponse;
import com.github.donnyk22.project.models.forms.BookAddForm;
import com.github.donnyk22.project.models.forms.BookEditForm;
import com.github.donnyk22.project.models.forms.BookFindForm;

public interface BooksService {
    BooksDto create(BookAddForm form, MultipartFile image);
    FindResponse<BooksDto> find(BookFindForm body);
    BooksDto findOne(Integer id);
    BooksDto update(Integer id, BookEditForm form, MultipartFile image);
    BooksDto delete(Integer id);
}
