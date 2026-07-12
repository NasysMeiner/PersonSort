package persistence;

public interface StateLoader {
    byte[] loadState(String fileName);
}