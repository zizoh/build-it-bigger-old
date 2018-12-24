package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.gradle.builditbigger.javajokes.Joke;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "jokeApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     *  A simple endpoint method that returns a joke from the java library
     */
    @ApiMethod(name = "getJokeService")
    public MyBean getJokeService() {
        MyBean response = new MyBean();

        Joke joke = new Joke();
        String funnyJoke = joke.getJoke();
        response.setData(funnyJoke);

        return response;
    }

}
