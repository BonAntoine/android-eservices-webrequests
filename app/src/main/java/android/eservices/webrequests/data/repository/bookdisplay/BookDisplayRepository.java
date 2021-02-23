package android.eservices.webrequests.data.repository.bookdisplay;

import android.eservices.webrequests.data.api.model.BookSearchResponse;
import android.eservices.webrequests.data.entity.BookEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface BookDisplayRepository {
    public Single<BookSearchResponse> getBookSearchResponse(String keywords);
    public Flowable<List<BookEntity>> getFavoriteBooks();
    public Completable addBookToFavorites(String bookId);
    public Completable removeBookFromFavorites(String bookId);
}