package pl.edu.agh.rssviewer.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import pl.edu.agh.rssviewer.MainActivity;
import pl.edu.agh.rssviewer.ManagementActivity;
import pl.edu.agh.rssviewer.ui.details.FeedDetailsActivity;
import pl.edu.agh.rssviewer.ui.preferences.PreferencesActivity;

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = { DatabaseModule.class })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = { DatabaseModule.class })
    abstract PreferencesActivity bindPreferencesActivity();

    @ContributesAndroidInjector(modules = { DatabaseModule.class })
    abstract FeedDetailsActivity bindFeedDetailsActivity();

    @ContributesAndroidInjector(modules = { DatabaseModule.class })
    abstract ManagementActivity bindManagementActivity();
}