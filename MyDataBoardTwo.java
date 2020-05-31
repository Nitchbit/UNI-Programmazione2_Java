import java.util.*;

public class MyDataBoardTwo<E extends Data> implements DataBoard<E> {
    /*
        ABSTRACT FUNCTION: <password, {category(0)...category(n)}> tale che f(password, category(i)) -->
                           --> <friendlist(i), datalist(i)> dove (friendlist --> {friend(0)..friend(n)},
                           datalist --> {data(0)..data(n)}) && 0<=i<n

        REPRESENTATION INVARIANT: password!=null && name!=null && structure !=null && couple!=null &&
                                  (for all i in structure --> structure.get(i)!=null dove 0<=i<structure.length()-1) &&
                                  (for all j in couple --> couple.get(j)!=null dove 0<=j<couple.length()-1)
     */

    private String password; //password del proprietario della bacheca
    private String name; //nome della bacheca
    private ArrayList<String> structure; //struttura di memorizzazione delle categorie
    private ArrayList<InternalData<E>> couple; //struttura di memorizzazione dei dati


    //costruttore che istanzia l'oggetto, inizializza solamente name e password, crea un hashmap vuota
    public MyDataBoardTwo(String dataBoardName, String passw) throws NullPointerException {
        /*
            REQUIRES: dataBoardName!=null && password!=null
            THROWS: se dataBoardName==null || password==null throws NullPointerException(unchecked)
            MODIFIES: this
            EFFECTS: istanzia l'oggetto assegnando un valore a name, un valore a password e creando una struttura dati
                     vuota
         */
        if(dataBoardName==null || passw==null) throw new NullPointerException();
        name=dataBoardName;
        this.password=passw;
        structure=new ArrayList<>();
        couple=new ArrayList<>();
    }

