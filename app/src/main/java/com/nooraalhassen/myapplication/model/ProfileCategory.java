package com.nooraalhassen.myapplication.model;

/**
 * Created by nooraalhassen on 5/10/16.
 */
public class ProfileCategory {

    long id, profile_id, catId;
    boolean showLanding;

    public ProfileCategory(long id, long profile_id, long catId, boolean showLanding) {
        this.id = id;
        this.profile_id = profile_id;
        this.catId = catId;
        this.showLanding = showLanding;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(long profile_id) {
        this.profile_id = profile_id;
    }

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    public boolean isShowLanding() {
        return showLanding;
    }

    public void setShowLanding(boolean showLanding) {
        this.showLanding = showLanding;
    }
}
