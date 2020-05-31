import java.util.Iterator;
import java.util.List;

public interface DataBoard<E extends Data> {
    /*
        OVERVIEW: collezione mutabile di elementi derivati dal tipo Data, la collezione garantisce la privacy dei dati

        TYPICAL ELEMENT: <password, name, {category(0)....category(n)}, {data(0)....data(n)}, {friend(0)...friend(n)}>
        */

    //crea una nuova categoria di dati
    public void createCategory(String category, String passw) throws NullPointerException, AccessRefusedException;
    /*
        REQUIRES: category!=null && passw!=null && passw.equals(this.password)==true
        THROWS: se category==null || passw==null throws NullPointerException(unchecked), se passw.equals(this.password) 
                è false throws AccessRefusedException(checked)
        MODIFIES: this
        EFFECTS: inserisce in this la nuova categoria se questa non è già presente nella collezione
     */

    //rimuove la categoria category dalla collezione
    public void removeCategory(String category, String passw) throws NullPointerException, AccessRefusedException;
    /*
        REQUIRES: category!=null && passw!=null && passw.equals(this.password)==true
        THROWS: se category==null || passw==null throws NullPointerException(unchecked), se passw.equals(this.password) 
                è false throws AccessRefusedException(checked)
        MODIFIES: this
        EFFECTS: rimuove da this la categoria se essa appartiene a this, altrimenti non fa nulla: l'effetto ottenuto
                 è comunque che in this.post non si ha la categoria category
     */

    //aggiunge un amico ad una categoria di dati
    public void addFriend(String category, String passw, String friend) throws NullPointerException, AccessRefusedException, CategoryNotFoundException;
    /*
        REQUIRES: category!=null && passw!=null && friend!=null && passw.equals(this.password)==true &&
                  category appartiene a this
        THROWS: se category==null || passw==null || friend==null throws NullPointerException(unchecked), se 
                passw.equals(this.password) è false throws AccessRefusedException(checked), se category non è presente in
                this throws CategoryNotFound(checked)
        MODIFIES: this
        EFFECTS: inserisce friend nella lista di amici con cui ha condiviso la categoria category se quest'ultima è presente
                 in this, se friend ha già accesso a quella categoria il metodo non ha effetto: si ha comunque l'avvenuta
                 condivisione di quella categoria con friend in this.post
     */

    //rimuove un amico da una categoria di dati
    public void removeFriend(String category, String passw, String friend) throws NullPointerException, AccessRefusedException, CategoryNotFoundException;
    /*
        REQUIRES: category!=null && passw!=null && friend!=null && passw.equals(this.password)==true && (category appartiene
                  a this)
        THROWS: se category==null || passw==null || friend==null throws NullPointerException(unchecked), se 
                passw.equals(this.password) è false throws AccessRefusedException(checked), se cateogory non appartiene
                a this throws CategoryNotFoundException(checked)
        MODIFIES: this
        EFFECTS: rimuove un amico dalla lista degli amici che possono vedere quella particolare categoria di dati, se l'amico
                 è presente nella lista, altrimenti il metodo non ha effetto: poichè si ha in ogni caso il risultato che
                 l'amico friend non è più presente nella lista di amici di category
     */

    //inserisce un dato in bacheca se vengono rispettati i controlli di identità
    public boolean put(String passw, E data, String category) throws NullPointerException, AccessRefusedException, CategoryNotFoundException;
    /*
        REQUIRES: passw!=null && category!=null && data!=null && passw.equals(this.password)==true && category appartiene
                  a this
        THROWS: se passw==null || category==null || data==null throws NullPointerException(unchecked), se passw.equals(this.password)
                è false throws AccessRefusedException(checked), se category non appartiene a this throws CategoryNotFoundException(checked)
        MODIFIES: this
        EFFECTS: restituisce true se inserisce l'elemento nella bacheca, false se il dato è già presente
     */

    //ottiene una copia del dato in bacheca se vengono rispettati i controlli di identità
    public E get(String passw, E data) throws NullPointerException, AccessRefusedException;
    /*
        REQUIRES: passw!=null && data!=null && passw.equals(this.password)==true
        THROWS: se passw==null || data==null throws NullPointerException(unchecked), se passw.equals(this.password) 
                è false throws AccessRefusedException(checked)
        RETURN: restituisce una copia del dato in bacheca se i controlli della password sono rispettati e se data è presente
                nella bacheca, null se il dato non è presente
     */

    //rimuove il dato dalla bacheca
    public E remove(String passw, E data) throws NullPointerException, AccessRefusedException;
    /*
        REQUIRES: passw!=null && data!=null && passw.equals(this.password)==true
        THROWS se passw==null || data==null throws NullPointerException(unchecked), se passw.equals(this.password) 
                è false throws AccessRefusedException(checked)
        MODIFIES: this
        EFFECTS: rimuove e restituisce data se presente nella bacheca, null se data non è presente
     */

    //crea la lista dei dati in bacheca per una determinata categoria
    public List<E> getDataCategory(String passw, String category) throws NullPointerException, AccessRefusedException;
    /*
        REQUIRES: passw!=null && category!=null && passw.equals(this.password)==true && (category appartiene a this)
        THROWS: se passw==null || category==null throws NullPointerException(unchecked), se passw.equals(this.password) 
                è false throws AccessRefusedException(checked)
        RETURN: restituisce la lista di tutti i dati appartenenti alla categoria category, se esiste la categoria ma non ci sono dati
                restituisce la lista vuota, se la categoria non esiste restituisce null
     */

    //restituisce un iteratore che genera tutti i dati in bacheca ordinati rispetto al numero di like
    public Iterator<E> getIterator(String passw) throws NullPointerException, AccessRefusedException;
    /*
        REQUIRES: passw!=null && passw.equals(this.password)==true
        THROWS: se pasw==null throws NullPointerException(unchecked), se passw.equals(this.password) 
                è false throws AccessRefusedException(checked)
        RETURN: restituisce un iteratore che permette di scorrere una lista di dati ordinati in base al numero di like,
                restituisce null se non ci sono dati all'interno della bacheca
     */

    //aggiunge un like ad un dato
    public void insertLike(String friend, E data) throws NullPointerException, DataNotFoundException, FriendNotFoundException, CategoryNotFoundException;
    /*
        REQUIRES: friend!=null && data!=null && (data appartiene a this) && (friend deve avere accesso a data) &&
                  (data.category appartiene a this)
        THROWS: se friend==null || data==null throws NullPointerException(unchecked), se data non appartiene a this throws
                DataNotFoundException(checked), se quel dato non è stato condiviso con l'amico friend throws FriendNotFoundException(checked)
                se data.category non appartiene a this throws CategoryNotFoundException(checked)
        MODIFIES: this
        EFFECTS: modifica this aggiungendo, al dato che è stato condiviso con qual particolare amico, 1 al contatore
                 dei like
     */

    //restituisce un iteratore che genera tutti i dati condivisi in bacheca
    public Iterator<E> getFriendIterator(String friend) throws NullPointerException, FriendNotFoundException;
    /*
        REQUIRES: friend!=null && (friend deve essere presente in almeno una delle liste di condivisione)
        THROWS: se friend==null throws NullPointerException(unchecked), se friend non è presente in nessuna delle liste
                di condivisione throws FriendNotFoundException(checked)
        RETURN: restituisce un iteratore che permette a friend di visualizzare tutti i dati che sono stati condivisi con lui,
                restituisce null se non ci sono dati condivisi con lui, ma è comunque presente nella lista di condivisione
                di almeno una categoria
     */
}