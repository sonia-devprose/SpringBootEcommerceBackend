package com.ecommerce.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
// CHANGED: This mapping now means this controller handles requests to the root
// of the application's context path.
// Since server.servlet.context-path is /api, this controller effectively handles /api
@RequestMapping("/")
public class ApiRootController {

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String getApiRoot() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>E-Commerce API</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 2em; }
                    h1 { color: #2c3e50; }
                    ul { list-style-type: none; padding: 0; }
                    li { margin: 8px 0; }
                    a { color: #3498db; text-decoration: none; }
                    a:hover { text-decoration: underline; }
                    .container { max-width: 800px; margin: 0 auto; }
                    code { background: #f5f5f5; padding: 2px 4px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>E-Commerce API v1.0</h1>
                    <p>Available endpoints:</p>
                    <ul>
                        <li><a href="products"><code>/api/products</code></a> - Product management</li>
                        <li><a href="customers"><code>/api/customers</code></a> - Customer management</li>
                        <li><a href="cart"><code>/api/cart</code></a> - Shopping cart</li>
                        <li><a href="h2-console"><code>/h2-console</code></a> - H2 Database Console (Dev only)</li>
                    </ul>
                    <p>Note: Paths below are relative to the /api context path.</p>
                    <p>Example Usage:</p>
                    <ul>
                        <li><code>GET /products</code> - Get all products</li>
                        <li><code>POST /products</code> - Create a new product</li>
                        <li><code>GET /products/{id}</code> - Get product by ID</li>
                        <li><code>PUT /products/{id}</code> - Update product by ID</li>
                        <li><code>DELETE /products/{id}</code> - Delete product by ID</li>
                    </ul>
                </div>
            </body>
            </html>
            """;
    }
}
