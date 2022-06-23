package com.example.task3.DAO;

import com.example.task3.Model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<T, ID> {
    User find(ID id) throws SQLException;
    List<T> findAll() throws SQLException;
    void insert (T t) throws SQLException;
    boolean update(T t) throws SQLException;
    boolean delete(ID id) throws SQLException;
}
