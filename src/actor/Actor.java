package actor;

import video.Show;
import video.ShowList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class Actor {
    private String name;
    private String careerDescription;
    private Double rating;
    private ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    // Constructor
    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.rating = 0.0;
    }

    // Getters and toString
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }

    // Methods for Queries
    public Double getRating(ShowList showList) {
        double newRating = 0.0;
        double count = 0.0;
        List<Show> list = showList.getShowList();
        for (var film : filmography) {
            for (var show : list)
                if (show.getTitle().equals(film) && show.getRating() > 0) {
                    newRating += show.getRating();
                    count++;
                }
        }
        if (count != 0)
            return newRating / count;
        return 0.0;
    }

    // Returns the number of searched awards
    // if it doesn't contain all the awards we searched
    // it returns zero
    public int noOfAwards(List<String> searchedAwards) {
        AtomicInteger count = new AtomicInteger();
        AtomicInteger matchingAwards = new AtomicInteger();
        for (var award : searchedAwards) {
            awards.forEach((a, n) -> {
                if (award.equals(a.toString())) {
                    count.addAndGet(n);
                    matchingAwards.getAndIncrement();
                }
            });
        }
        // Verifying if the actor has all the awards we searched for
        if (matchingAwards.get() == searchedAwards.size()) {
            return count.get();
        }
        return 0;
    }

    public boolean filterDescription(List<String> words) {
        int count = 0;
        for (String word : words) {
            if (getCareerDescription().toLowerCase()
                    .contains(word.toLowerCase())) {
                count++;
            }
        }
        return count == words.size();
    }
}
