package pl.rstepniewski.purchaselistapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rstepniewski.purchaselistapi.model.category.Category;
import pl.rstepniewski.purchaselistapi.model.category.CategoryRepository;
import pl.rstepniewski.purchaselistapi.auth.RegisterRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class PurchaseListApiController {
    private final CategoryRepository categoryRepository;

    @GetMapping("")
    public ResponseEntity<List<Category>> register(
            @RequestBody RegisterRequest request ){
        List<Category> all = categoryRepository.findAll();
        return ResponseEntity.ok( all );
    }

}
