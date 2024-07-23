# BidzBuddy Backend

This repository contains the backend implementation of the Online Auctioning System called BidzBuddy, which is designed to provide a secure and user-friendly auction experience. The backend is built using Spring Boot and provides APIs for user authentication, role management, product listing and approval, bidding, notifications, and more.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Technologies Used](#technologies-used)

## Frontend Repository
The frontend repository for bidzbuddy can be found [bidzbuddy frontend](https://github.com/Osigelialex/bidzbuddy-frontend)

## Features

- **User Authentication:** Secure login and registration with email verification.
- **Role Management:** Differentiation between Sellers, Buyers, and Admins for personalized experiences.
- **Product Listing and Approval:** Admin-approved product listings with filtering options for search.
- **Bidding:** Timed bidding contests where the highest bidder wins.
- **Notifications:** Email and in-app notifications for actions like outbidding, product approval, and winning bids.
- **Admin Dashboard:** Comprehensive tools for monitoring and managing the platform.
- **Payment Integration:** Paystack for handling secure transactions.

## Installation

### Prerequisites

- Java 21 or higher
- Maven
- PostgreSQL

### Steps

1. **Clone the Repository**

   ```bash
   git clone https://github.com/Osigelialex/bidzbuddy-backend.git
   cd bidzbuddy-backend

2. **Set up the Environment Variables**
   ```
    spring.application.name=bidzbuddy
    spring.datasource.url=${DB_URL}
    spring.datasource.username=${DB_USERNAME}
    spring.datasource.password=${DB_PSW}
    spring.jpa.properties.hibernate.dialect=${DB_DIALECT}
    spring.jpa.hibernate.ddl-auto=update
    
    jwt.secret=${JWT_SECRET}
    
    cloudinary.cloud_name=${CLOUD_NAME}
    cloudinary.api_key=${API_KEY}
    cloudinary.api_secret=${API_SECRET}
    
    spring.mail.host=${MAIL_HOST}
    spring.mail.port=${MAIL_PORT}
    spring.mail.username=${MAIL_USER}
    spring.mail.password=${MAIL_PASS}
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    
    paystack.secret=${PAYSTACK_SECRET}
    
    spring.session.store-type=jdbc
    spring.session.jdbc.initialize-schema=always
   ```

## Technologies Used

- Spring Boot: Backend framework for Java.
- Spring Security: For authentication and authorization.
- PostgreSQL: Relational database management system.
- Paystack API: Payment processing.
- JavaMailSender: For sending email notifications.
