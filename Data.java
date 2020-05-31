public interface Data {
    /*
        OVERVIEW: tipo di dato mutabile che rappresenta una pagina Spotify, dove è presente il nome dell'artista, il numero
                  di likes che ha ricevuto quell'artista, una lista di canzoni scelte dall'artista, una lista di tutte
                  le canzoni pubblicate dall'artista, un tag che indica il genere(la categoria) a cui appartiene l'artista

        TYPICAL ELEMENT: <artistName, numLikes, artistPlaylist, artistSongs, categoryTag>
     */

    //metodo che stampa tutti i valori degli attributi di this
    public void display();
    /*
        EFFECTS: stampa this.artistName, this.numLikes, this.artistPlaylist, this.artistSongs, this.categoryTag
     */

    //metodo che associa la categoria a this
    public void setType(String category) throws NullPointerException;
    /*
        REQUIRES: category!=null
        THROWS: se category==null throws NullPointerException(unchecked)
        MODIFIES: this
        EFFECTS: this.categoryTag=category
     */

    //metodo che restituisce la categoria del dato
    public String getType();
    /*
        RETURN: restituisce this.categoryTag
     */

    //metodo per incrementare i likes di un dato
    public void addLikes();
    /*
        MODIFIES: this
        EFFECTS: this.numLikes++
     */

    //metodo che restituisce i likes
    public int getNumLikes();
    /*
        RETURN: this.numLikes
     */
}
