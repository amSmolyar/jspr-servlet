package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
  private PostController controller;

  @Override
  public void init() {
    final var repository = new PostRepository();
    final var service = new PostService(repository);
    controller = new PostController(service);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // primitive routing
    final var path = req.getRequestURI();
    if (path.equals("/api/posts")) {
      controller.all(resp);
      return;
    }

    if (req.getContextPath().matches("/api/posts/\\d+")) {
      // easy way
      final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
      controller.getById(id, resp);
      return;
    }

    super.doGet(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final var path = req.getRequestURI();
    if (path.equals("/api/posts")) {
      controller.save(req.getReader(), resp);
      return;
    }

    super.doPost(req, resp);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final var path = req.getRequestURI();
    if (path.matches("/api/posts/\\d+")) {
      // easy way
      final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
      controller.removeById(id, resp);
      return;
    }

    super.doDelete(req, resp);
  }



}

