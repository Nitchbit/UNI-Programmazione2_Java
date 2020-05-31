import java.util.ArrayList;
import java.util.Iterator;

public class MainTest {
    public static void main(String[] args) {
        //work test
        String password = "password";
        MyDataBoardOne<Data> board = new MyDataBoardOne<Data>("bacheca", password);
        String category = "";
        if(Integer.parseInt(args[0])==1) {
            System.out.println("CREATE CATEGORY TEST");
            //createCategory test
            for (int i = 0; i < 3; i++) {
                category = "Tag" + i;
                try {
                    board.createCategory(category, password);
                } catch (NullPointerException | AccessRefusedException e) {
                    System.out.println(e);
                }
            }
            System.out.println("Category list: ");
            for (Object elem : board.getCategoryList()) {
                System.out.println(elem.toString());
            }
            System.out.println("REMOVE CATEGORY TEST");
            //removeCategory test
            try {
                board.removeCategory("Tag0", password);
            } catch (NullPointerException | AccessRefusedException e) {
                System.out.println(e);
            }
            System.out.println("Category list: ");
            for (Object elem : board.getCategoryList()) {
                System.out.println(elem.toString());
            }
            System.out.println("ADD FRIEND TEST");
            //addFriend test
            for (int i = 0; i < 12; i++) {
                try {
                    if (i % 2 == 0) board.addFriend("Tag1", password, "friend" + i);
                    else board.addFriend("Tag2", password, "friend" + i);
                    System.out.println("Added friend"+i);
                } catch (NullPointerException | AccessRefusedException | CategoryNotFoundException e) {
                    System.out.println(e);
                }
            }
            System.out.println("GET FRIEND ITERATOR TEST");
            //getFriendIterator test
            for (int i = 0; i < 12; i++) {
                //utilizzo il test di getFriendIterator per testare l'effetto delle addFriend, se l'inserimento non è andato
                //a buon fine mi aspetto un'eccezione
                try {
                    //se gli amici sono presenti nella bacheca mi aspetto che l'iteratore sia "vuoto", cioè non ci sono
                    //ancora dati condivisi con essi
                    System.out.print("List of friend"+i+" ");
                    Iterator myIt = board.getFriendIterator("friend" + i);
                    if (myIt == null) System.out.println("Empty list");
                } catch (NullPointerException | FriendNotFoundException e) {
                    System.out.println(e);
                }
            }
            System.out.println("REMOVE FRIEND TEST");
            //removeFriend test
            try {
                board.removeFriend("Tag1", password, "friend0");
                System.out.println("Friend0 removed");
            } catch (NullPointerException | AccessRefusedException | CategoryNotFoundException e) {
                System.out.println(e);
            }
            //uso getFriendIterator per vedere se la rimozione è andata a buon fine, se è cosi mi aspetto un'eccezione
            try {
                System.out.print("Getting iterator for friend0: ");
                board.getFriendIterator("friend0");
            } catch (NullPointerException | FriendNotFoundException e) {
                System.out.println("FriendNotFoundException received");
            }
            ArrayList<String> playlist = new ArrayList<>();
            ArrayList<String> songs = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                playlist.add("playlistSong" + j);
                songs.add("song" + j);
            }
            System.out.println("PUT TEST && INSERT LIKE TEST");
            //put test && insertLike test && test FriendNotFoundExcpetion
            for (int i = 1; i < 10; i++) {
                try {
                    if(i%2==0) category="Tag1";
                    else category="Tag2";
                    MyData elem = new MyData("artist" + i, playlist, songs, category);
                    if (board.put(password, elem, category)) System.out.println("Data inserted");
                    //aggiunge un like e testa al contempo se l'amico non è presente nella lista di condivisione
                    for (int j = 0; j < (int) (Math.random() * 10); j++) {
                        board.insertLike("friend"+i, elem);
                    }
                    System.out.println("Like inserted");
                } catch (NullPointerException | AccessRefusedException | CategoryNotFoundException | DataNotFoundException | FriendNotFoundException e) {
                    System.out.println(e);
                }
            }
            //get test
            System.out.println("GET TEST");
            for (int i = 1; i < 4; i++) {
                //uso la get per testare anche il risultato delle put
                try {
                    if(i%2==0) category="Tag1";
                    else category="Tag2";
                    board.get(password, new MyData("artist" + i, playlist, songs, category)).display();
                } catch (NullPointerException | AccessRefusedException e) {
                    System.out.println(e);
                }
            }
            System.out.println("REMOVE TEST");
            //remove test
            for (int i = 4; i < 8; i++) {
                try {
                    if(i%2==0) category="Tag1";
                    else category="Tag2";
                    board.remove(password, new MyData("artist" + i, playlist, songs, category)).display();
                } catch (NullPointerException | AccessRefusedException e) {
                    System.out.println(e);
                }
            }
            System.out.println("DATA CATEGORY REST");
            //getDataCategory test
            for (Object elem : board.getCategoryList()) {
                try {
                    for (Data el : board.getDataCategory(password, (String) elem)) {
                        el.display();
                    }
                } catch (NullPointerException | AccessRefusedException e) {
                    System.out.println(e);
                }
            }
            System.out.println("ITERATOR TEST");
            //getIterator test
            try {
                Iterator mine = board.getIterator(password);
                while (mine.hasNext()) {
                    MyData elem = (MyData) mine.next();
                    elem.display();
                }
            } catch(NullPointerException | AccessRefusedException e) {
                System.out.println(e);
            }
        }
        if(Integer.parseInt(args[0])==2) {
            //Exceptions test
            //createCategory exceptions test
            System.out.println("CREATE CATEGORY EXCEPTION");
            try {
                //board.createCategory(null, password);
                //board.createCategory("TagX", null);
                board.createCategory("TagX", "notpass");
            } catch (NullPointerException | AccessRefusedException e) {
                System.out.println(e);
            }
            //removeCategory exceptions test
            System.out.println("REMOVE CATEGORY EXCEPTION");
            try {
                //board.removeCategory(category, password);
                //board.removeCategory("TagX", null);
                board.removeCategory("TagX", "notpass");
            } catch (NullPointerException | AccessRefusedException e) {
                System.out.println(e);
            }
            System.out.println("ADD FRIEND EXCEPTION");
            //addFriend exceptions test
            try {
                //board.addFriend(null, password, "friend0");
                board.createCategory("TagX", password);
                //board.addFriend("TagX", null);
                //board.addFriend("TagX", password, null);
                //board.addFriend("TagX", "notpass", "friend0");
                board.removeCategory("TagX", password);
                board.addFriend("TagX", password, "fiend0");
            } catch(NullPointerException | AccessRefusedException | CategoryNotFoundException e) {
                System.out.println(e);
            }
            System.out.println("REMOVE FRIEND EXCEPTION");
            //removeFriend exceptions test
            try {
                //board.removeFriend(null, password,"friend0");
                //board.removeFriend("TagX", null, "friend0");
                //board.removeFriend("TagX", password, null);
                board.createCategory("TagX", password);
                //board.removeFriend("TagX", "notpass", "friend0");
                board.removeCategory("TagX", password);
                board.removeFriend("TagX", password, "friend0");
            } catch(NullPointerException | AccessRefusedException | CategoryNotFoundException e) {
                System.out.println(e);
            }
            System.out.println("PUT EXCEPTION");
            try {
                MyData data=new MyData("name", new ArrayList<>(), new ArrayList<>(), "TagX");
                //board.put(null, data, "TagX");
                //board.put(password, null, "TagX");
                //board.put(password, data, null);
                //board.put("notpass", data, "TagX");
                board.put(password, data, "TagX");
            } catch(NullPointerException | AccessRefusedException | CategoryNotFoundException e) {
                System.out.println(e);
            }
            System.out.println("GET EXCEPTION");
            try {
                MyData data=new MyData("name", new ArrayList<>(), new ArrayList<>(), "TagX");
                //board.get(null, data);
                //board.get(password, null);
                board.get("notpass", data);
            } catch(NullPointerException | AccessRefusedException e) {
                System.out.println(e);
            }
            System.out.println("REMOVE EXCEPTION");
            try {
                MyData data=new MyData("name", new ArrayList<>(), new ArrayList<>(), "TagX");
                //board.remove(null, data);
                //board.remove(password, null);
                board.remove("notpass", data);
            } catch(NullPointerException | AccessRefusedException e) {
                System.out.println(e);
            }
            System.out.println("GET DATA CATEGORY EXCEPTION");
            try {
                //board.getDataCategory(null, "TagX");
                //board.getDataCategory(password, null);
                board.getDataCategory("notpass", "TagX");
            } catch(NullPointerException | AccessRefusedException e) {
                System.out.println(e);
            }
            System.out.println("GET ITERATOR EXCEPTION");
            try {
                //board.getIterator(null);
                board.getIterator("notpass");
            } catch(NullPointerException | AccessRefusedException e) {
                System.out.println(e);
            }
            System.out.println("INSERT LIKE TEST");
            try {
                MyData data=new MyData("name", new ArrayList<>(), new ArrayList<>(), "TagX");
                //board.insertLike(null, data);
                //board.insertLike("friend0", null);
                board.createCategory("TagX", password);
                board.addFriend("TagX", password, "friend0");
                board.remove(password, data);
                board.insertLike("friend0", data);
            } catch (NullPointerException | DataNotFoundException | FriendNotFoundException | CategoryNotFoundException | AccessRefusedException e) {
                System.out.println(e);
            }
            System.out.println("GET FRIEND ITERATOR");
            try {
                //board.getFriendIterator(null);
                board.getFriendIterator("friend1");
            } catch(NullPointerException | FriendNotFoundException e) {
                System.out.println(e);
            }
        }
    }
}
