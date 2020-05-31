import java.util.*;

public class MyDataBoardOne<E extends Data> implements DataBoard<E> {
    /*
        ABSTRACT FUNCTION: <password,{category(0)...category(n)}> tale che f(password, category(i)) -->
                           --> <friendlist(i), datalist(i)> dove (friendlist --> {friend(0)..friend(n)},
                           datalist --> {data(0)..data(n)}) && 0<=i<n

        REPRESENTATION INVARIANT: password!=null && name!=null && structure !=null && (for all K in structure.keySet()
                                  --> k!=null) && (for all K in structure.keySet() --> structure.get(K)!=null)
     */

    private String password; //password del proprietario della bacheca
    private String name; //nome della bacheca
    private HashMap<String, InternalData<E>> structure; //struttura di memorizzazione dei dati

    //costruttore che istanzia l'oggetto, inizializza solamente name e password, crea un hashmap vuota
    public MyDataBoardOne(String dataBoardName, String passw) throws NullPointerException {
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
        structure=new HashMap<String, InternalData<E>>();
    }

    @Override
    public void createCategory(String category, String passw) throws NullPointerException, AccessRefusedException {
        if(category==null || passw==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        try {
            this.structure.put(category, new InternalData<E>());
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCategory(String category, String passw) throws NullPointerException, AccessRefusedException {
        if(category==null || passw==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        try {
            this.structure.remove(category);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addFriend(String category, String passw, String friend) throws NullPointerException, AccessRefusedException, CategoryNotFoundException {
        if(category==null || passw==null || friend==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        if(!structure.containsKey(category)) throw new CategoryNotFoundException();
        try {
            (this.structure.get(category)).friendListAdd(friend);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFriend(String category, String passw, String friend) throws NullPointerException, AccessRefusedException, CategoryNotFoundException {
        if(category==null || passw==null || friend==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        if(!structure.containsKey(category)) throw new CategoryNotFoundException();
        try {
            (this.structure.get(category)).friendListRemove(friend);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean put(String passw, E data, String category) throws NullPointerException, AccessRefusedException, CategoryNotFoundException {
        if(passw==null || data==null || category==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        if(!structure.containsKey(category)) throw new CategoryNotFoundException();
        try {
            data.setType(category);
            return this.structure.get(category).dataListAdd(data);
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
            return this.structure.get(data.getType()).dataListGet(data);
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
            return this.structure.get(data.getType()).dataListRemove(data);
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
            return this.structure.get(category).dataListGetAll();
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterator<E> getIterator(String passw) throws NullPointerException, AccessRefusedException {
        if(passw==null) throw new NullPointerException();
        if(!passw.equals(this.password)) throw new AccessRefusedException();
        Set<String> mySet=structure.keySet();
        ArrayList<E> wholeDataBoard= new ArrayList<>();
        for(String key : mySet) {
            try {
                wholeDataBoard.addAll(this.structure.get(key).dataListGetAll());
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
        if(!this.structure.containsKey(data.getType())) throw new CategoryNotFoundException();
        if(!this.structure.get(data.getType()).friendListGetAll().contains(friend)) throw new FriendNotFoundException();
        if(!this.structure.get(data.getType()).dataListGetAll().contains(data)) throw new DataNotFoundException();
        InternalData<E> elem=this.structure.get(data.getType());
        elem.dataListGetAll().get(elem.dataListGetAll().indexOf(data)).addLikes();
    }

    @Override
    public Iterator<E> getFriendIterator(String friend) throws NullPointerException, FriendNotFoundException {
        if(friend==null) throw new NullPointerException();
        ArrayList<E> wholePublicDataBoard= new ArrayList<>();
        int trovato=0;
        for(String key : this.structure.keySet()) {
            if(this.structure.get(key).friendListGetAll().contains(friend)) {
                trovato=1;
                wholePublicDataBoard.addAll(this.structure.get(key).dataListGetAll());
            }
        }
        if(trovato==0) throw new FriendNotFoundException();
        if(wholePublicDataBoard.isEmpty()) return null;
        return new MyIterator<>(wholePublicDataBoard.iterator());
    }

    //metodo che restituisce la lista delle categorie
    public Set<String> getCategoryList() {
        /*
            RETURN: this.structure.keySet()
         */
        return this.structure.keySet();
    }

    //tipo degli oggetti che ottengo se faccio structure.get(category) dove structure è la hashmap, category è una chiave
    private class InternalData<E extends Data> {
        /*
            ABSTRACT FUNCTION:(datalist, friendlist)=<datalist, friendlist> se datalist, friendlist sono validi
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
                THROWS: se itr==null throws new NullPointerException(unchecked)
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
