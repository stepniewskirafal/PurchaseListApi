package pl.rstepniewski.purchaselistapi.security.user;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rstepniewski.purchaselistapi.model.category.Category;
import pl.rstepniewski.purchaselistapi.model.category.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
@Tag(name = "Management Control Panel")
public class ManagerController {

    private final CategoryRepository categoryRepository;

    @Operation(
            description = "Get a list of all categories.",
            summary = "List categories.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorised / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PostMapping("/category")
    public ResponseEntity<List<Category>> postCategory() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @PutMapping("/category")
    public ResponseEntity<List<Category>> updateCategory() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @DeleteMapping("/category")
    @Hidden
    public ResponseEntity<List<Category>> deleteAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }
}