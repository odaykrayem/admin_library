package com.digitalminds.admindataupload.Model;


public class Book {

    //each attribute starts with the word "book" will be taken ... others for refactor
    String bookId;
    String bookTitle;
    String bookAuthorName;
    String bookImagePath;
    String bookPdfUrl;
    String bookCategoryNameEN;
    String BookCategoryNameAR;
    String bookCategoryNameKU;
    String bookRating;
    String bookDescriptionEN;
    String bookDescriptionAR;
    String bookDescriptionKU;
    String liked;
    String bookShares;
    String bookReaders;
    String bookDownloads;

    String bookPagesNumber;
    String bookLanguage;

    public Book() {
    }

    public Book(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Book(String bookId,
                String bookTitle,
                String bookAuthorName,
                String bookImagePath,
                String bookPdfUrl,
                String bookCategoryNameEN,
                String bookCategoryNameAR,
                String bookCategoryNameKU,
                String bookRating,
                String bookDescriptionEN,
                String bookDescriptionAR,
                String bookDescriptionKU,
                String liked,
                String bookShares,
                String bookReaders,
                String bookDownloads,
                String bookPagesNumber,
                String bookLanguage) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthorName = bookAuthorName;
        this.bookImagePath = bookImagePath;
        this.bookPdfUrl = bookPdfUrl;
        this.bookCategoryNameEN = bookCategoryNameEN;
        this.BookCategoryNameAR = bookCategoryNameAR;
        this.bookCategoryNameKU = bookCategoryNameKU;
        this.bookRating = bookRating;
        this.bookDescriptionEN = bookDescriptionEN;
        this.bookDescriptionAR = bookDescriptionAR;
        this.bookDescriptionKU = bookDescriptionKU;
        this.liked = liked;
        this.bookShares = bookShares;
        this.bookReaders = bookReaders;
        this.bookDownloads = bookDownloads;
        this.bookPagesNumber = bookPagesNumber;
        this.bookLanguage = bookLanguage;
    }

    public String getBookLanguage() {
        return bookLanguage;
    }

    public void setBookLanguage(String bookLanguage) {
        this.bookLanguage = bookLanguage;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthorName() {
        return bookAuthorName;
    }

    public void setBookAuthorName(String bookAuthorName) {
        this.bookAuthorName = bookAuthorName;
    }

    public String getBookImagePath() {
        return bookImagePath;
    }

    public void setBookImagePath(String bookImagePath) {
        this.bookImagePath = bookImagePath;
    }

    public String getBookPdfUrl() {
        return bookPdfUrl;
    }

    public void setBookPdfUrl(String bookPdfUrl) {
        this.bookPdfUrl = bookPdfUrl;
    }

    public String getBookCategoryNameEN() {
        return bookCategoryNameEN;
    }

    public void setBookCategoryNameEN(String bookCategoryNameEN) {
        this.bookCategoryNameEN = bookCategoryNameEN;
    }

    public String getBookCategoryNameAR() {
        return BookCategoryNameAR;
    }

    public void setBookCategoryNameAR(String bookCategoryNameAR) {
        BookCategoryNameAR = bookCategoryNameAR;
    }

    public String getBookCategoryNameKU() {
        return bookCategoryNameKU;
    }

    public void setBookCategoryNameKU(String bookCategoryNameKU) {
        this.bookCategoryNameKU = bookCategoryNameKU;
    }



    public String getBookRating() {
        return bookRating;
    }

    public void setBookRating(String bookRating) {
        this.bookRating = bookRating;
    }

    public String getBookDescriptionEN() {
        return bookDescriptionEN;
    }

    public void setBookDescriptionEN(String bookDescriptionEN) {
        this.bookDescriptionEN = bookDescriptionEN;
    }

    public String getBookDescriptionAR() {
        return bookDescriptionAR;
    }

    public void setBookDescriptionAR(String bookDescriptionAR) {
        this.bookDescriptionAR = bookDescriptionAR;
    }

    public String getBookDescriptionKU() {
        return bookDescriptionKU;
    }

    public void setBookDescriptionKU(String bookDescriptionKU) {
        this.bookDescriptionKU = bookDescriptionKU;
    }

    public String getLiked() {
        return liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getBookShares() {
        return bookShares;
    }

    public void setBookShares(String bookShares) {
        this.bookShares = bookShares;
    }

    public String getBookReaders() {
        return bookReaders;
    }

    public void setBookReaders(String bookReaders) {
        this.bookReaders = bookReaders;
    }

    public String getBookDownloads() {
        return bookDownloads;
    }

    public void setBookDownloads(String bookDownloads) {
        this.bookDownloads = bookDownloads;
    }

    public String getBookPagesNumber() {
        return bookPagesNumber;
    }

    public void setBookPagesNumber(String bookPagesNumber) {
        this.bookPagesNumber = bookPagesNumber;
    }

//    //to return description in the right lang
//    public String getDescription(String lang) {
//        //for testing
//        String name = " ";
//        switch (lang){
//            case Const.CATEGORIES_CHILD_EN:
//                name = bookDescriptionEN;
//                break;
//            case Const.CATEGORIES_CHILD_AR:
//                name = bookDescriptionAR;
//                break;
//            case Const.CATEGORIES_CHILD_KURD:
//                name = bookDescriptionKURD;
//                break;
//        }
//        return name;
//    }

}
