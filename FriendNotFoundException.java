//eccezione per indicare che nessun amico con quel nome è stato trovato
class FriendNotFoundException extends Exception {
    public FriendNotFoundException() {
        super();
    }
    public FriendNotFoundException(String s) {
        super(s);
    }
}