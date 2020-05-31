//eccezione per rifiutare l'accesso quando la password è errata
class AccessRefusedException extends Exception {
    public AccessRefusedException() {
        super();
    }
    public AccessRefusedException(String s) {
        super(s);
    }
}