package com.mb.spring.models;

import com.mb.spring.annotation.Isbn;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Book implements Serializable {

    @NotNull
    @Isbn
    private String isbn;

    @Size(min = 1, max = 1024)
    private String title;

    @NotNull
    private User author;
}