    @Override
    public void createCategory(String category, String passw) throws NullPointerException, AccessRefusedException {
        if(category==null || passw==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        try {
            this.structure.add(category);
            int index=this.structure.indexOf(category);
            this.couple.add(index, new InternalData<E>());
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCategory(String category, String passw) throws NullPointerException, AccessRefusedException {
        if(category==null || passw==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        try {
            int index=this.structure.indexOf(category);
            this.structure.remove(category);
            this.couple.remove(index);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addFriend(String category, String passw, String friend) throws NullPointerException, AccessRefusedException, CategoryNotFoundException {
        if(category==null || passw==null || friend==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        if(!this.structure.contains(category)) throw new CategoryNotFoundException();
        try {
            int index=this.structure.indexOf(category);
            (this.couple.get(index)).friendListAdd(friend);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFriend(String category, String passw, String friend) throws NullPointerException, AccessRefusedException, CategoryNotFoundException {
        if(category==null || passw==null || friend==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        if(!structure.contains(category)) throw new CategoryNotFoundException();
        try {
            int index=this.structure.indexOf(category);
            (this.couple.get(index)).friendListRemove(friend);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean put(String passw, E data, String category) throws NullPointerException, AccessRefusedException, CategoryNotFoundException {
        if(passw==null || data==null || category==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        if(!structure.contains(category)) throw new CategoryNotFoundException();
        try {
            data.setType(category);
            int index=this.structure.indexOf(category);
            return this.couple.get(index).dataListAdd(data);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public E get(String passw, E data) throws NullPointerException, AccessRefusedException {
        if(passw==null || data==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        try {
            return this.couple.get(this.structure.indexOf(data.getType())).dataListGet(data);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public E remove(String passw, E data) throws NullPointerException, AccessRefusedException {
        if(passw==null || data==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        try {
            return this.couple.get(this.structure.indexOf(data.getType())).dataListRemove(data);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<E> getDataCategory(String passw, String category) throws NullPointerException, AccessRefusedException {
        if(passw==null || category==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        try {
            int index=this.structure.indexOf(category);
            return this.couple.get(index).dataListGetAll();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterator<E> getIterator(String passw) throws NullPointerException, AccessRefusedException {
        if(passw==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        ArrayList<E> wholeDataBoard= new ArrayList<>();
        for(String key : this.structure) {
            try {
                int index=this.structure.indexOf(key);
                wholeDataBoard.addAll(this.couple.get(index).dataListGetAll());
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        }
        wholeDataBoard.sort(new Comparator<E>() { @Override public int compare(E e, E t1) {
            if(e.getNumLikes()==t1.getNumLikes()) return 0;
            return e.getNumLikes() - t1.getNumLikes(); } });
        return new MyIterator<>(wholeDataBoard.iterator());
    }

    @Override
    public void insertLike(String friend, E data) throws NullPointerException, DataNotFoundException, FriendNotFoundException, CategoryNotFoundException {
        if(friend==null || data==null) throw new NullPointerException();
        if(!this.structure.contains(data.getType())) throw new CategoryNotFoundException();
        if(!this.couple.get(this.structure.indexOf(data.getType())).friendListGetAll().contains(friend)) throw new FriendNotFoundException();
        if(!this.couple.get(this.structure.indexOf(data.getType())).dataListGetAll().contains(data)) throw new DataNotFoundException();
        InternalData<E> elem=this.couple.get(this.structure.indexOf(data.getType()));
        elem.dataListGetAll().get(elem.dataListGetAll().indexOf(data)).addLikes();
    }

    @Override
    public Iterator<E> getFriendIterator(String friend) throws NullPointerException, FriendNotFoundException {
        if(friend==null) throw new NullPointerException();
        ArrayList<E> wholePublicDataBoard= new ArrayList<>();
        int trovato=0;
        for(String key : this.structure) {
            if(this.couple.get(this.structure.indexOf(key)).friendListGetAll().contains(friend)) {
                trovato=1;
                wholePublicDataBoard.addAll(this.couple.get(this.structure.indexOf(key)).dataListGetAll());
            }
        }
        if(trovato==0) throw new FriendNotFoundException();
        if(wholePublicDataBoard.isEmpty()) return null;
        return new MyIterator<>(wholePublicDataBoard.iterator());
    }

    //restituisce la lista delle categorie
    public ArrayList<String> getCategoryList() {
        /*
            RETURN: this.structure;
         */
        return this.structure;
    }

    //tipo degli oggetti che ottengo se faccio structure.get(category) dove structure è la hashmap, category è una chiave
    private class InternalData<E extends Data> {
        /*
            ABSTRACT FUNCTION:
            REPRESENTATION INVARIANT: dataList!=null && friendList!=null
         */
        private ArrayList<E> dataList; //lista dei dati
        private ArrayList<String> friendList; //lista dei nomi che hanno accesso ai dati

        //metodo costruttore
        public InternalData() {
            /*
                MODIFIES: this
                EFFECTS: istanzia le due liste
             */
            dataList=new ArrayList<>();
            friendList=new ArrayList<>();
        }

        //aggiunge un dato alla lista
        public boolean dataListAdd(E data) throws NullPointerException {
            /*
                REQUIRES: data!=null
                THROWS: se data==null throws NullPointerException(unchecked)
                MODIFIES: this
                EFFECTS: dataList.add(data)
             */
            if(data==null) throw new NullPointerException();
            if(this.dataList.indexOf(data)!=-1) return false;
            return this.dataList.add(data);
        }

        //restituisce il dato se presente nella lista
        public E dataListGet(E data) throws NullPointerException {
            /*
                REQUIRES: data!=null
                THROWS: se data==null throws NullPointerException(unchecked)
                RETURN: this.dataList.get(dataList.indexOf(data)) se è presente data, null altrimenti
             */
            if(data==null) throw new NullPointerException();
            if(this.dataList.indexOf(data)==-1) return null;
            return this.dataList.get(this.dataList.indexOf(data));
        }

        //rimuove e restituisce il dato
        public E dataListRemove(E data) throws NullPointerException {
            /*
                REQUIRES: data!=null
                THROWS: se data==null throws NullPointerException(unchecked)
                MODIFIES: this
                EFFECTS: this.dataList.remove(data)
             */
            if(data==null) throw new NullPointerException();
            if(this.dataList.indexOf(data)==-1) return null;
            return this.dataList.remove(this.dataList.indexOf(data));
        }

        //aggiunge un amico alla lista
        public void friendListAdd(String friend) throws NullPointerException {
            /*
                REQUIRES: friend!=null
                THROWS: se friend==null throws NullPointerException
                MODIFIES: this
                EFFECTS: this.friendList.add(friend)
             */
            if(friend==null) throw new NullPointerException();
            this.friendList.add(friend);
        }

        //rimuove un amico dalla lista
        public void friendListRemove(String friend) throws NullPointerException {
            /*
                REQUIRES: friend!=null
                THROWS: se friend==null throws NullPointerException
                MODIFIES: this
                EFFECTS: this.friendList.remove(friend)
             */
            if(friend==null) throw new NullPointerException();
            this.friendList.remove(friend);
        }
        //restituisce l'intera lista dataList
        public ArrayList<E> dataListGetAll() {
            /*
                RETURN: this.dataList
             */
            return this.dataList;
        }

        //restituisce l'intera lista friendList
        public ArrayList<String> friendListGetAll() {
            /*
                RETURN: this.friendList
             */
            return this.friendList;
        }
    }
    private class MyIterator<E> implements Iterator<E> {

        private Iterator<E> itr;

        //metodo costruttore
        public MyIterator(Iterator<E> itr) throws NullPointerException {
            /*
                REQUIRES: itr!=null
                THROWS: se itr==null throws NullPointerException
                MODIFIES: this
                EFFECTS: inizializza this.itr
             */
            if(itr==null) throw new NullPointerException();
            this.itr=itr;
        }
        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public E next() {
            return itr.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
