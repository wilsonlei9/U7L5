import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        ArrayList<String> arr = new ArrayList<String>();
        String cast = "";
        for (int i = 0; i < movies.size(); i++) {
            cast += movies.get(i).getCast();
            cast += "|";

        }
        ArrayList<String> standIn = new ArrayList<String>();
        String[] subs = cast.split("\\|");
        for (String sub2 : subs) {
            standIn.add(sub2);
        }

        for (int i = 0; i < standIn.size(); i++) {
            for (int j = i + 1; j < standIn.size(); j++) {
                if (standIn.get(i).compareTo(standIn.get(j)) > 0) {
                    String temp = standIn.get(i);
                    standIn.set(i, standIn.get(j));
                    standIn.set(j, temp);
                }
            }
        }

        ArrayList<String> castList = new ArrayList<String>();
        String check = "";
        for (String sub : standIn) {
            if (!sub.equals(check)) {
                castList.add(sub);
                check = sub;
            }
        }

        ArrayList<String> actorList = new ArrayList<String>();
        System.out.print("Enter a cast member: ");
        Scanner s = new Scanner(System.in);
        String choice = s.nextLine();
        for (int i = 0; i < castList.size(); i++)
        {
            if (castList.get(i).contains(choice))
            {
                actorList.add(castList.get(i));
            }
        }

        for (int i = 0; i < actorList.size(); i++)
        {
            System.out.println(i + 1 + ". " + actorList.get(i));
        }

        int choice2 = s.nextInt();
        ArrayList<Movie> movie = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++)
        {
            if (movies.get(i).getCast().contains(actorList.get(choice2 - 1)))
            {
                movie.add(movies.get(i));
            }
        }
        sortResults(movie);
        for (int i = 0; i < movie.size(); i++)
        {
            System.out.println(i + 1 + ". " + movie.get(i));
        }

        int choice3 = s.nextInt();
        displayMovieInfo(movie.get(choice3 - 1));
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String keyword = movies.get(i).getKeywords();
            keyword = keyword.toLowerCase();

            if (keyword.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres() {
        String genres = "";
        ArrayList<String> arr = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++) {
            genres += movies.get(i).getGenres();
            genres += "|";

        }
        ArrayList<String> standIn = new ArrayList<String>();
        String[] subs = genres.split("\\|");
        for (String sub2 : subs) {
            standIn.add(sub2);
        }

        for (int i = 0; i < standIn.size(); i++) {
            for (int j = i + 1; j < standIn.size(); j++) {
                if (standIn.get(i).compareTo(standIn.get(j)) > 0) {
                    String temp = standIn.get(i);
                    standIn.set(i, standIn.get(j));
                    standIn.set(j, temp);
                }
            }
        }

        ArrayList<String> genreList = new ArrayList<String>();
        String check = "";
        for (String sub : standIn) {
            if (!sub.equals(check)) {
                genreList.add(sub);
                check = sub;
            }
        }
        System.out.println(genreList);

        for (int i = 0; i < genreList.size(); i++) {
            System.out.println(i + 1 + ". " + genreList.get(i));
        }

        Scanner s = new Scanner(System.in);
        int choice = s.nextInt();

        ArrayList<Movie> movie = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getGenres().contains(genreList.get(choice - 1))) {
                movie.add(movies.get(i));
            }
        }

        for (int i = 0; i < movie.size(); i++) {
            System.out.println(i + 1 + " " + movie.get(i));

        }
        int choice2 = s.nextInt();
        displayMovieInfo(movie.get(choice2 - 1));
    }

    private void listHighestRated()
    {
        ArrayList<Movie> moviesList = new ArrayList<Movie>(movies);
        ArrayList<Movie> rating = new ArrayList<Movie>(movies);

    }

    private void listHighestRevenue()
    {

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
