package util;

import math.Vector;

public class Octree<E> {
    
    private Vector key;
    private E val;
    Octree<E>[] subtrees;
    
    public Octree() {
        this(null, null);
    }

    public Octree(Vector k, E v) {
        key = k;
        val = v;
        subtrees = new Octree[8];
    }

    private Vector getKey() { return key; }
    private E getVal() { return val; }
    
    public boolean isEmpty() { return key == null; }

    public boolean isLeaf() {
        if(isEmpty())
            return false;
        else for(int i = 0; i < 8; i++)
            if(subtrees[i] != null)
                return false;
        return true;
    }

    private static int height(Octree t) {
        if(t == null)
            return 0;
        else if(t.isEmpty())
            return 0;
        
        int max = 0;
        for(int i = 0; i < 8; i++)
            max = Math.max(max, height(t.subtrees[i]));
        return 1 + max;

    }

    public int height() {
        return height(this);
    }
    
    public int size() {
        if(isEmpty())
            return 0;
        
        int size = 1;
        for(int i = 0; i < subtrees.length; i++)
            if(subtrees[i] != null)
                size += subtrees[i].size();
        return size;
    }

    private static int toKeyArray(Octree tree, Vector[] list, int curr) {
        if(tree == null || tree.isEmpty())
            return curr;
      
        int indx = curr + 1;
        list[indx] = tree.key;

        for(int i = 0; i < 8; i++)
            indx = toKeyArray(tree.subtrees[i], list, indx);

        return indx;
    }

    public Vector[] toKeyArray() {
        Vector[] res = new Vector[size()];
        toKeyArray(this, res, 0);
        return res;
    }
   
    private static int toValueArray(Octree tree, Object[] list, int curr) {
        if(tree == null || tree.isEmpty())
            return curr;
        
        int indx = curr + 1;
        list[indx] = tree.val;

        for(int i = 0; i < 8; i++)
            indx = toValueArray(tree.subtrees[i], list, indx);

        return indx;
    }

    public Object[] toValueArray() {
        if(isEmpty())
            return new Object[0];

        Object[] res = new Object[size()];
        toValueArray(this, res, 0);
        return res;
    }
    
    
    public void add(Vector idx, E item) {

        if(idx == null)
            throw new IllegalArgumentException("Octree elements must have non-null keys.");
        if(item == null)
            throw new IllegalArgumentException("Octree elements must be non-null.");

        if(key == null) {
            key = idx;
            val = item;
        } else {
            Vector diff = idx.sub(key);
            int indx = (diff.X < 0 ? 0 : 4) + (diff.Y < 0 ? 0 : 2) + (diff.Z < 0 ? 0 : 1);
            if(subtrees[indx] == null)
                subtrees[indx] = new Octree(idx, item);
            else
                subtrees[indx].add(idx, item);
        }
    }

    public E get(Vector idx) {

        if(idx == null)
            return null;

        Vector diff = idx.sub(key);
        if(diff.equals(Vector.ZERO))
            return val;
        else {
           int indx = (diff.X < 0 ? 0 : 4) + (diff.Y < 0 ? 0 : 2) + (diff.Z < 0 ? 0 : 1);

            if(subtrees[indx] == null)
                return null;
            else
                return subtrees[indx].get(idx);

        }
    }

    public void set(Vector idx, E item) {
         
        if(idx == null)
            throw new IllegalArgumentException("Octree elements do not have null keys.");
        if(item == null)
            throw new IllegalArgumentException("Octree elements must be non-null.");

        Vector diff = idx.sub(key);
        if(diff.equals(Vector.ZERO))
            val = item;
        else {
            int indx = (diff.X < 0 ? 0 : 4) + (diff.Y < 0 ? 0 : 2) + (diff.Z < 0 ? 0 : 1);
            
            if(subtrees[indx] == null)
                return;
            else
                subtrees[indx].set(idx, item);
        }
    }

    public E remove(Vector idx) {

        if(idx == null)
            return null;

        Vector diff = idx.sub(key);
        if(diff.equals(Vector.ZERO)) {
            //Get all of the values. removed will be the first element.
            Vector[] keys = toKeyArray();
            Object[] list = toValueArray();
            
            for(int i = 1; i < list.length; i++)
                add(keys[i], (E) list[i]);

            return (E) list[0];

        } else {
           
            int indx = (diff.X < 0 ? 0 : 4) + (diff.Y < 0 ? 0 : 2) + (diff.Z < 0 ? 0 : 1);
            
            if(subtrees[indx] == null)
                return null;
            else {
                E res = subtrees[indx].remove(idx);
                if(subtrees[indx].isEmpty())
                    subtrees[indx] = null;
                return res;
            }
        }

    }

    public Vector indexOf(E item) {
        if(val == item)
            return key;

        Vector res = null;
        for(int i = 0; i < 8 && res == null; i++)
            if(subtrees[i] != null)
                res = subtrees[i].indexOf(item);
        return res;
    }

    public void update(Vector newKey, E val) {
        //My current method is to remove the item and re-add it.
        if(val != null)
            add(newKey, remove(indexOf(val)));
    }


}
