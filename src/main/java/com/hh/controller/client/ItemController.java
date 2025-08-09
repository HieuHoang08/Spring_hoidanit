package com.hh.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.hh.domain.Cart;
import com.hh.domain.CartDetail;
import com.hh.domain.Product;
import com.hh.domain.User;
import com.hh.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {

    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getProductDetailPage(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id).get();
        model.addAttribute("product", product);
        model.addAttribute("productId", id);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        long productId = id;
        String email = (String) session.getAttribute("email");

        this.productService.handleAddProductToCart(email, productId, session, 1);

        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCardPage(Model model, HttpServletRequest request) {
        User crUser = new User();
        HttpSession session = request.getSession(false);
        long userId = (Long) session.getAttribute("userId");
        crUser.setId(userId);
        Cart cart = this.productService.fetchByUser(crUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0.0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long cartDetailId = id;
        this.productService.handleDeleteCartDetail(cartDetailId, session);

        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User crUser = new User();
        HttpSession session = request.getSession(false);
        long userId = (Long) session.getAttribute("userId");
        crUser.setId(userId);
        Cart cart = this.productService.fetchByUser(crUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0.0;
        for (CartDetail cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckoutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = (cart == null || cart.getCartDetails() == null)
                ? new ArrayList<>()
                : cart.getCartDetails();

        this.productService.handleUpdateCartBeforCheckout(cartDetails);

        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("userId");
        currentUser.setId(id);
        

        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThankYouPage(Model model, HttpServletRequest request){
        return "client/cart/thanks";
    }
}
