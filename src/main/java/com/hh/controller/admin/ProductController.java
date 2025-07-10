package com.hh.controller.admin;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.BindingResult;
import com.hh.domain.Product;
import com.hh.domain.User;
import com.hh.service.ProductService;
import com.hh.service.UploadService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List   <Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String handleCreateProduct(
        @ModelAttribute("newProduct")@Valid Product product,
        BindingResult newProductBindingResult,
        @RequestParam("hoidanitFile") MultipartFile file
        ) {
        if(newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String image = this.uploadService.handleSaveFile(file, "products");
        product.setImage(image);
        this.productService.createProduct(product);
        return "redirect:/admin/product";
    }

    @RequestMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        // Lấy sản phẩm theo ID và thêm vào model
        Product product = this.productService.getProductById(id).get();
        model.addAttribute("product", product);
        model.addAttribute("productId", id);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/delete/{id}")
        public String getDeleteProduct(Model model ,@PathVariable long id) {
            model.addAttribute("productId", id);
            model.addAttribute("newProduct", new Product());
            return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String handleDeleProduct(Model model,@ModelAttribute("newProduct") Product product) {
        this.productService.deleteProductById(product.getId());
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Optional<Product> productOptional = this.productService.getProductById(id);
        model.addAttribute("newProduct", productOptional.get());
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateProduct(
        @ModelAttribute("newProduct")@Valid Product product,
        BindingResult productBindingResult,
        @RequestParam("hoidanitFile") MultipartFile file
    ) {
        if(productBindingResult.hasErrors()) {
            return "admin/product/update";
        }

        Product crrProduct = this.productService.getProductById(product.getId()).get();
        if(crrProduct != null){
            if(!file.isEmpty()) {
                String image = this.uploadService.handleSaveFile(file, "products");
                product.setImage(image);
            }
            crrProduct.setName(product.getName());
            crrProduct.setPrice(product.getPrice());
            crrProduct.setQuantity(product.getQuantity());
            crrProduct.setDetailDesc(product.getDetailDesc());
            crrProduct.setShortDesc(product.getShortDesc());
            crrProduct.setFactory(product.getFactory());
            crrProduct.setTarget(product.getTarget());
            this.productService.updateProduct(crrProduct);
        }
        return "redirect:/admin/product";
    }

}
