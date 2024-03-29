package exercise.article;

import exercise.worker.WorkerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WorkerImplTest {
    private Article article1 = new Article("Title_1", "Content_1", "Author_1", null);
    private Article article2 = new Article("Title_2", "Content_2", "Author_2", LocalDate.of(2022, 1, 1));
    private Article article3 = new Article(null, "Content_3", "Author_3", null);
    private Article article4 = new Article("Title_4", null, "Author_4", null);
    private Article article5 = new Article("Title_5", "Content_5", null, null);
    private List<Article> articles;
    @Mock
    private Library library;
    private WorkerImpl worker;
    @BeforeEach
    public void setUp() {
        library = mock(Library.class);
        worker = new WorkerImpl(library);
        articles = new ArrayList<>();
    }
    @Test
    @DisplayName("Возврат списка названий статей")
    public void shouldReturnGetCatalog() {
        when(library.getAllTitles()).thenReturn(List.of("Title_1", "Title_2", "Title_3"));
        String actual = worker.getCatalog();
        String expected = "Список доступных статей:\n    Title_1\n    Title_2\n    Title_3\n";
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Не добавляются статьи, в которых одно из полей (title/content/author) имеет знаечние null")
    public void shouldNotAddWith_NullTitle_NullContent_NullAuthor() {
        articles.add(article3);
        articles.add(article4);
        articles.add(article5);
        List<Article> prepareArticles = worker.prepareArticles(articles);
        assertEquals(0, prepareArticles.size());
    }
    @Test
    @DisplayName("Корректная установка даты")
    public void isCorrectDate() {
        articles.add(article1);
        articles.add(article2);
        List<Article> actual = worker.prepareArticles(articles);
        List<LocalDate> actualArticlesDates = actual.stream().map(Article::getCreationDate).toList();
        List<LocalDate> expected = Arrays.asList(LocalDate.now(), LocalDate.of(2022, 1, 1));
        assertEquals(expected, actualArticlesDates);
    }
    @Test
    @DisplayName("Проверка вызовов методов store и updateCatalog")
    public void shouldInvokeMethodsStoreAndUpdateCatalog() {
        articles.add(article1);
        worker.addNewArticles(articles);
        verify(library, times(1)).store(anyInt(), anyList());
        verify(library, times(1)).updateCatalog();
    }
    @Test
    @DisplayName("Не добавляются статьи с одинаковым названием")
    public void shouldNotAddDuplicatesTitles() {
        articles.add(article1);
        when(library.getAllTitles()).thenReturn(List.of(article1.getTitle()));
        Article newАrticle = new Article("Title_1", "Content", "Author", null);
        articles.add(newАrticle);
        worker.addNewArticles(articles);
        verify(library, never()).store(anyInt(),anyList());
    }
}