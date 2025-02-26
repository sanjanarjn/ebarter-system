# 📚 eBarter - Book Exchange Platform (Backend)

A backend service for a book exchange platform where users can list, exchange, and borrow books while earning reward points.

## 🚀 Features

### 📌 Core Features
- **User Registration & Verification**: Users must be verified to participate in exchanges.
- **Browse Books**: Guests can view available books.
- **List Books**: Verified users can list books for exchange.
- **Book Exchange**: Users can exchange books and earn **1 reward point**.
- **Book Borrowing**: If a mutual exchange is unavailable, users can borrow a book for **1 reward point** (credited to the owner).

### 🌟 Bonus Features
- **User Reviews & Ratings**: Users can rate and review each other.
- **Book Reviews & Ratings**: Books can be reviewed and rated.
- **Follow Users**: Get notified when a followed user lists a new book.

---

## 🛠️ Tech Stack
- **Spring Boot** - Backend framework
- **Spring Security + JWT** - Authentication & Authorization
- **Spring Data JPA** - ORM for database interaction
- **PostgreSQL** - Relational database
- **Lombok** - Reduces boilerplate code

---

## 🎨 Database Schema

### 🔹 Tables

- `user` - Stores user information.
- `verification_otp` - Manages OTP-based user verification.
- `item_category` - Defines book categories.
- `item` - Stores book details.
- `exchange` - Tracks exchange transactions between users.
- `exchange_transaction` - Manages book borrowing/exchange transactions.
- `user_profile` - Stores user points and rating.
- `user_rating` - Stores user ratings given by others.
- `item_rating` - Stores book ratings given by users.
- `follow_detail` - Tracks user follows.

### 🔹 Key Relationships
- A user can own multiple books (`item` table).
- Exchanges involve two users (`exchange` table).
- Books can be rated by users (`item_rating` table).
- Users can follow others (`follow_detail` table).

---

## 📥 Installation & Setup

### 📌 Prerequisites
- Java 17+
- PostgreSQL
