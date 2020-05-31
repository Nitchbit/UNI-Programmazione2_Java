//eccezione per indicare che nessun dato con quel nome è stato trovato
class DataNotFoundException extends Exception {
    public DataNotFoundException() {
        super();
    }
    public DataNotFoundException(String s) {
        super(s);
    }
}