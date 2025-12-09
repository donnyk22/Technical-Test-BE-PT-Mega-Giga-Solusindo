package com.github.donnyk22.project.services.books;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.github.donnyk22.project.models.dtos.BooksDto;
import com.github.donnyk22.project.models.dtos.FindResponse;
import com.github.donnyk22.project.models.entities.Books;
import com.github.donnyk22.project.models.enums.UserRoles;
import com.github.donnyk22.project.models.forms.BookAddForm;
import com.github.donnyk22.project.models.forms.BookEditForm;
import com.github.donnyk22.project.models.forms.BookFindForm;
import com.github.donnyk22.project.models.mappers.BooksMapper;
import com.github.donnyk22.project.repositories.BooksRepository;
import com.github.donnyk22.project.services.categories.CategoriesServiceImpl;
import com.github.donnyk22.project.utils.AuthExtractUtil;
import com.github.donnyk22.project.utils.ImageUtil;

@Service
@Transactional
public class BooksServiceImpl implements BooksService{

    private static final Logger logger = LoggerFactory.getLogger(CategoriesServiceImpl.class);

    @Autowired BooksRepository booksRepository;
    @Autowired AuthExtractUtil authExtractUtil;

    @Override
    public BooksDto create(BookAddForm form, MultipartFile image) throws Exception {
        Books newBook = BooksMapper.toEntity(form, ImageUtil.ToBase64(image));
        if (newBook == null){
            logger.error("Failed to save book");
            throw new Exception("Failed to save book");
        }
        booksRepository.save(newBook);
        logger.info("Book entry submitted successfully: " + newBook.getId());
        return BooksMapper.toBaseDto(newBook);
    }

    @Override
    public FindResponse<BooksDto> find(BookFindForm params) throws Exception {
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
        Specification<Books> spec = (root, query, cb) -> cb.conjunction();
        if(StringUtils.isNotBlank(params.getKeyword())){
            spec = spec.and((root, query, cb) -> 
                cb.or(
                    cb.like(cb.lower(root.get("title")), "%" + params.getKeyword().toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("author")), "%" + params.getKeyword().toLowerCase() + "%")
                )
            );
        }
        if (params.getCategoyId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("categoryId"), params.getCategoyId()));
        }
        Page<Books> result = booksRepository.findAll(spec, pageable);
        List<BooksDto> records = result.getContent().stream()
            .map(BooksMapper::toBaseDto)
            .toList();
        
        return new FindResponse<BooksDto>()
            .setRecords(records)
            .setTotalPage(result.getTotalPages())
            .setTotalItem((int) result.getTotalElements())
            .setHasNext(result.hasNext())
            .setHasPrev(result.hasPrevious());
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
        return BooksMapper.toDetailDto(book);
    }

    @Override
    public BooksDto update(Integer id, BookEditForm form, MultipartFile image) throws Exception {
        if (id == null){
            logger.error("Id is required");
            throw new Exception("Id is required");
        }
        Books book = booksRepository.findById(id).orElse(null);
        if (book == null){
            logger.error("Book not found: " + id);
            throw new Exception("Book not found");
        }
        Books updatedBooks = BooksMapper.toEntityWithId(id, form, ImageUtil.ToBase64(image));
        if(updatedBooks == null){
            logger.error("Failed to save book");
            throw new Exception("Failed to save book");
        }
        booksRepository.save(updatedBooks);
        logger.info("Book updated successfully: " + id);
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
