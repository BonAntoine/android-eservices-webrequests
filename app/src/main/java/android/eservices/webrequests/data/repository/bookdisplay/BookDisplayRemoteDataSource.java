package android.eservices.webrequests.data.repository.bookdisplay;

import android.eservices.webrequests.BookApplication;
import android.eservices.webrequests.data.api.BookDisplayService;
import android.eservices.webrequests.data.api.model.Book;
import android.eservices.webrequests.data.api.model.BookSearchResponse;

import io.reactivex.Single;

public class BookDisplayRemoteDataSource {

    public BookDisplayRemoteDataSource(BookDisplayService booksDisplayService) {
        this.booksDisplayService = booksDisplayService;
    }

    // Va taper la webapi, donc bookDisplayService
    BookDisplayService booksDisplayService;

    public Single<BookSearchResponse> getBookSearchResults(String keywords) {
        return booksDisplayService.getBookSearchResult(keywords, BookApplication.API_KEY);
    }

    public Single<Book> getBookDetails(String bookId) {
        return booksDisplayService.getBook(bookId, BookApplication.API_KEY);
    }

}
