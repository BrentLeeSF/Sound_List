package project1;

public interface ListIterator {  
	
    public Object next();
    public Object previous();
    
    public boolean hasNext();
    public boolean hasPrevious();
    
    public void add(Object elem);
    public void remove();
    public void set(Object value);
    
}
