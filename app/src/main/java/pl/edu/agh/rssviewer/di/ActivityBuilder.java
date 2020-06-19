package pl.edu.agh.rssviewer.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import pl.edu.agh.rssviewer.MainActivity;
import pl.edu.agh.rssviewer.ui.main.HomeViewModel;
import pl.edu.agh.rssviewer.ui.preferences.PreferencesActivity;

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = { DatabaseModule.class })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = { DatabaseModule.class })
    abstract PreferencesActivity bindPrefe();

    @ContributesAndroidInjector(modules = { DatabaseModule.class })
    abstract HomeViewModel bindHomeViewModel();
}