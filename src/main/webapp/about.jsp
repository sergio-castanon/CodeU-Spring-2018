<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>

<%@ page import="codeu.helper.AdminHelper"%>

<%
String user = (String) request.getSession().getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Git Rekt's Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">Git Rekt's Chat App</a>
    <a href="/conversations">Conversations</a>

    <% if(user != null){ %>
      <a>Hello <%= user %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>

    <% if (AdminHelper.isAdmin(user)) { %>
      <a href="/admin">Admin</a>
    <% } %>

    <% if (user != null) { %>
      <a href="/logout?post_logout_redirect=/about.jsp">Logout</a>
    <% } %>
  </nav>

  <div id="container">
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

      <h1>About the Code U Chat App </h1>
      <p>
        This is an example chat application designed to be a starting point
        for your CodeU project team work. Here's some stuff to think about:
        This chat application is a project under development by Team 1. Here
        is some background on the developers and the site:
      </p>

      <ul>
        <li><strong>Cynthia Serrano Najera:</strong> Is a student at Wellesley
            College and studies Computer Science and Latinx Studies. She has an
            interest in photography and comic books.</li>

        <li><strong>Justin Mah:</strong> Is a student at the University of
            Alberta and studies Computer Science. He also has an avid interest
            in anime and studying Japanese.</li>

        <li><strong>Sergio Castanon:</strong> Is a student at the Univeristy of
            Utah. He is interested in cars, especially Japanese cars.</li>
      </ul>

      <p>
        Look out for new information as we make changes this summer!
      </p>
    </div>
  </div>
</body>
</html>
