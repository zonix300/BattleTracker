# 🛡️ DnD Battle Tracker

A web app that helps Dungeon Masters manage turn order, HP, and combat states in Dungeons & Dragons 5e.

**Tech Stack:**  
Java 17 • Spring Boot • Spring Web • Spring Security • React • TypeScript • REST API • PostgreSQL

---

## 🌐 Live Demo
🔗 [Try it here!](https://dnd-battle-tracker.onrender.com)

---

## ✨ Features
- Create and manage combat entities (players, monsters, NPCs)
- Automatic initiative ordering
- Apply damage, healing, and effects
- Group combatants for area effects
- Authentication with Spring Security
- Persistent data in PostgreSQL
- Responsive UI (React + TypeScript)

---

## 🧱 Architecture
- **Backend:** Spring Boot REST API  
- **Frontend:** React + TypeScript  
- **Database:** PostgreSQL  
- **Security:** Spring Security (JWT)  
- **Hosting:** Render (Backend) + GitHub Pages (Frontend)

---

## 🚀 How to run locally
```bash
# Backend
cd backend
mvn spring-boot:run

# Frontend
cd frontend
npm install
npm start
