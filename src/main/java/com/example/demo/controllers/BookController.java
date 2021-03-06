package com.example.demo.controllers;


import com.example.demo.models.Book;
import com.example.demo.services.BookService;
import com.example.demo.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BookController {

    private BookService bookService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping({"/index", "/", "home"})
//    @RequestMapping(value = {"/index", "/", "/home"}, method = RequestMethod.GET)
    public String index(ModelMap model) {

        List<Book> bookList = this.bookService.getAll();

        model.addAttribute("books", bookList);

        return "book/index";
    }


    @GetMapping("view/{id}")
    public String view(@PathVariable("id") Integer id, Model model) {
        System.out.println("ID: " + id);


        Book book = this.bookService.findOne(id);

        model.addAttribute("book", book);
        return "book/view-detail";

    }


    @GetMapping("/update/{book_id}")
    public String showFormUpdate(@PathVariable Integer book_id, ModelMap modelMap) {
        System.out.println("ID to update: " + book_id);

        Book book = this.bookService.findOne(book_id);

        modelMap.addAttribute("isNew", false);
        modelMap.addAttribute("book", book);

        return "book/create-book";
    }


    @PostMapping("update/submit")
    public String updateSubmit(@ModelAttribute Book book, MultipartFile file) {
        System.out.println(book);


        String filename = this.uploadService.singleFileUpload(file, "class/");

        if (!file.isEmpty()) {
            book.setThumbnail("/images-btb/" + filename);
        }

        this.bookService.update(book);

        return "redirect:/index";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {

        System.out.println(id);

        this.bookService.delete(id);

        return "redirect:/index";
    }


    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("isNew", true);
        model.addAttribute("book", new Book());

        return "book/create-book";
    }

    @PostMapping("/create/submit")
    public String create(@Valid Book book, BindingResult bindingResult, MultipartFile file, ModelMap model) {
        String filename = this.uploadService.upload(file, "class/");
        book.setThumbnail("/images-btb/" + filename);
        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew",true);
            return "book/create-book";
        }

        System.out.println(book);
        this.bookService.create(book);
        return "redirect:/index";
    }


    @GetMapping("/test-multi-upload")
    public String showUpload() {
        return "book/upload-file";
    }


    @PostMapping("/test-multi-upload/submit")
    public String testMultipleFileUpload(@RequestParam("file") List<MultipartFile> files) {
        List<String> filenames = this.uploadService.upload(files, "test/");
        return "";
    }


}
