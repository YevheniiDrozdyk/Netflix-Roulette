package com.indev.netflixroulette.database;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.indev.netflixroulette.model.Production;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Controller of database.
 *
 * @author E.Drozdyk
 * @version 1.0 15 Oct 2016
 */
public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
        realm.isAutoRefresh();
    }

    public static RealmController with(Fragment fragment) {
        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {
        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {
        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public void clearAll() {
        realm.beginTransaction();
        realm.delete(Production.class);
        realm.commitTransaction();
    }

    public RealmResults<Production> getProductions() {

        return realm.where(Production.class).findAll();
    }

    public Production getProduction(String id) {
        return realm.where(Production.class).equalTo("id", id).findFirst();
    }

    public boolean hasProductions() {
        return !realm.isEmpty();
    }
}
