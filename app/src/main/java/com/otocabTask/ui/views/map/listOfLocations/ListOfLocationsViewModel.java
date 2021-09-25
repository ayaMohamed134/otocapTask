package com.otocabTask.ui.views.map.listOfLocations;


import com.otocabTask.data.repo.AppRepo;
import com.otocabTask.ui.base.BaseViewModel;

import javax.inject.Inject;


/**
 * Created by Aya Mohamed on 11/17/2019.
 */
public class ListOfLocationsViewModel extends BaseViewModel {

    private final AppRepo appRepo;

    @Inject
    public ListOfLocationsViewModel(AppRepo appRepo) {
        super(appRepo);
        this.appRepo = appRepo;
    }

}
