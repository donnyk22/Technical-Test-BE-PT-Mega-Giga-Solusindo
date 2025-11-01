package com.github.donnyk22.project.services.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.donnyk22.project.models.dtos.BooksDto;
import com.github.donnyk22.project.models.dtos.FindResponse;
import com.github.donnyk22.project.models.entities.Books;
import com.github.donnyk22.project.models.forms.BookAddForm;
import com.github.donnyk22.project.models.forms.BookEditForm;
import com.github.donnyk22.project.models.forms.BookFindForm;
import com.github.donnyk22.project.models.mappers.BooksMapper;
import com.github.donnyk22.project.repositories.BooksRepository;
import com.github.donnyk22.project.services.categories.CategoriesServiceImpl;
import com.github.donnyk22.project.utils.ImageUtil;

@Service
@Transactional
public class BooksServiceImpl implements BooksService{

    private static final Logger logger = LoggerFactory.getLogger(CategoriesServiceImpl.class);

    @Autowired
    BooksRepository booksRepository;

    @Override
    public BooksDto create(BookAddForm form, MultipartFile image) throws Exception {
        if (image.isEmpty()) {
            logger.error("File is empty");
            throw new Exception("File is empty");
        }
        Books newBook = new Books()
            .setTitle(form.getTitle())
            .setAuthor(form.getAuthor())
            .setPrice(form.getPrice())
            .setStock(form.getStock())
            .setYear(form.getYear())
            .setImageBase64(ImageUtil.ToBase64(image));
        newBook.setCategoryId(form.getCategoryId());
        booksRepository.save(newBook);
        logger.info("Book entry submitted successfully");
        return BooksMapper.toBaseDto(newBook);
    }

    @Override
    public FindResponse<BooksDto> find(BookFindForm body) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }

    @Override
    public BooksDto findOne(Integer id) throws Exception {
        if (id == null){
            logger.error("Id is required");
            throw new Exception("Id is required");
        }
        Books book = booksRepository.findById(id).orElse(null);
        if(book == null){
            logger.error("Book not found");
            throw new Exception("Book not found");
        }
        logger.info("Book found: " + id);
        return BooksMapper.toDetailDto(book);
    }

    @Override
    public BooksDto update(Integer id, BookEditForm form, MultipartFile image) throws Exception {
        Books book = booksRepository.findById(id).orElse(null);
        if (book == null){
            logger.error("Book not found: " + id);
            throw new Exception("Book not found");
        }
        book.setTitle(form.getTitle())
            .setAuthor(form.getAuthor())
            .setPrice(form.getPrice())
            .setStock(form.getStock())
            .setYear(form.getYear())
            .setCategoryId(form.getCategoryId());
        if (!image.isEmpty()){
            book.setImageBase64(ImageUtil.ToBase64(image));
        }
        booksRepository.save(book);
        return BooksMapper.toBaseDto(book);
    }

    @Override
    public BooksDto delete(Integer id) throws Exception {
        if(id == null){
            logger.error("Id is required");
            throw new Exception("Id is required");
        }
        Books book = booksRepository.findById(id).orElse(null);
        if (book == null){
            logger.error("Book not found: " + id);
            throw new Exception("Book not found");
        }
        booksRepository.deleteById(id);
        logger.info("Book deleted successfuly: " + id);
        return BooksMapper.toBaseDto(book);
    }
    
}
