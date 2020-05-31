//eccezione per indicare che nessuna categoria è stata trovata
class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException() {
        super();
    }
    public CategoryNotFoundException(String s) {
        super(s);
    }
}