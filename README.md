# 📋 ToDo Simple API

Desenvolvi este projeto como parte do meu aprendizado em **Spring Boot**. O curso ministrado por [Lucas Angelo](https://github.com/Lucas-Angelo/todosimple-api) foi uma grande fonte de inspiração e aprendizado. A implementação segue o passo a passo apresentado na [playlist no YouTube](https://www.youtube.com/watch?v=YcO-Q6yozmU&list=PLiXotHlANc8ptwP6wajo73OZo9Nh5i597), que recomendo a todos que desejam se aprofundar no tema.


---

## 🚀 Começando

Siga as instruções abaixo para configurar e executar o projeto na sua máquina local para testes, desenvolvimento e aprendizado.

### 🛠️ Pré-requisitos

Certifique-se de ter instalado as ferramentas abaixo antes de começar:
- **Java**
- **Maven**
- **MySQL**
---

## 📦 Como Rodar o Projeto

### 1️⃣ Clonar o Repositório

Use o comando abaixo para clonar o repositório:

```bash
git clone https://github.com/Athoosz/spring-boot-api.git
```

### 2️⃣ Configurar e Iniciar a API (Backend)
#### 🔧 Passo 2.1: Abrir o Arquivo application.properties
Edite o arquivo de configurações do projeto:
```bash
src\main\resources\application.properties
```

#### 🔧 Passo 2.2: Configurar Credenciais do Banco de Dados
Insira as credenciais conforme sua configuração local do MySQL Server:
```properties
# Configuração do Banco de Dados
spring.datasource.url=jdbc:mysql://localhost:3306/todosimple?createDatabaseIfNotExist=true
spring.datasource.username=root  <-- username do seu sql
spring.datasource.password=admin <-- senha do seu msql
```

#### 🔧 Passo 2.3: Construir e Iniciar a API
```bash
mvn clean install
```
Inicie a aplicação:
```bash
mvn spring-boot:run
```
Ou va até 
```bash
src\main\java\com\athosfs\todosimple\TodosimpleApplication.java 
```
E clique em Run

🌐 Acesse a API em: http://localhost:8080/

### 3️⃣ Rodar o Frontend
#### 🌐 Passo 3.1: Abrir o Arquivo login.html
Para acessar a interface de usuário, abra o arquivo login.html ou cadastro.html diretamente no navegador:

#### 🌐 Acesse o Frontend em: http://127.0.0.1:5500/view/login.html 
---

## 📚 Recursos Adicionais
- Playlist do curso: [YouTube](https://www.youtube.com/watch?v=YcO-Q6yozmU&list=PLiXotHlANc8ptwP6wajo73OZo9Nh5i597)