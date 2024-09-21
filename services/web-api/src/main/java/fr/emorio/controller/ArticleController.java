package fr.emorio.controller;

import fr.emorio.model.Article;
import fr.emorio.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    
    private final ArticleService articleService;

    @GetMapping("/search")
    public ResponseEntity<Page<Article>> searchArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "publicationDate,desc") String[] sort,
            @RequestParam String query) {
        
        if (query.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // Create a PageRequest object
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc(sort[0])));

        // Fetch paginated results
        Page<Article> articles = articleService.searchArticles(pageRequest, query);

        return ResponseEntity.ok(articles);
    }
    
}
