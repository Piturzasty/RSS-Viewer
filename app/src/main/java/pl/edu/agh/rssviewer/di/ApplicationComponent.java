package pl.edu.agh.rssviewer.di;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import pl.edu.agh.rssviewer.RssViewerApplication;

@Component(modules = {
        AndroidInjectionModule.class,
        ActivityBuilder.class
})
public interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        ApplicationComponent build();

        @BindsInstance
        Builder application(Application application);
    }

    void inject(RssViewerApplication app);
}