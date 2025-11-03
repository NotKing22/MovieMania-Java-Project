<h1 align="center">🎬 MovieMania</h1>

<p align="center">
  <em>Um projeto interativo para avaliar, filtrar e explorar filmes!</em><br>
  Desenvolvido com Spring Boot, JavaScript e integração com a API do TMDB.
</p>

---

<h2>📖 Sobre o Projeto</h2>

<p>
  O <strong>MovieMania</strong> é uma aplicação web que permite ao usuário visualizar uma lista de filmes, 
  avaliar cada um com notas de <strong>1 a 5 estrelas</strong> e filtrar os resultados de acordo com suas preferências.
  <br><br>
  O sistema também possui um <strong>painel administrativo</strong> onde é possível gerenciar usuários e visualizar suas avaliações.
</p>

---

<h2>🔑 Configurando a API Key da TMDB</h2>

<p>
  Para que o projeto funcione corretamente, é necessário possuir uma chave de acesso (API Key) da 
  <a href="https://www.themoviedb.org/" target="_blank"><strong>The Movie Database (TMDB)</strong></a>.
  Essa chave permite que a aplicação consuma os dados reais dos filmes.
</p>

---

<h3>📍 Como configurar:</h3>

<ol>
  <li>Acesse o site oficial da TMDB e crie uma conta gratuita.</li>
  <li>Vá até o seu perfil e gere uma <strong>API Key</strong> (seção “API” nas configurações da conta).</li>
  <li>Abra o arquivo <code>MovieService.class</code> no seu projeto.</li>
  <li>Procure a linha que contém a variável responsável pela API Key, semelhante a:
    <pre><code>private static final String API_KEY = "SUA_API_KEY_AQUI";</code></pre>
  </li>
  <li>Substitua <code>"SUA_API_KEY_AQUI"</code> pela sua chave obtida no site da TMDB.</li>
</ol>

<p>
  ⚠️ <strong>Importante:</strong> Nunca publique sua API Key real em repositórios públicos. 
  Caso o projeto vá para o GitHub, utilize variáveis de ambiente ou um arquivo de configuração 
  local (como <code>.env</code>) para manter sua chave segura.
</p>

<p align="center">
  <img src="https://i.imgur.com/jawiSTL.png" width="1000px" alt="Exemplo de inserção da API Key no MovieService.class">
</p>

---

<h2>🌟 Funcionalidades</h2>

<ul>
  <li>⭐ Avaliar filmes com notas de <strong>1 a 5 estrelas</strong>.</li>
  <li>🎭 Filtrar filmes por:
    <ul>
      <li><strong>Nota</strong> — exibe apenas filmes com nota igual ou superior à escolhida.</li>
      <li><strong>Gênero</strong> — exibe filmes correspondentes ao gênero selecionado.</li>
      <li><strong>Década</strong> — filtra filmes lançados dentro do intervalo selecionado.</li>
    </ul>
  </li>
  <li>⚙️ Login de <strong>administrador</strong> para acessar a lista de usuários.</li>
  <li>👥 Visualizar as avaliações feitas por cada usuário individualmente.</li>
  <li>📊 Ordenar avaliações por nota — do <strong>maior para o menor</strong> ou <strong>menor para o maior</strong>.</li>
</ul>

---

<h2>🏠 Página Inicial</h2>

<p>
  Na página inicial são exibidos os filmes mais populares da API do TMDB.
  Cada filme possui seu pôster, título, número de votos e uma nota média ajustada para a escala de 0–5 estrelas.
  <br><br>
  Os filmes podem ser carregados em páginas (“Carregar mais”) e a interface é totalmente dinâmica.
</p>

<p align="center">
  <img src="https://imgur.com/jQdRsXu.png" width="1000px" alt="pagina inicial">
  <br>
  <img src="https://imgur.com/CwsUBBF.png" width="1000px" alt="pagina inicial filtro">
  <br>
  <img src="https://imgur.com/BTXS1sI.png" width="1000px" alt="avaliando filme">
</p>

---

<h2>🎛️ Sistema de Filtros</h2>

<p>
  Os filtros foram implementados de forma dinâmica no frontend, comunicando-se com o backend via API.
  <br><br>
  Quando o usuário aplica um filtro de <strong>nota</strong>, o valor selecionado (de 1 a 5) é convertido internamente
  para a escala original (0–10) utilizada pela API do TMDB, garantindo compatibilidade.
  <br><br>
  Além disso, ao selecionar uma nota (por exemplo, 3 estrelas), o sistema exibe os filmes com nota igual ou superior,
  ordenados de forma crescente:
