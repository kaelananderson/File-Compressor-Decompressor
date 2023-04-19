import java.util.*;

public class HashTableChain<K, V> implements KWHashMap<K, V> {

    public static class Entry<K, V> {

        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V val) {
            V oldVal = value;
            value = val;
            return oldVal;
        }

        public String toString() {
            return key.toString() + "=" + value.toString();
        }

    }

    private LinkedList<Entry<K, V>>[] table;
    private int numKeys;
    private int totRehash = 0;
    private static final int CAPACITY = 101; 
    private static final double LOAD_THRESHOLD = 5.0;

    //default constructor
    public HashTableChain() {
        table = new LinkedList[CAPACITY]; 
        numKeys = 0;
    }

    //non default constructor
    public HashTableChain(int capacity) {
        table = new LinkedList[capacity];
        numKeys = 0;
    }

    @Override
    public V get(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        if (table[index] == null) {
            return null;
        }

        for (Entry<K,V>nextItem: table[index]) {
            if (nextItem.getKey().equals(key)) {
                return nextItem.getValue();
            }
        }
        return null;
    } 

    @Override 
    public V put(K key, V value) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }

        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        for (Entry<K, V> nextItem : table[index]) {
            if (nextItem.getKey().equals(key)) {
                V oldVal = nextItem.getValue(); 
                nextItem.setValue(value); 
                return oldVal;
            }
        }
        table[index].addFirst(new Entry<>(key, value)); 
        numKeys++;
        if (numKeys > (LOAD_THRESHOLD * table.length)) {
            rehash();
        }    
        return null;
    }

    public int size() {
        return numKeys;
    }

    public boolean isEmpty() {
        return numKeys == 0;
    }

    @Override
    public V remove(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }

        if (table[index] == null) {
            return null;
        }

        for (Entry<K, V> nextItem : table[index]) {
            if (nextItem.getKey().equals(key)) {
                table[index].remove(nextItem);
                numKeys--;
                if (table[index].isEmpty()) {
                    table[index] = null;
                }
                return nextItem.getValue();
            }
        }
        return null;
    }

    private void rehash() {
        LinkedList<Entry<K, V>>[] oldTable = table; 
        table = new LinkedList[2* oldTable.length + 1];
        
        numKeys = 0;

        for (int i = 0; i < oldTable.length; i++) {
            for (Entry<K, V> nextItem : oldTable[i]) {
                put(nextItem.getKey(), nextItem.getValue());
            }
        }
    
        totRehash++;
    } 
    
    public int getTotRehash() {
       return totRehash;
    }    
                

}
