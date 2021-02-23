package android.eservices.webrequests.data.repository.bookdisplay;

import android.eservices.webrequests.data.api.model.Book;
import android.eservices.webrequests.data.api.model.BookSearchResponse;
import android.eservices.webrequests.data.entity.BookEntity;
import android.eservices.webrequests.data.repository.bookdisplay.local.BookDisplayLocalDataSource;
import android.eservices.webrequests.data.repository.bookdisplay.mapper.BookToBookEntityMapper;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class BookDisplayDataRepository implements BookDisplayRepository {


    private BookDisplayLocalDataSource bookDisplayLocalDataSource;
    private BookDisplayRemoteDataSource bookDisplayRemoteDataSource;
    private BookToBookEntityMapper bookToBookEntityMapper;

    public BookDisplayDataRepository(BookDisplayRemoteDataSource bookDisplayRemoteDataSource, BookToBookEntityMapper bookToBookEntityMapper, BookDisplayLocalDataSource bookDisplayLocalDataSource) {
        this.bookDisplayLocalDataSource = bookDisplayLocalDataSource;
        this.bookDisplayRemoteDataSource = bookDisplayRemoteDataSource;
        this.bookToBookEntityMapper = bookToBookEntityMapper;
    }

    public Single<BookSearchResponse> getBookSearchResponse(String keywords) {
        // rerieve book from remote
        // return bookDisplayRemoteDataSource.getBookSearchResults(keywords);

        return bookDisplayRemoteDataSource.getBookSearchResults(keywords)
                .zipWith(bookDisplayLocalDataSource.getFavoriteIdList(), new BiFunction<BookSearchResponse, List<String>, BookSearchResponse>() {
                    @Override
                    public BookSearchResponse apply(BookSearchResponse bookSearchResponse, List<String> idList) throws Exception {
                        for (Book book : bookSearchResponse.getBookList()) {
                            if (idList.contains(book.getId())) {
                                book.setFavorite();
                            }
                        }
                        return bookSearchResponse;
                    }
                });
    }




    public Flowable<List<BookEntity>> getFavoriteBooks() {
        return bookDisplayLocalDataSource.loadFavorites();
    }


    public Completable addBookToFavorites(String bookId) {
        return bookDisplayRemoteDataSource.getBookDetails(bookId)
                .map(new Function<Book, BookEntity>() {
                    @Override
                    public BookEntity apply(Book book) throws Exception {
                        return bookToBookEntityMapper.map(book);
                    }
                })
                .flatMapCompletable(new Function<BookEntity, CompletableSource>() {
                    @Override
                    public CompletableSource apply(BookEntity bookEntity) throws Exception {
                        return bookDisplayLocalDataSource.addBookToFavorites(bookEntity);
                    }
                });
    }


    public Completable removeBookFromFavorites(String bookId) {
        return bookDisplayLocalDataSource.deleteBookFromFavorites(bookId);
    }
}
