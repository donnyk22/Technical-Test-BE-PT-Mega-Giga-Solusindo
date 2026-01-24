package com.github.donnyk22.project.services.books;

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

import com.github.donnyk22.project.exceptions.BadRequestException;
import com.github.donnyk22.project.exceptions.ResourceNotFoundException;
import com.github.donnyk22.project.models.dtos.BooksDto;
import com.github.donnyk22.project.models.dtos.FindResponse;
import com.github.donnyk22.project.models.entities.Books;
import com.github.donnyk22.project.models.forms.BookAddForm;
import com.github.donnyk22.project.models.forms.BookEditForm;
import com.github.donnyk22.project.models.forms.BookFindForm;
import com.github.donnyk22.project.models.mappers.BooksMapper;
import com.github.donnyk22.project.repositories.BooksRepository;
import com.github.donnyk22.project.utils.AuthExtractUtil;
import com.github.donnyk22.project.utils.MediaUtil;

import lombok.SneakyThrows;

@Service
@Transactional
public class BooksServiceImpl implements BooksService{

    @Autowired BooksRepository booksRepository;
    @Autowired AuthExtractUtil authExtractUtil;

    @Override
    @SneakyThrows
    public BooksDto create(BookAddForm form, MultipartFile image) {
        Books newBook = BooksMapper.toEntity(form, MediaUtil.ToBase64(image));
        booksRepository.save(newBook);
        return BooksMapper.toBaseDto(newBook);
    }

    @Override
    public FindResponse<BooksDto> find(BookFindForm params) {
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
    public BooksDto findOne(Integer id) {
        if (id == null){
            throw new BadRequestException("Id is required");
        }
        Books book = booksRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
        return BooksMapper.toDetailDto(book);
    }

    @Override
    @SneakyThrows
    public BooksDto update(Integer id, BookEditForm form, MultipartFile image) {
        if (id == null){
            throw new BadRequestException("Id is required");
        }
        booksRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
        Books updatedBooks = BooksMapper.toEntityWithId(id, form, MediaUtil.ToBase64(image));
        booksRepository.save(updatedBooks);
        return BooksMapper.toBaseDto(updatedBooks);
    }

    @Override
    public BooksDto delete(Integer id) {
        if(id == null){
            throw new BadRequestException("Id is required");
        }
        Books book = booksRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
        booksRepository.deleteById(id);
        return BooksMapper.toBaseDto(book);
    }
    
}
