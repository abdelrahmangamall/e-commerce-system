# E-Commerce System

A simple Java e-commerce system with product management, shopping cart, and checkout functionality.

## Features

- Product management (expiring/non-expiring, shippable/non-shippable)
- Shopping cart with quantity validation
- Checkout process with:
  - Shipping calculation (minimum 30 fee)
  - Payment validation
  - Inventory updates
- Detailed output including:
  - Shipment notice (for shippable items)
  - Checkout receipt

## How to Run

1. Clone the repository
2. Compile the Java files:
   ```bash
   javac com/ecommerce/*.java com/ecommerce/model/*.java com/ecommerce/service/*.java
