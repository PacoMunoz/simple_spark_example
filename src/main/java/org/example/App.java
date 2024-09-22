package org.example;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.example.exception.UserAlreadyExitsException;
import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.example.service.UserService;
import spark.Spark;

import java.util.HashSet;

public class App 
{
    public static void main( String[] args ) {

        final var userService = new UserService(new HashSet<>());
        Spark.port(8080);

        Spark.exception(UserNotFoundException.class, (ex, req, res) -> {
            res.body("Error: " + ex.getMessage());
            res.status(HttpStatus.NOT_FOUND_404);
        });

        Spark.exception(UserAlreadyExitsException.class, (ex, req, res) -> {
            res.body("Error: " + ex.getMessage());
            res.status(HttpStatus.BAD_REQUEST_400);
        });

        Spark.get("/v1/users/:documentNumber", (req, res) -> {
            final var user = userService.getUser(req.params(":documentNumber"));
            if (user.isPresent()) {
                return new Gson().toJson(user.get());
            }
            res.status(HttpStatus.NOT_FOUND_404);
            return "";
        });

        Spark.get("/v1/users", (req, res) -> {
            final var users = userService.users();
            return new Gson().toJson(users);
        });

        Spark.post("/v1/users", (req, res) -> {
            final var user = userService.createUser(new Gson().fromJson(req.body(), User.class));
            res.status(HttpStatus.CREATED_201);
            return new Gson().toJson(user);
        });

        Spark.put("/v1/users/:documentNumber", ((req, res) -> {
            final var user = userService.updateUser(new Gson().fromJson(req.body(), User.class));
            res.status(HttpStatus.NO_CONTENT_204);
            return new Gson().toJson(user);
        }));

        Spark.delete("/v1/users/:documentNumber", (req, res) -> {
            userService.deleteUser(req.params(":documentNumber"));
            res.status(HttpStatus.NO_CONTENT_204);
            return "";
        });
    }
}
