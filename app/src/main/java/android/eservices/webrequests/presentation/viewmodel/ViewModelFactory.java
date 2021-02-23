package android.eservices.webrequests.presentation.viewmodel;


import android.eservices.webrequests.data.repository.bookdisplay.BookDisplayDataRepository;
import android.eservices.webrequests.presentation.bookdisplay.search.mapper.BookToViewModelMapper;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
// on init le repo qu'on va utiliser pour créer les view model
// Android crée pour nous les vieux model
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final BookDisplayDataRepository bookDisplayRepository;

    public ViewModelFactory(BookDisplayDataRepository bookDisplayRepository) {
        this.bookDisplayRepository = bookDisplayRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BookSearchViewModel.class)) {
            return (T) new BookSearchViewModel(bookDisplayRepository);
        }

        if (modelClass.isAssignableFrom(BookFavoriteViewModel.class)) {
            return (T) new BookFavoriteViewModel(bookDisplayRepository);
        }

        //Handle favorite view model case
        throw new IllegalArgumentException("Unknown ViewModel class");

    }
}