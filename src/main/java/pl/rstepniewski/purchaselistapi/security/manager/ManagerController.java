package pl.rstepniewski.purchaselistapi.security.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rstepniewski.purchaselistapi.model.category.Category;
import pl.rstepniewski.purchaselistapi.model.category.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
public class ManagerController {

    private final CategoryRepository categoryRepository;

    @GetMapping("/users")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<List<Category>> postCategory() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PutMapping("/users")
    public ResponseEntity<List<Category>> updateCategory() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @DeleteMapping("/users")
    public ResponseEntity<List<Category>> deleteAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }
}
