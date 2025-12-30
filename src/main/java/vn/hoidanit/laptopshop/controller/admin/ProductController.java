package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProductController {
    private final UploadService uploadService;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public ProductController(UploadService uploadService, ProductRepository productRepository,
            ProductService productService) {
        this.uploadService = uploadService;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        List<Product> products = productService.fetchProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getProductCreationPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProductPage(
            @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        if (newProductBindingResult.hasErrors()) {
            List<FieldError> errors = newProductBindingResult.getFieldErrors();
            for (FieldError error : errors) {
                System.out.println(error.getField() + " - " + error.getDefaultMessage());
            }
            return "admin/product/create";
        }

        String images = uploadService.uploadFile(file, "product");
        product.setImage(images);
        productService.createProduct(product);
        return "redirect:/admin/product";

    }

    @GetMapping("admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product product = productService.getProduct(id).get();
        model.addAttribute("id", id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("admin/product/update/{id}")
    public String productUpdatingPage(Model model, @PathVariable long id) {
        Optional<Product> productOptional = productService.getProduct(id);
        if (productOptional.isPresent()) {
            model.addAttribute("newProduct", productOptional.get()); // Lấy Product ra khỏi Optional
        } else {
            return "redirect:/admin/product"; // Hoặc xử lý lỗi tùy ý
        }
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(Model model,
            @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }
        // 2. Lấy Optional từ Service
        Optional<Product> productOptional = productService.getProduct(product.getId());

        if (productOptional.isPresent()) {
            Product currentProduct = productOptional.get();

            if (!file.isEmpty()) {
                String fileName = uploadService.uploadFile(file, "product");
                currentProduct.setImage(fileName);
            }
            currentProduct.setDetailDesc(product.getDetailDesc());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setShortDesc(product.getShortDesc());
            currentProduct.setSold(product.getSold());
            currentProduct.setTarget(product.getTarget());

            productRepository.save(currentProduct);

        }
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @GetMapping("admin/product/delete/delete/{id}")
    public String DeleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/product";
    }

}
