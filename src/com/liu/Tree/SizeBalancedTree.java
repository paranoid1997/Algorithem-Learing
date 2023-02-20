package com.liu.Tree;

public class SizeBalancedTree {

    public static class SBTNode<K extends Comparable<K>,V> {
        public K key;
        public V value;
        public SBTNode<K,V> left;
        public SBTNode<K,V> right;
        public int size;

        public SBTNode(K key, V value) {
            this.key = key;
            this.value = value;
            size = 1;
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>,V> {
        private SBTNode<K,V> root;

        private SBTNode<K,V> rightRotates(SBTNode<K,V> cur) {
            SBTNode<K,V> leftNode = cur.left;
            cur.left = leftNode.right;
            leftNode.right = cur;
            leftNode.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return leftNode;
        }
        private SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> rightNode = cur.right;
            cur.right = rightNode.left;
            rightNode.left = cur;
            rightNode.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return rightNode;
        }

        /**
         * SB树的平衡调节
         */
        private SBTNode<K,V> maintain(SBTNode<K,V> cur) {
            if (cur == null) {
                return null;
            }
            int leftSize = cur.left != null ? cur.left.size : 0;
            int leftLeftSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            int leftRightSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
            int rightSize = cur.right != null ? cur.right.size : 0;
            int rightLeftSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            int rightRightSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;

            if (leftLeftSize > rightSize) {
                //LL型
                cur = rightRotates(cur);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) {
                //LR型
                cur.left = leftRotate(cur.left);
                cur = rightRotates(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }else if (rightRightSize > leftSize) {
                //RR型
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur = maintain(cur);
            }else if (rightLeftSize > leftSize) {
                //RL型
                cur.right = rightRotates(cur.right);
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }
            return cur;
        }

        /**
         * SB树之增加节点
         */
        private SBTNode<K,V> add(SBTNode<K,V> cur,K key,V value) {
            if (cur == null) {
                return new SBTNode<K,V>(key,value);
            }else {
                cur.size++;
                if (key.compareTo(cur.key) < 0) {
                    cur.left = add(cur.left,key,value);
                }else {
                    cur.right = add(cur.right,key,value);
                }
                return maintain(cur);
            }
        }

        /**
         * SB树之删除节点
         */
        private SBTNode<K,V> delete(SBTNode<K,V> cur,K key) {
            cur.size--;
            if (key.compareTo(cur.key) > 0) {
                cur.right = delete(cur.right,key);
            }else if (key.compareTo(cur.key) < 0) {
                cur.left = delete(cur.left,key);
            }else {
                //当前要删除的节点key
                if (cur.left == null && cur.right == null) {
                    cur = null;
                }else if (cur.left == null && cur.right != null) {
                    cur = cur.right;
                }else if (cur.left != null && cur.right == null) {
                    cur = cur.left;
                }else {
                    //有左有右时
                    SBTNode<K,V> pre = null;
                    SBTNode<K,V> des = cur.right;
                    des.size--;
                    while (des.left != null) {
                        pre = des;
                        des =des.left;
                        des.size--;
                    }
                    if (pre != null) {
                        pre.left = des.right;
                        des.right = cur.right;
                    }
                    des.left = cur.left;
                    des.size = (des.left != null ? des.left.size : 0) + (des.right != null ? des.left.size : 0) + 1;
                    cur = des;
                }
            }
            return cur;
        }
        public V getIndexValue(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("invalid parameter.");
            }
            return getIndex(root, index + 1).value;
        }
        public K getIndexKey(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("invalid parameter.");
            }
            return getIndex(root, index + 1).key;
        }

        public K firstKey() {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur.key;
        }

        public K lastKey() {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur.right != null) {
                cur = cur.right;
            }
            return cur.key;
        }

        public K floorKey(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.key;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
            return lastNoSmallNode == null ? null : lastNoSmallNode.key;
        }
        private SBTNode<K, V> findLastIndex(K key) {
            SBTNode<K, V> pre = root;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.key) == 0) {
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return pre;
        }

        private SBTNode<K, V> findLastNoSmallIndex(K key) {
            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    ans = cur;
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return ans;
        }

        private SBTNode<K, V> findLastNoBigIndex(K key) {
            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else {
                    ans = cur;
                    cur = cur.right;
                }
            }
            return ans;
        }
        private SBTNode<K, V> getIndex(SBTNode<K, V> cur, int kth) {
            if (kth == (cur.left != null ? cur.left.size : 0) + 1) {
                return cur;
            } else if (kth <= (cur.left != null ? cur.left.size : 0)) {
                return getIndex(cur.left, kth);
            } else {
                return getIndex(cur.right, kth - (cur.left != null ? cur.left.size : 0) - 1);
            }
        }
        public V get(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.key) == 0) {
                return lastNode.value;
            } else {
                return null;
            }
        }

        public int size() {
            return root == null ? 0 : root.size;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.key) == 0 ? true : false;
        }

        // （key，value） put -> 有序表 新增、改value
        public void put(K key, V value) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.key) == 0) {
                lastNode.value = value;
            } else {
                root = add(root, key, value);
            }
        }
        public void remove(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            if (containsKey(key)) {
                root = delete(root, key);
            }
        }
    }
    // for test
    public static void printAll(SBTNode<String, Integer> head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    // for test
    public static void printInOrder(SBTNode<String, Integer> head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + "(" + head.key + "," + head.value + ")" + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    // for test
    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }
    public static void main(String[] args) {
        SizeBalancedTreeMap<String, Integer> sbt = new SizeBalancedTreeMap<String, Integer>();
        sbt.put("d", 4);
        sbt.put("c", 3);
        sbt.put("a", 1);
        sbt.put("b", 2);
        // sbt.put("e", 5);
        sbt.put("g", 7);
        sbt.put("f", 6);
        sbt.put("h", 8);
        sbt.put("i", 9);
        sbt.put("a", 111);
        System.out.println(sbt.get("a"));
        sbt.put("a", 1);
        System.out.println(sbt.get("a"));
        for (int i = 0; i < sbt.size(); i++) {
            System.out.println(sbt.getIndexKey(i) + " , " + sbt.getIndexValue(i));
        }
        printAll(sbt.root);
        System.out.println(sbt.firstKey());
        System.out.println(sbt.lastKey());
        System.out.println(sbt.floorKey("g"));
        System.out.println(sbt.ceilingKey("g"));
        System.out.println(sbt.floorKey("e"));
        System.out.println(sbt.ceilingKey("e"));
        System.out.println(sbt.floorKey(""));
        System.out.println(sbt.ceilingKey(""));
        System.out.println(sbt.floorKey("j"));
        System.out.println(sbt.ceilingKey("j"));
        sbt.remove("d");
        printAll(sbt.root);
        sbt.remove("f");
        printAll(sbt.root);
    }
}
