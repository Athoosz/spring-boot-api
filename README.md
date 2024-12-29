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


## 📂 Estrutura do Projeto

Abaixo estão os principais componentes e funcionalidades organizados para facilitar a navegação no projeto.

#### **Documentação do Código**

##### **Configurações de Segurança**
- `SecurityConfig.java`: Configurações de segurança do Spring Security.
- `WebConfig.java`: Configurações de CORS.

##### **Controladores**
- `TaskController.java`: Gerencia operações relacionadas a tarefas.
- `UserController.java`: Gerencia operações relacionadas a usuários.

##### **Modelos**
- `Task.java`: Representa o modelo de uma tarefa.
- `User.java`: Representa o modelo de um usuário.
- `ProfileEnum.java`: Enumeração para perfis de usuário.
- `TaskProjection.java`: Projeção para tarefas.
- `UserCreateDTO.java`: DTO para criação de usuários.
- `UserUpdateDTO.java`: DTO para atualização de usuários.

##### **Repositórios**
- `TaskRepository.java`: Repositório de tarefas.
- `UserRepository.java`: Repositório de usuários.

##### **Serviços**
- `TaskService.java`: Lida com a lógica de negócios para tarefas.
- `UserService.java`: Lida com a lógica de negócios para usuários.
- `UserDetailsServiceImpl.java`: Implementação do serviço de detalhes do usuário.

##### **Exceções**
- `GlobalExceptionHandler.java`: Gerencia exceções globais.
- `ErrorResponse.java`: Estrutura de resposta para erros.
- `AuthorizationException.java`: Exceção para problemas de autorização.
- `DataBindingViolationException.java`: Exceção para violação de integridade de dados.
- `ObjectNotFoundException.java`: Exceção para objetos não encontrados.

##### **Segurança**
- `JWTAuthenticationFilter.java`: Filtro para autenticação com JWT.
- `JWTAuthorizationFilter.java`: Filtro para autorização com JWT.
- `JWTUtil.java`: Utilitário para manipulação de tokens JWT.
- `UserSpringSecurity.java`: Implementação de `UserDetails` para Spring Security.

##### **Aplicação Principal**
- `TodosimpleApplication.java`: Classe principal da aplicação Spring Boot.

##### **Testes**
- `TodosimpleApplicationTests.java`: Testes de contexto da aplicação.

---

### 🌐 Frontend

#### Arquivos:
- `index.html`: Página principal do frontend.
- `cadastro.html`: Página para cadastro de novos usuários.
- `login.html`: Página de login.
- `style.css`: Estilos CSS utilizados no frontend.
- `script.js`: Script JavaScript para gerenciar tarefas.
- `cadastro.js`: Script JavaScript para o cadastro de usuários.
- `login.js`: Script JavaScript para o login de usuários.

--- 
## 📚 Recursos Adicionais
- Playlist do curso: [YouTube](https://www.youtube.com/watch?v=YcO-Q6yozmU&list=PLiXotHlANc8ptwP6wajo73OZo9Nh5i597)