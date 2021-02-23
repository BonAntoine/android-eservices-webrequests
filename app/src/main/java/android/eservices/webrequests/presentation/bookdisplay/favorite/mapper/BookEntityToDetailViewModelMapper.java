package android.eservices.webrequests.presentation.bookdisplay.favorite.mapper;


import android.eservices.webrequests.data.entity.BookEntity;
import android.eservices.webrequests.presentation.bookdisplay.favorite.adapter.BookDetailViewItem;
import android.text.Html;

import java.util.ArrayList;
import java.util.List;

public class BookEntityToDetailViewModelMapper {

    //TODO

    private BookDetailViewItem map(BookEntity bookEntity) {
        BookDetailViewItem bookItemViewModel = new BookDetailViewItem();
        bookItemViewModel.setBookTitle(bookEntity.getTitle());
        bookItemViewModel.setBookId(bookEntity.getId());
        bookItemViewModel.setIconUrl(bookEntity.getThumbUrl());
        if (bookEntity.getDescription() != null) {
            bookItemViewModel.setBookDescription(Html.fromHtml(bookEntity.getDescription()).toString());
        }
        bookItemViewModel.setBookAuthors(bookEntity.getAuthors());

        bookItemViewModel.setBookPublishedDate("Published in " + bookEntity.getPublishedDate());

        String language = "This book is in " + languageMapper(bookEntity.getLanguage());
        bookItemViewModel.setBookLanguage(language);
        return bookItemViewModel;
    }

    public List<BookDetailViewItem> map(List<BookEntity> bookList) {
        List<BookDetailViewItem> bookItemViewModelList = new ArrayList<>();
        for (BookEntity book : bookList) {
            bookItemViewModelList.add(map(book));
        }
        return bookItemViewModelList;
    }

    private String languageMapper(String input) {
        switch (input) {
            case "en":
                return "English";
            case "fr":
                return "French";
            default:
                return "Unknown (" + input + ")";
        }
    }
}
