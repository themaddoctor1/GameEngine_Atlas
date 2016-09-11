package util;

public class BinaryTree<T extends Comparable, E> {
    
    //A binary tree is either a node with two subtrees or null.
    private T key;
    private E val;
    BinaryTree<T, E> left, right;

    public BinaryTree() {
        this(null, null); 
    }
    
    private BinaryTree(T k, E v) {
        key = k;
        val = v;
        left = null;
        right = null;
    }

    //The key and value are properties of the particular node.
    private void setKey(T k) { key = k; }
    private void setVal(E v) { val = v; }

    private T getKey() { return key; }
    private E getVal() { return val; }
    
    public boolean isEmpty() { return key == null; }
    /**
     * A leaf is defined as a tree with one item.
     */
    public boolean isLeaf() { return !isEmpty() && left == null && right == null; }
    
    public void LL() {
        BinaryTree<T, E> 
                A = this,
                B = right,
                C = B.right,
                D = B.left,
                E = A.left;
        
        //Swap the keys
        T swpT = A.key;
        A.setKey(B.getKey());
        B.setKey(swpT);
        
        //Swap the values
        E swpE = A.val;
        A.setVal(B.getVal());
        B.setVal(swpE);
        
        //Extra reference swaps
        A.right = C;
        B.left = E;
        B.right = D;

    }

    public void RR() {
        BinaryTree<T, E> 
                A = this,
                B = left,
                C = B.left,
                D = B.right,
                E = A.right;
        
        //Swap the keys
        T swpT = A.key;
        A.setKey(B.getKey());
        B.setKey(swpT);
        
        //Swap the values
        E swpE = A.val;
        A.setVal(B.getVal());
        B.setVal(swpE);
        
        //Extra reference swaps
        A.left = C;
        B.right = E;
        B.left = D;

    }

    public void LR() {
        left.LL();
        RR();
    }

    public void RL() {
        right.RR();
        LL();
    }
    
    public int height() {
        return height(this);
    }

    private static int height(BinaryTree t) {
        if(t == null)
            return 0;
        if(t.isEmpty())
            return 0;
        else if(t.isLeaf())
            return 1;
        else
            return 1 + Math.max(t.left.height(), t.right.height());
    }

    public int size() {
        if(isEmpty())
            return 0;
        else
            return 1 + (left == null ? 0 : left.size()) + (right == null ? 0 : right.size());
    }

    /**
     * Adds an item to the tree.
     * Note: Avoid using matching keys, as it can be dangerous.
     *
     * @param idx A non-null key for positioning the item.
     * @param item The item being added to the tree.
     */
    public void add(T idx, E item) {
        if(key == null) {
            key = idx;
            val = item;
        } else {
            int comparison = idx.compareTo(key); 
            if(comparison< 0) {
                if(left == null)
                    left = new BinaryTree(idx, item);
                else
                    left.add(idx, item);
            } else if(comparison > 0){
                if(right == null)
                    right = new BinaryTree(idx, item);
                else
                    right.add(idx, item);
            } else {
                set(idx, item);
            }
        }

    }

    public E get(T idx) {
        int comparison = idx.compareTo(key);
        if(comparison == 0)
            return val;
        else if(comparison < 0) {
            if(left == null)
                return null;
            else
                return left.get(idx);
        } else {
            if(right == null)
                return null;
            else
                return right.get(idx);
        }
    }

    public void set(T idx, E item) {
        int comparison = idx.compareTo(key);
        if(comparison == 0)
            val = item;
        else if(comparison < 0)
            left.set(idx, item);
        else
            right.set(idx, item);
    }

    public E remove(T idx) {
        if(key == null)
            return null;
        int comparison = idx.compareTo(key);

        if(comparison == 0) {
            E result = val;
            if(right != null) {
                if(right.left == null) {
                    key = right.key;
                    val = right.val;
                    right = right.right;
                } else {
                    BinaryTree<T, E>
                            prev = right,
                            newDat = right.left;
                    while(newDat.left != null) {
                        prev = newDat;
                        newDat = newDat.left;
                    }
                    
                    prev.left = newDat.right;
                    key = newDat.key;
                    val = newDat.val;

                }
            } else if(left != null) {
                if(left.right == null) {
                    key = left.key;
                    val = left.val;
                    right = left.left;
                } else {
                    BinaryTree<T, E>
                            prev = left,
                            newDat = left.right;
                    while(newDat.right != null) {
                        prev = newDat;
                        newDat = newDat.right;
                    }
                    
                    prev.right = newDat.left;
                    key = newDat.key;
                    val = newDat.val;

                }
            }

            return result;

        } else if(comparison < 0) {
            E result = left.remove(idx);
            if(left.key == null)
                left = null;
            return result;
        } else {
            E result = right.remove(idx);
            if(right.key == null)
                right = null;
            return result;
        }



    }

    public boolean contains(T idx) {
        if(key == null)
            return false;

        int comparison = idx.compareTo(key);
        if(comparison == 0)
            return true;
        else if(comparison < 0)
            return left != null && left.contains(idx);
        else
            return right != null && right.contains(idx);
    }

}

