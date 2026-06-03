# ArtGallery - Sistema de Exibição de Obras

![Java](https://img.shields.io/badge/Java-17%2B-blue?style=for-the-badge&logo=java)
![Status](https://img.shields.io/badge/status-complete-green?style=for-the-badge)
![License](https://img.shields.io/badge/license-academic-lightgrey?style=for-the-badge)
![UI](https://img.shields.io/badge/UI-Swing-orange?style=for-the-badge)

Sistema em Java para gerenciamento de um acervo de arte digital com suporte a cadastro, avaliação, atualização e persistência em CSV.

---

## Funcionalidades

- 🖼️ Cadastro de obras digitais
- 📋 Listagem de obras ativas
- 🔎 Busca por título e autor
- ⭐ Sistema de avaliações (0–10)
- 🏆 Top obras (ranking)
- ✏️ Atualização de obras
- ❌ Desativação de obras
- 🗑️ Remoção de obras
- 💾 Persistência em CSV
- 🖥️ Interface Swing

---

## 🧱 Arquitetura

Entities → Domain models  
Repository → Memory storage  
ArtGallery → Business logic  
GUI → Swing interface  
Persistencia → CSV handling  
Exception → Custom exceptions  

---

## 💾 Data

dados/
 ├── obras.csv
 ├── avaliacoes.csv
 └── exposicoes.csv

---

## ⚙️ Run

javac src/ArtGallery/br/ufc/dc/TF_ArtGallery/**/*.java

java src.ArtGallery.br.ufc.dc.TF_ArtGallery.GUI.Main_GUI

---

## 👨‍💻 Autor

Gustavo C. Martins (555217)
