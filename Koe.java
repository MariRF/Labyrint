import java.util.Iterator;

class Koe<T> implements Liste<T> {
    protected Node foran;
    protected Node sist;
    protected int storrelse = 0;

    public int storrelse(){
        return storrelse;
    }
    public boolean erTom(){
        return storrelse == 0;
    }

    public void settInn(T element){
        Node ny = new Node(element);
        Node temp = foran;
        if(foran == null){
            foran = ny;
            storrelse++;
            return;
        }
        while(temp.neste != null){
            temp = temp.neste;
        }
        temp.neste = ny;
        storrelse++;
    }

    public T fjern(){
        if (erTom()){
            return null;
        }
        if (foran != null){
            Node temp = foran;
            foran = foran.neste;
            storrelse--;
            return temp.hentInnhold();

        }
        return null;
    }

    protected class Node {
        T element;
        Node neste;
        Node forrige;

        public Node(T element){
            this.element = element;
        }
        public T hentInnhold(){
            return element;
        }
    }

    public Iterator<T> iterator(){
        return new KoeIterator();
    }

    public class KoeIterator implements Iterator<T>{
        protected Node plass;

        KoeIterator(){
            plass = foran;
        }

        public boolean hasNext(){
            if (storrelse == 0){
                return false;
            }
            if (plass == null){
                return false;
            }
            return true;
        }
        public T next(){
            if (hasNext()){
                T temp = plass.element;
                plass = plass.neste;
                return temp;
            }
            return null;

        }
    }
}
