package com.marketplace.tpo.demo.controllers.categories;

import com.marketplace.tpo.demo.entity.Product;
import com.marketplace.tpo.demo.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:5174")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 📦 Listar productos
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 🔍 Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ➕ Crear producto (con o sin URL de imagen)
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // 🖼️ Subir imagen (guarda la URL en el producto)
    @PostMapping("/{id}/image")
    public ResponseEntity<Product> uploadImage(
            @PathVariable Long id,
            @RequestParam("imageUrl") String imageUrl
    ) {
        return productRepository.findById(id).map(product -> {
            product.setImageUrl(imageUrl);
            productRepository.save(product);
            return ResponseEntity.ok(product);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 🗑️ Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) return ResponseEntity.notFound().build();
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    // ✏️ Modificar producto existente
@PutMapping("/{id}")
public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
    return productRepository.findById(id)
            .map(product -> {
                // Actualizamos los campos que se reciban en el body
                if (updatedProduct.getName() != null)
                    product.setName(updatedProduct.getName());
                if (updatedProduct.getDescription() != null)
                    product.setDescription(updatedProduct.getDescription());
                if (updatedProduct.getPrice() != null)
                    product.setPrice(updatedProduct.getPrice());
                if (updatedProduct.getStock() != null)
                    product.setStock(updatedProduct.getStock());
                if (updatedProduct.getImageUrl() != null)
                    product.setImageUrl(updatedProduct.getImageUrl());
                if (updatedProduct.getCategory() != null)
                    product.setCategory(updatedProduct.getCategory());

                // Guardamos y devolvemos el producto actualizado
                Product saved = productRepository.save(product);
                return ResponseEntity.ok(saved);
            })
            .orElse(ResponseEntity.notFound().build());
}

}

