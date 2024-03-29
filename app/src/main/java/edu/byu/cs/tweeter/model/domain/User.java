package edu.byu.cs.tweeter.model.domain;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.SortedList;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a user in the system.
 */
public class User implements Comparable<User>, Serializable {

    private final String firstName;
    private final String lastName;
    private final String alias;
    private final String imageUrl;
    private byte [] imageBytes;
    private Story story;
    private Feed feed;

    public User(String firstName, String lastName, String imageURL) {
        this(firstName, lastName, String.format("@%s%s", firstName, lastName), imageURL, null, null);
    }

    public User(String firstName, String lastName, String alias, String imageURL, Story story, Feed feed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.imageUrl = imageURL;
        this.story = story;
        this.feed = feed;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getAlias() {
        return alias;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public byte [] getImageBytes() {
        return imageBytes;
    }

    public Story getStory() {
        return story;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return alias.equals(user.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", alias='" + alias + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public int compareTo(User user) {
        return this.getAlias().compareTo(user.getAlias());
    }
}
