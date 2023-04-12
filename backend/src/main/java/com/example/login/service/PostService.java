package com.example.login.service;


import com.example.login.dao.entities.Post;
import com.example.login.dao.repos.PostRepo;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PostService implements BasicService {

    private final PostRepo repo;

    public PostService(PostRepo repo) {
        this.repo = repo;
    }

    @Override
    public void add(Object request) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void edit(String name, int id) {

    }

    @Override
    public List<Post> showAll() {
        return repo.findAll();
    }
}
