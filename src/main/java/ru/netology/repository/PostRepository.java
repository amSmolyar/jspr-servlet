package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;

// Stub
public class PostRepository {
  private Map<Long, Post> postMap;
  private Long idCnt = (long) 1;

  public PostRepository() {
    postMap = new HashMap<>();
  }

  public List<Post> all() {
    List<Post> list = new ArrayList<>(postMap.values());
    return list;
  }

  public Optional<Post> getById(long id) {
    return (postMap.containsKey(id)) ? Optional.of(postMap.get(id)) : Optional.empty();
  }

  public synchronized Post save(Post post) {
    if ((post.getId() == 0) || (!postMap.containsKey(post.getId()))) {
      Post newPost = new Post(idCnt, post.getContent());
      postMap.put(idCnt, newPost);
      idCnt++;
      return newPost;
    }

    postMap.put(post.getId(), post);
    return post;
  }

  public synchronized void removeById(long id) {
    if (postMap.containsKey(id))
      postMap.remove(id);
  }
}
