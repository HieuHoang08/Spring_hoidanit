package com.hh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hh.domain.Cart;
import com.hh.domain.CartDetail;
import com.hh.domain.Order;
import com.hh.domain.OrderDetail;
import com.hh.domain.Product;
import com.hh.domain.User;
import com.hh.repository.CartDetailRepository;
import com.hh.repository.CartRepository;
import com.hh.repository.OrderDetailRepository;
import com.hh.repository.OrderRepository;
import com.hh.repository.ProductRepository;
import com.hh.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService, UserRepository userRepository,
            OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;

    }

    public Product handleSaveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return this.productRepository.findById(id);
    }

    public Product updateProduct(Product product) {
        return this.productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession sesison, long quantity) {
        // Tìm user theo email
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // Tìm cart theo user
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                // Nếu chưa có cart thì tạo mới
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);

                cart = this.cartRepository.save(newCart);

            }

            // Tìm sản phẩm theo ID
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product rProduct = productOptional.get();

                // 🔧 Sửa chỗ này: Dùng Cart và Product object
                CartDetail oldDetail = cartDetailRepository.findByCartAndProduct(cart, rProduct);

                if (oldDetail == null) {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(rProduct);
                    cartDetail.setPrice(rProduct.getPrice());
                    cartDetail.setQuantity((int) quantity);
                    this.cartDetailRepository.save(cartDetail);

                    // Cập nhật tổng số mặt hàng trong giỏ
                    int sum = cart.getSum() + 1;
                    cart.setSum(sum);
                    this.cartRepository.save(cart);
                    sesison.setAttribute("sum", sum);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + (int) quantity);
                    this.cartDetailRepository.save(oldDetail);
                }
            }
        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleDeleteCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetaiOptional = this.cartDetailRepository.findById(cartDetailId);
        if (cartDetaiOptional.isPresent()) {
            CartDetail cartDetail = cartDetaiOptional.get();
            Cart cart = cartDetail.getCart();

            this.cartDetailRepository.deleteById(cartDetailId);
            // Cập nhật tổng số mặt hàng trong giỏ
            if (cart.getSum() > 1) {
                int sum = cart.getSum() - 1;
                cart.setSum(sum);
                session.setAttribute("sum", sum);
                this.cartRepository.save(cart);
            } else {
                this.cartRepository.deleteById(cart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleUpdateCartBeforCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> optionalCartDetail = this.cartDetailRepository.findById(cartDetail.getId());
            if (optionalCartDetail.isPresent()) {
                CartDetail existingCartDetail = optionalCartDetail.get();
                existingCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(existingCartDetail);
            }
        }
    }

    @Transactional
    public void handlePlaceOrder(
            User user, HttpSession session,
            String receiverName, String receiverAddress, String receiverPhone) {

        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();

            if (cartDetails != null) {
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += cd.getPrice();
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);

                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());

                    this.orderDetailRepository.save(orderDetail);
                }

                for (CartDetail cd : cartDetails) {
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                cartRepository.deleteCartDetailsByCartId(cart.getId());

                session.setAttribute("sum", 0);
            }
        }

    }

    
}