</p>

<pre>
1.0 ⭐ → 2.0 ⭐ → 3.0 ⭐ → 4.0 ⭐ → 5.0 ⭐
</pre>

<p>
  Essa ordenação é feita de forma eficiente no frontend, utilizando o método <code>Array.sort()</code> do JavaScript
  com uma função de comparação baseada no campo <code>vote_average</code>.
</p>

---

<h3>⚙️ Complexidade dos Filtros</h3>

<table>
  <tr>
    <th>Filtro</th>
    <th>Algoritmo</th>
    <th>Complexidade</th>
    <th>Descrição</th>
  </tr>
  <tr>
    <td><strong>Nota</strong></td>
    <td><code>Array.filter() + Array.sort()</code></td>
    <td>O(n log n)</td>
    <td>Filtra os filmes acima da nota mínima e ordena pelo valor crescente de nota.</td>
  </tr>
  <tr>
    <td><strong>Gênero</strong></td>
    <td><code>Array.filter()</code></td>
    <td>O(n)</td>
    <td>Seleciona apenas os filmes cujo gênero corresponde ao valor selecionado.</td>
  </tr>
  <tr>
    <td><strong>Década</strong></td>
    <td><code>Array.filter()</code></td>
    <td>O(n)</td>
    <td>Filtra filmes com base na data de lançamento dentro do intervalo da década escolhida.</td>
  </tr>
  <tr>
    <td><strong>Ordenação Inicial (sem filtros)</strong></td>
    <td>API TMDB (sem ordenação adicional)</td>
    <td>—</td>
    <td>Os filmes são exibidos na mesma ordem retornada pela API, preservando o ranking original.</td>
  </tr>
</table>

---

<h2>🔐 Painel Administrativo</h2>

<p>
  O administrador pode fazer login e acessar uma página exclusiva com:
</p>

<ul>
  <li>👤 Lista de todos os usuários cadastrados.</li>
  <li>🔍 Botão <strong>“Ver”</strong> para acessar as avaliações do usuário selecionado.</li>
  <li>📈 Filtros e ordenação por nota (crescente ou decrescente).</li>
</ul>

<p align="center">
  <em>🔒 auth: email[admin@email.com], senha[1234]</em><br>
  <img src="https://imgur.com/tenfJ0n.png" width="1000px" alt="pagina administrativo">
  <img src="https://imgur.com/MRx2DH0.png" width="1000px" alt="pagina administrativo">
  <img src="https://imgur.com/T1zY20i.png" width="1000px" alt="pagina administrativo">
  <img src="https://imgur.com/UKIyPak.png" width="1000px" alt="pagina administrativo">
</p>

---

<h2>🧠 Lógica de Ordenação e Exibição</h2>

<p>
  Os filmes são inicialmente exibidos exatamente na ordem em que a API TMDB os retorna,
  priorizando popularidade. Quando o usuário aplica um filtro de nota, a aplicação:
</p>

<ol>
  <li>Filtra os filmes com <code>vote_average >= (nota_selecionada * 2)</code>.</li>
  <li>Ordena os resultados de forma crescente pelo campo <code>vote_average</code>.</li>
  <li>Renderiza os filmes dinamicamente no grid.</li>
</ol>

<p>
  Esse comportamento garante que o filtro de nota não altere o conjunto base dos filmes antes do usuário interagir,
  mantendo a exibição inicial natural e realista.
</p>

---

<h2>🛠️ Tecnologias Utilizadas</h2>

<ul>
  <li><strong>Java</strong> + <strong>Spring Boot</strong> (Backend)</li>
  <li><strong>Thymeleaf</strong> (Template Engine)</li>
  <li><strong>JavaScript</strong> (Frontend interativo)</li>
  <li><strong>HTML5</strong> + <strong>CSS3</strong> (Interface)</li>
  <li><strong>API TMDB</strong> (Dados dos filmes)</li>
</ul>

---

<h2>🚀 Execução</h2>

<ol>
  <li>Clone o repositório: <code>git clone https://github.com/seuusuario/MovieMania.git</code></li>
  <li>Entre na pasta do projeto: <code>cd MovieMania</code></li>
  <li>Inicie o servidor Spring Boot: <code>mvn spring-boot:run</code></li>
  <li>Acesse: <code>http://localhost:8080</code></li>
</ol>

---

<h2 align="center">✨ Desenvolvido com dedicação por Matheus de Jesus ✨</h2>
