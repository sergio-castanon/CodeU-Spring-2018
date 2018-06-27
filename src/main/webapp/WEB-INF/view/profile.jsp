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
<!DOCTYPE html>
<%@page import="codeu.helper.ProfileHelper"%>
<%@page import="codeu.model.store.basic.ProfileStore" %>
<html>
<head>
  <title>Git Rekt's Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">Git Rekt's Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">
      <% 
       String user = (String) request.getSession().getAttribute("user"); 
       String profileOwner = (String) request.getAttribute("profileName"); 
      %>
       <h1><%= profileOwner %>'s Profile</h1>
       <hr>
       <h2>About Me</h2>
       <p id="aboutMe"><%= ProfileStore.getInstance().getProfileText(profileOwner) %></p>
       <% if (ProfileHelper.isSameUser(user, profileOwner)) { %> 
        <h3>Edit</h3>
        <hr>
        <form action="/profile/<%= profileOwner %>" method="POST">
        	<textarea cols="50" rows="5" id="description" name="description" 
        		placeholder="Edit your About Me section here."></textarea> 
        	<br/>
        	<button type="submit">Submit</button>
        </form>
      <% } %>
        
    </div>
  </div>
</body>
</html>
