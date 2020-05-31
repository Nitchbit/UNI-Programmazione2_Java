import java.util.ArrayList;

//IMPLEMENTARE LA DEEP COPY ???

public class MyData implements Data {
    /*
        ABSTRACT FUNCTION(artistName, numlikes, artistPlaylist, artistSogns, categoryTag)=<artistName, numlikes,
                         artistPlaylist, artistSogns, categoryTag> se essi sono validi

        REPRESENTATION INVARIANT: artistName!=null && numLikes>=0 &&  artistsPlaylist!=null && artistSongs!=null &&
                                  categoryTag!=null && !artistName.isEmpty()
     */

    //nome che identifica il dato
    private String artistName;
    //numero di likes
    private int numLikes;
    //lista di canzoni preferite dall'artista
    private ArrayList<String> artistPlaylist;
    //lista di canzoni pubblicate dall'artista
    private ArrayList<String> artistSongs;
    //categoria a cui appartiene l'artista
    private String categoryTag;

    //metodo costruttore che inizializza le variabili d'istanza
    public MyData(String artistName, ArrayList<String> artistPlaylist, ArrayList<String> artistSongs, String categoryTag) throws NullPointerException, IllegalArgumentException {
        /*
            REQUIRES: artistName!=null && artistPlaylist!=null && artistSongs!=null && categoryTag!=null && !artistName.isEmpty()
            THROWS: se artistName==null || artistPlaylist==null || artistSogns==null || categoryTag==null throws NullPointerException(unchecked),
                    se !artistName.isEmpty() || categoryTag.length()==0 throws IllegalArgumentException(unchecked)
            MODIFIES: this
            EFFECTS: this.artistName=artistName, this.artistPlaylist=artistPlaylist, this.artistSongs=artistSongs,
                     this.categoryTag=categoryTag, this.numLikes=0
         */
        if(artistName==null || artistPlaylist==null || artistSongs==null || categoryTag==null) throw new NullPointerException();
        if(artistName.isEmpty() || categoryTag.length()==0) throw new IllegalArgumentException();
        this.artistName=artistName;
        this.numLikes=0;
        this.artistPlaylist=artistPlaylist;
        this.artistSongs=artistSongs;
        this.categoryTag=categoryTag;
    }

    @Override
    public void display() {
        StringBuilder displayPlaylist= new StringBuilder();
        StringBuilder displaySongs= new StringBuilder();
        for (String artistPlaylist : this.getArtistPlaylist()) displayPlaylist.append(artistPlaylist).append(" ");
        for (String artistSong : this.getArtistSongs()) displaySongs.append(artistSong).append(" ");
        String displayString="Artist Name: " + this.getArtistName() + "\nArtist Likes: " + this.getNumLikes() +
                "\nArtist Playlist: " + displayPlaylist + "\nArtist Songs: " + displaySongs + "\nArtist Genre: " +
                this.getCategoryTag() + "\n";
        System.out.println(displayString);
    }

    @Override
    public void setType(String category) throws NullPointerException {
        if(category==null) throw new NullPointerException();
        this.categoryTag=category;
    }

    @Override
    public String getType() {
        return this.categoryTag;
    }

    @Override
    public void addLikes() {
        this.numLikes++;
    }

    //metodo che restituisce il nome dell'artista
    private String getArtistName() {
        /*
            RETURN: this.categoryTag
         */
        return artistName;
    }

    //metodo che restituisce il numero di likes
    public int getNumLikes() {
        /*
            RETURN: this.numLikes
         */
        return numLikes;
    }

    @Override
    public boolean equals(Object o) {
        if(o==null) return false;
        if(o instanceof MyData) {
            MyData other=(MyData) o;
            if(other.artistName.equals(this.artistName) && other.artistPlaylist.equals(this.artistPlaylist) &&
            other.artistSongs.equals(this.artistSongs) && other.categoryTag.equals(this.categoryTag)) return true;
            else return false;
        }
        else return false;
    }

    //metodo che restituisce la playlist consigliata dall'artista
    private ArrayList<String> getArtistPlaylist() {
        /*
            RETURN: this.artistPlaylist
         */
        return artistPlaylist;
    }

    //metodo che restituisce la lista di tutte le canzoni pubblicate dall'artista
    private ArrayList<String> getArtistSongs() {
        /*
            RETURN: this.artistSongs
         */
        return artistSongs;
    }

    //metodo che restituisce il genere a cui appartiene l'artista
    private String getCategoryTag() {
        /*
            RETURN: this.categoryTag
         */
        return categoryTag;
    }
}