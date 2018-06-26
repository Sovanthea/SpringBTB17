package com.example.demo.services.impl;

import com.example.demo.models.Book;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAll() {
        return this.bookRepository.getAll();
    }

    @Override
    public Book findOne(Integer id) {
        return this.bookRepository.findOne(id);
    }

    @Override
    public boolean update(Book book) {
        return this.bookRepository.update(book);
    }

    @Override
    public boolean delete(Integer id) {
        return this.bookRepository.delete(id);
    }

    @Override
    public boolean create(Book book) {
        return this.bookRepository.create(book);
    }
}
