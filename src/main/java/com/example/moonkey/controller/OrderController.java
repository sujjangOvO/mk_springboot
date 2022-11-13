package com.example.moonkey.controller;

import com.example.moonkey.domain.Orders;
import com.example.moonkey.dto.OrderDto;
import com.example.moonkey.repository.OrderRepository;
import com.example.moonkey.service.OrderService;


import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/app")
public class OrderController {



}
