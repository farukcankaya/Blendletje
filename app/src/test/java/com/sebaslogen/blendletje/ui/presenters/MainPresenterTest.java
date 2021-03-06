package com.sebaslogen.blendletje.ui.presenters;

import com.sebaslogen.blendletje.data.database.DatabaseManager;
import com.sebaslogen.blendletje.data.remote.ArticlesServer;
import com.sebaslogen.blendletje.domain.commands.RequestArticlesCommand;
import com.sebaslogen.blendletje.domain.model.ListItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.anyInt;
import static utils.TestUtils.prepareAndStartServerToReturnJsonFromFile;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class MainPresenterTest {

    private MockWebServer mServer;
    private MainContract.ViewActions mViewActions;

    @Before
    public void setUp() throws Exception {
        mViewActions = mock(MainContract.ViewActions.class);
        mServer = new MockWebServer(); // Create a MockWebServer. These are lean enough to create an instance for every unit test
    }

    @After
    public void tearDown() throws IOException {
        mServer.shutdown(); // Shut down the mServer. Instances cannot be reused.
    }

    @Test
    public void onCreation_ListOfArticlesIsLoaded() throws IOException {
        // Given there is a presenter
        final MainPresenter presenter = spy(new MainPresenter(mViewActions, Schedulers.immediate(),
                Schedulers.immediate(), getMockedRequestCommandBuilder()));

        // When the view is attached
        presenter.attachView();

        // Then
        verify(mViewActions).displayPopularArticlesList(anyListOf(ListItem.class));
    }

    private RequestArticlesCommand.RequestArticlesCommandBuilder getMockedRequestCommandBuilder()
            throws IOException {
        final HttpUrl baseUrl = prepareAndStartServerToReturnJsonFromFile(mServer,
                "popular(ws.blendle.com_items_popular).json");
        final DatabaseManager databaseManager = mock(DatabaseManager.class);
        doReturn(Observable.empty()).when(databaseManager).requestPopularArticles(anyInt(), anyInt());

        final ArticlesServer articlesServer = new ArticlesServer(baseUrl, RxJavaCallAdapterFactory.
                createWithScheduler(Schedulers.immediate()));
        return new RequestArticlesCommand
                .RequestArticlesCommandBuilder(articlesServer, databaseManager);
    }

    // TODO: Add negative test cases and add hermetic unit test cases mocking layers below
}