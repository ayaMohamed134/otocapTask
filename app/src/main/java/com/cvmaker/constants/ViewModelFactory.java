package com.cvmaker.constants;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;


/**
 * created by Aya mohamed on 8/9/2018.
 * <p>
 * A provider factory that persists ViewModels {@link ViewModel}.
 * Used if the view model has a parameterized constructor.
 * // update use NewInstanceFactory instead of Factory
 */
@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModels;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModels) {
        this.viewModels = viewModels;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        Provider<? extends ViewModel> creator = viewModels.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : viewModels.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }
        try {
            return (T) creator.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}